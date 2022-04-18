import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Building, BuildingControllerService, RouteControllerService } from 'src/services/crawl-api';
import { firstValueFrom, lastValueFrom  } from 'rxjs';
import {BuildingLocation} from 'src/services/crawl-api/'
import { BaraBackendWrapperService } from 'src/services/bara-backend-wrapper/bara-backend-wrapper.service';


@Component({
  selector: 'app-search-buildings',
  templateUrl: './search-buildings.component.html',
  styleUrls: ['./search-buildings.component.scss']
})
export class SearchBuildingsComponent implements OnInit {

  @Output() selectBuildingFromSearch: EventEmitter<Building> = new EventEmitter<Building>();

  searchBuildings = new FormGroup({
    building: new FormControl(null)
  });

  buildings: Array<Building> = [];

  constructor(private baraApi: BaraBackendWrapperService){}

  async ngOnInit(){
    this.buildings = await this.baraApi.getBuildings();
    this.searchBuildings.get("building")?.setValue(this.buildings[0]);
  }

  searchBuildingsFn() {
    let selectedBuilding: Building = this.searchBuildings.get("building")?.value;
    this.selectBuildingFromSearch.emit(selectedBuilding);
  }

}
