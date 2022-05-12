import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { FormControl, FormGroup, FormArray } from '@angular/forms';
import { BaraBackendWrapperService } from 'src/services/bara-backend-wrapper/bara-backend-wrapper.service';
import { Building } from 'src/services/crawl-api';
import { RouteParameters } from 'src/share/types/RouteParameters';
import { ConstraintsFormComponent } from '../constraints-form/constraints-form.component';

@Component({
  selector: 'app-input-locations',
  templateUrl: './input-locations.component.html',
  styleUrls: ['./input-locations.component.scss']
})
export class InputLocationsComponent implements OnInit {

  @Output() inputBuildings: EventEmitter<RouteParameters> = new EventEmitter<RouteParameters>();

  @ViewChild(ConstraintsFormComponent) constraintFormComponent!: ConstraintsFormComponent;

  buildings: Array<Building> = [];
  showConstraintsForm: boolean = false;
  
  inputLocations = new FormGroup({
    start: new FormControl(''),
    destination: new FormControl(''),
  });

  constructor(private baraApi: BaraBackendWrapperService) {}

  async ngOnInit(): Promise<void> {
    this.buildings = await this.baraApi.getBuildings();

    this.inputLocations.get("start")?.setValue(this.buildings[0]);
    this.inputLocations.get("destination")?.setValue(this.buildings[1]);
  }

  inputLocationsFn() {
    let fromBuilding: Building = <Building> this.inputLocations.get("start")?.value;
    let toBuilding: Building = <Building> this.inputLocations.get("destination")?.value;

    let routeParameters: RouteParameters = <RouteParameters> {
      fromBuilding: fromBuilding,
      toBuilding: toBuilding,
      avoidCrowds: false,
      stopByFood: false,
      preferIndoors: false,
      pitstops: []
    };

    if (this.showConstraintsForm){
      let constraintForm: FormGroup = this.constraintFormComponent.getFormConstraints();
      
      let constraintPitstops: Building[] = (<Building[]>constraintForm.get("stops")?.value).filter(building => building != null);
      
      routeParameters.preferIndoors = constraintForm.get("indoor")?.value;
      routeParameters.pitstops = constraintPitstops;
      routeParameters.stopByFood = constraintForm.get("food")?.value;
      routeParameters.avoidCrowds = constraintForm.get("avoidCrowds")?.value;
    }

    this.inputBuildings.emit(routeParameters);
  }

  toggleConstraints() {
    this.showConstraintsForm = !this.showConstraintsForm;
  }
}
