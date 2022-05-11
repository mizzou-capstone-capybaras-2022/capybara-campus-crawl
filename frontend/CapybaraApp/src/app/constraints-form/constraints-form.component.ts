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
    food: new FormControl(''),
    time: new FormControl(''),
    timeHours: new FormControl(''),
    timeMinutes: new FormControl(''),
    indoor: new FormControl(''),
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
    stopsInList.push(new FormControl(null));
    this.numOfStops--;
  }

}
