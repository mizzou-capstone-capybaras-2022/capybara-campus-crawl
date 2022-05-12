import { Component, Input, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup } from '@angular/forms';
import { Building } from 'src/services/crawl-api';

@Component({
  selector: 'app-constraints-form',
  templateUrl: './constraints-form.component.html',
  styleUrls: ['./constraints-form.component.scss']
})
export class ConstraintsFormComponent {

  @Input() buildings: Array<Building> = [];
  numOfStops: number = 0;

  constraintForm: FormGroup = new FormGroup({
    food: new FormControl(false),
    avoidCrowds: new FormControl(false),
    time: new FormControl(null),
    timeHours: new FormControl(null),
    timeMinutes: new FormControl(null),
    indoor: new FormControl(false),
    stops: new FormArray([])
  })

  constructor() { }

  get formHasPitstops(): boolean {
    return this.numOfStops > 0;
  }

  addStop() {
    let stopsInList: FormArray = <FormArray> this.constraintForm.get("stops");
    stopsInList.push(new FormControl(null));
    this.numOfStops++;
  }

  removeStop(){
    let stopsInList: FormArray = <FormArray> this.constraintForm.get("stops");
    stopsInList.removeAt(stopsInList.getRawValue().length - 1)
    this.numOfStops--;
  }

  getFormConstraints(): FormGroup{
    return this.constraintForm
  }

}
