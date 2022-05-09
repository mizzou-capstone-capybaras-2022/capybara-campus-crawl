import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

import {Place} from '../../services/crawl-api/model/place';


@Component({
  selector: 'app-places-viewer',
  templateUrl: './places-viewer.component.html',
  styleUrls: ['./places-viewer.component.scss']
})
export class PlacesViewerComponent {

  places:Array<Place.PlaceTypeEnum> = [];

  inputPlaceForm = new FormGroup({
    placeType: new FormControl(Place.PlaceTypeEnum.Classroom)
  });

  constructor() {
    this.places = Object.values(Place.PlaceTypeEnum);
  }

  getPlaceName(place: Place.PlaceTypeEnum) : string {
    return place.toString().charAt(0).toUpperCase() + place.toString().slice(1).toLowerCase();
  }

  inputPlacesFn(): void {
    
  }

}
