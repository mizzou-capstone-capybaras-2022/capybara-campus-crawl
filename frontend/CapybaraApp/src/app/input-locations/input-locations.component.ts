import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, FormArray } from '@angular/forms';
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
    destination: new FormControl(''),
    constraints: new FormControl(''),
    indoor: new FormControl(''),
    stops: new FormArray([])
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

    //console.log(this.inputLocations);

    this.inputBuildings.emit([fromBuilding, toBuilding]);
  }

  isShown2: boolean = false ;
  numOfStops: number = 0;
  toggleShow2() {
    this.isShown2 = ! this.isShown2;
  }
  addStop() {
    let stopsInList: FormArray = <FormArray> this.inputLocations.get("stops");
    stopsInList.push(new FormControl(null));
    this.numOfStops++;
  }
  removeStop(){
    let stopsInList: FormArray = <FormArray> this.inputLocations.get("stops");
    stopsInList.push(new FormControl(null));
    this.numOfStops--;
  }

}
