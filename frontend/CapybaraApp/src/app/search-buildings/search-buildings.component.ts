import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-search-buildings',
  templateUrl: './search-buildings.component.html',
  styleUrls: ['./search-buildings.component.scss']
})
export class SearchBuildingsComponent implements OnInit {

  searchBuildings = new FormGroup({
    building: new FormControl('')
  });

  constructor() { }

  ngOnInit(): void {
  }

  searchBuildingsFn() {
    console.log(this.searchBuildings.value);
  }

}
