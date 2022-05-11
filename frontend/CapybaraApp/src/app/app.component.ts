import { Component, ViewChild, ViewEncapsulation } from '@angular/core';
import { BaraBackendWrapperService } from 'src/services/bara-backend-wrapper/bara-backend-wrapper.service';

import { Building, BuildingControllerService, Place, Point } from 'src/services/crawl-api';
import { PiMetric } from 'src/services/crawl-api/model/piMetric';
import { RouteParameters } from 'src/share/types/RouteParameters';
import { MapComponent } from './map/map.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent {
  title = 'CapybaraApp';

  @ViewChild(MapComponent) mapComponent!: MapComponent;

  constructor(private baraApi: BaraBackendWrapperService){}

  onSelectBuildingFromSearch(selectedBuilding: Building){
    let buildingPoint = this.getBuildingPoint(selectedBuilding);
    this.mapComponent.renderNormalMapMarkers([buildingPoint]);
  }

  async onRouteParametersRecieved(routeParameters: RouteParameters){
    let buildingFrom: Building = routeParameters.fromBuilding;
    let buildingTo: Building = routeParameters.toBuilding;

    let routePoints: Point[] = await this.baraApi.getRouteWithConstraints(<number>buildingFrom.buildingId, <number>buildingTo.buildingId, null);    
    this.mapComponent.renderRoute(routePoints);
  }

  async onSelectPlace(selectedPlace: Place.PlaceTypeEnum){
    let placesOfInterest: Array<Place> = await this.baraApi.getPlacesByPlaceType(selectedPlace);
    this.mapComponent.renderPlaceMarkers(placesOfInterest);
  }

  async onViewMetrics(){
    let metricsOfInterest: Array<PiMetric> = await this.baraApi.getMetrics();
    this.mapComponent.renderMetricMarkers(metricsOfInterest);
  }

  private getBuildingPoint(building: Building): Point {
    return <Point>{
      latitude: building.graphNode?.latitude,
      longitude: building.graphNode?.longitude
    }
  }
}
