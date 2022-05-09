import { Injectable } from '@angular/core';
import { firstValueFrom, Observable, of } from 'rxjs';
import { Building, BuildingControllerService, BuildingLocation, BuildingRouteRequest, Location, Point, RouteControllerService, RouteRequest, RouteRequestConstraints } from '../crawl-api';
import { PlacesControllerService } from '../crawl-api/api/placesController.service';

//TODO pull from places API

@Injectable({
  providedIn: 'root'
})
export class BaraBackendWrapperService {
  private buildings: Observable<Building[]> = of(<Array<Building>>[]);

  constructor(private buildingDao: BuildingControllerService, private routeDao: RouteControllerService, private placeDao: PlacesControllerService) {
    this.buildings = this.buildingDao.getBuildings();
  }

  async getPlaces(): Promise<Place[]> {
    let placesObservable = this.placeDao.getPlaces();

    return await firstValueFrom(placesObservable);
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

  async getRouteWithConstraints(fromBuildingId: number, toBuildingId: number, routeConstraints: RouteRequestConstraints | null): Promise<Point[]> {
    let constraintsToUse: RouteRequestConstraints = <RouteRequestConstraints>{
      stopForFood: false,
      preferIndoors: false,
      avoidCrowds: false,
      pitstops: [],
      timeConstraint: null
    }
    
    if (routeConstraints != null){
      constraintsToUse = <RouteRequestConstraints> routeConstraints;
    }

    let fromBuilding: Location = <BuildingLocation>{
      buildingId: fromBuildingId
    }

    let toBuilding: Location = <BuildingLocation> {
      buildingId: toBuildingId
    }

    let routeRequest: RouteRequest = <RouteRequest> {
      fromLocation: fromBuilding,
      toLocation: toBuilding,
      constraints: constraintsToUse
    }

    return await firstValueFrom(this.routeDao.getRoute(routeRequest));
  }
}
