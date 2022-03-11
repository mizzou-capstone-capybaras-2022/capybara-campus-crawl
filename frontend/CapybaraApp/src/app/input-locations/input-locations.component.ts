import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

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

  constructor() { }

  ngOnInit(): void {
  }

  inputLocationsFn() {
    console.log(this.inputLocations.value);
  }

}
