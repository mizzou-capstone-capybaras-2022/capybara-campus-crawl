import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { BaraBackendWrapperService } from 'src/services/bara-backend-wrapper/bara-backend-wrapper.service';
import { Building } from 'src/services/crawl-api';

@Component({
  selector: 'app-input-locations',
  templateUrl: './input-locations.component.html',
  styleUrls: ['./input-locations.component.scss']
})
export class InputLocationsComponent implements OnInit {

  @Output() inputBuildings: EventEmitter<[Building, Building]> = new EventEmitter<[Building, Building]>();

  buildings: Array<Building> = [];

  inputLocations = new FormGroup({
    start: new FormControl(''),
    destination: new FormControl('')
  });

  constructor(private baraApi: BaraBackendWrapperService) { 
    
  }

  async ngOnInit(): Promise<void> {
    this.buildings = await this.baraApi.getBuildings();

    this.inputLocations.get("start")?.setValue(this.buildings[0]);
    this.inputLocations.get("destination")?.setValue(this.buildings[1]);
  }

  inputLocationsFn() {
    let fromBuilding: Building = <Building> this.inputLocations.get("start")?.value;
    let toBuilding: Building = <Building> this.inputLocations.get("destination")?.value;

    this.inputBuildings.emit([fromBuilding, toBuilding]);
  }

}
