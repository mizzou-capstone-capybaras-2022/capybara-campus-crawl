import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Building, BuildingControllerService, RouteControllerService } from 'src/services/crawl-api';
import { firstValueFrom, lastValueFrom  } from 'rxjs';
import {BuildingLocation} from 'src/services/crawl-api/'

@Component({
  selector: 'app-input-locations',
  templateUrl: './input-locations.component.html',
  styleUrls: ['./input-locations.component.scss']
})
export class InputLocationsComponent implements OnInit {

  inputLocations = new FormGroup({
    start: new FormControl(''),
    destination: new FormControl('')
  });

  buildings: Array<Building> = [];

  constructor(private buildingDao: BuildingControllerService, private routeDao: RouteControllerService) { }

  async ngOnInit() {
    this.buildings = await lastValueFrom(this.buildingDao.getBuildings());
  }

  inputLocationsFn() {
    console.log(this.inputLocations.value);
  }

  searchBuildingsFn() {
    console.log(
      <BuildingLocation>{
        buildingId: this.inputLocations.value
      }
    );
  }

}
