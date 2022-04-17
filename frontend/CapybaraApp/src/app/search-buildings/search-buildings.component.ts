import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Building, BuildingControllerService, RouteControllerService } from 'src/services/crawl-api';
import { firstValueFrom, lastValueFrom  } from 'rxjs';
import {BuildingLocation} from 'src/services/crawl-api/'

@Component({
  selector: 'app-search-buildings',
  templateUrl: './search-buildings.component.html',
  styleUrls: ['./search-buildings.component.scss']
})
export class SearchBuildingsComponent implements OnInit {

  searchBuildings = new FormGroup({
    building: new FormControl('')
  });

  buildings: Array<Building> = [];

  constructor(private buildingDao: BuildingControllerService, private routeDao: RouteControllerService){

  }

  async ngOnInit(){
    this.buildings = await lastValueFrom(this.buildingDao.getBuildings());
  }

  searchBuildingsFn() {
    console.log(
      <BuildingLocation>{
        buildingId: this.searchBuildings.value
      }
    );
  }

}
