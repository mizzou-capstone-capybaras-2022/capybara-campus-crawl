import { Component, ViewChild, ViewEncapsulation } from '@angular/core';
import { BaraBackendWrapperService } from 'src/services/bara-backend-wrapper/bara-backend-wrapper.service';

import { Building, BuildingControllerService, Place, Point } from 'src/services/crawl-api';
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
    this.mapComponent.renderMapMarkers([buildingPoint]);
  }

  async onSelectBuildingForNavigation(selectedBuilding: [Building, Building]){
    let buildingFrom = selectedBuilding[0];
    let buildingTo = selectedBuilding[1];

    let routePoints: Point[] = await this.baraApi.getRouteWithConstraints(<number>buildingFrom.buildingId, <number>buildingTo.buildingId, null);    
    this.mapComponent.renderRoute(routePoints);
  }

  async onSelectPlace(selectedPlace: Place.PlaceTypeEnum){
    let placesOfInterest: Array<Place> = await this.baraApi.getPlacesByPlaceType(selectedPlace);

    console.log(placesOfInterest);
  }

  private getBuildingPoint(building: Building): Point {
    return <Point>{
      latitude: building.graphNode?.latitude,
      longitude: building.graphNode?.longitude
    }
  }
}
