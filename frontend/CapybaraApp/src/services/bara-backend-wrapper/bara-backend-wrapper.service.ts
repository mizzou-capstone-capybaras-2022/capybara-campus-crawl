import { Injectable } from '@angular/core';
import { Polyline } from 'leaflet';
import { firstValueFrom, Observable, of } from 'rxjs';
import { Building, BuildingControllerService } from '../crawl-api';

@Injectable({
  providedIn: 'root'
})
export class BaraBackendWrapperService {

  private buildings: Observable<Building[]> = of(<Array<Building>>[]);

  constructor(private buildingDao: BuildingControllerService) {
    this.buildings = this.buildingDao.getBuildings();
  }

  async getBuildings(): Promise<Building[]> {
    return await firstValueFrom(this.buildings);
  }

  async getRoute(): Promise<Polyline | null> {
    return await null;
  }
}
