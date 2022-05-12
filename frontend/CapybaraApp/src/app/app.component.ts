import { Component, ViewChild, ViewEncapsulation } from '@angular/core';
import { BaraBackendWrapperService } from 'src/services/bara-backend-wrapper/bara-backend-wrapper.service';

import { Building, BuildingControllerService, BuildingLocation, PitstopConstraint, Place, Point, RouteRequestConstraints } from 'src/services/crawl-api';
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
    console.log(routeParameters);

    let buildingFrom: Building = routeParameters.fromBuilding;
    let buildingTo: Building = routeParameters.toBuilding;
    let routeConstraints: RouteRequestConstraints = this.buildRouteConstraintsFromParameters(routeParameters);

    let routePoints: Point[] = await this.baraApi.getRouteWithConstraints(<number>buildingFrom.buildingId, <number>buildingTo.buildingId, routeConstraints);    
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

  private buildRouteConstraintsFromParameters(routeParameters: RouteParameters): RouteRequestConstraints{
    let constraints = <RouteRequestConstraints>{
      stopForFood: false,
      preferIndoors: false,
      avoidCrowds: false,
      pitstops: [],
      timeConstraint: null
    };

    constraints.preferIndoors = routeParameters.preferIndoors;
    constraints.stopForFood = routeParameters.stopByFood;
    constraints.avoidCrowds = routeParameters.avoidCrowds;
    constraints.pitstops = routeParameters.pitstops.map (pitstop => 
      <PitstopConstraint> {
        location: <BuildingLocation> {
          buildingId: pitstop.buildingId
        }
      }
    );

    return constraints;
  }

}
