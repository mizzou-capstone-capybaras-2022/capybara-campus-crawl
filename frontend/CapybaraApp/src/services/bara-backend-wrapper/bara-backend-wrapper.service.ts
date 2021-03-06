import { Injectable } from '@angular/core';
import { firstValueFrom, Observable, of } from 'rxjs';
import { Building, BuildingControllerService, BuildingLocation, BuildingRouteRequest, Location, MetricsControllerService, Place, Point, RouteControllerService, RouteRequest, RouteRequestConstraints } from '../crawl-api';
import { PlacesControllerService } from '../crawl-api/api/placesController.service';
import { PiMetric } from '../crawl-api/model/piMetric';

//TODO pull from places API

@Injectable({
  providedIn: 'root'
})
export class BaraBackendWrapperService {
  private buildings: Observable<Building[]> = of(<Array<Building>>[]);

  constructor(private buildingDao: BuildingControllerService, private routeDao: RouteControllerService, private placeDao: PlacesControllerService, private metricDao: MetricsControllerService) {
    this.buildings = this.buildingDao.getBuildings();
  }

  async getMetrics(){
    let metricsObservable = this.metricDao.getMetrics();
    let returnedMetrics: Array<PiMetric> = await firstValueFrom(metricsObservable);

    return returnedMetrics;
  }

  async getPlacesByPlaceType(placeTypeOfInterest: Place.PlaceTypeEnum): Promise<Place[]> {
    let placesObservable = this.placeDao.getPlaces();
    let returnedPlaces: Array<Place> = await firstValueFrom(placesObservable);

    let filteredPlaces = returnedPlaces.filter(place => {
      return place.placeType == placeTypeOfInterest;
    })

    return filteredPlaces;
  }

  async getBuildings(): Promise<Building[]> {
    return await firstValueFrom(this.buildings);
  }

  async getRouteBetweenBuildings(fromBuildingId: number, toBuildingId: number): Promise<Point[]> {
    let buildingRouteRequest: BuildingRouteRequest = <BuildingRouteRequest>{
      fromBuilding: <BuildingLocation>{
        buildingId: fromBuildingId
      },
      toBuilding: <BuildingLocation>{
        buildingId: toBuildingId
      }
    };

    return await firstValueFrom(this.routeDao.getSimpleRouteBetweenBuildings(buildingRouteRequest));
  }

  async getRouteWithConstraints(fromBuildingId: number, toBuildingId: number, routeConstraints: RouteRequestConstraints): Promise<Point[]> {
    let fromBuilding: Location = <BuildingLocation>{
      buildingId: fromBuildingId
    }

    let toBuilding: Location = <BuildingLocation> {
      buildingId: toBuildingId
    }

    let routeRequest: RouteRequest = <RouteRequest> {
      fromLocation: fromBuilding,
      toLocation: toBuilding,
      constraints: routeConstraints
    }

    return await firstValueFrom(this.routeDao.getRoute(routeRequest));
  }
}
