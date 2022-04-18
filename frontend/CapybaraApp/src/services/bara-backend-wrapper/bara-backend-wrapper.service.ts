import { Injectable } from '@angular/core';
import { Polyline } from 'leaflet';
import { firstValueFrom, Observable, of } from 'rxjs';
import { Building, BuildingControllerService, BuildingLocation, BuildingRouteRequest, Point, RouteControllerService } from '../crawl-api';

@Injectable({
  providedIn: 'root'
})
export class BaraBackendWrapperService {
  private buildings: Observable<Building[]> = of(<Array<Building>>[]);

  constructor(private buildingDao: BuildingControllerService, private routeDao: RouteControllerService) {
    this.buildings = this.buildingDao.getBuildings();
  }

  async getBuildings(): Promise<Building[]> {
    return await firstValueFrom(this.buildings);
  }

  async getRouteBetweenBuildings(fromBuildingId: number, toBuildingId: number): Promise<Point[]> {
    return await firstValueFrom(this.routeDao.getSimpleRouteBetweenBuildings(<BuildingRouteRequest>{
      fromBuilding: <BuildingLocation>{
        buildingId: fromBuildingId
      },
      toBuilding: <BuildingLocation>{
        buildingId: toBuildingId
      }
    }));
  }
}
