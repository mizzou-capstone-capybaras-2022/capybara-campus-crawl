import { Component, ViewChild, ViewEncapsulation } from '@angular/core';

import { Building, BuildingControllerService, Point } from 'src/services/crawl-api';
import { MapComponent } from './map/map.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent {
  title = 'CapybaraApp';

  @ViewChild(MapComponent) mapComponent!: MapComponent;

  constructor(){

  }

  ngOnInit(){

  }

  onSelectBuildingFromSearch(selectedBuilding: Building){
    let buildingPoint = <Point>{
      latitude: selectedBuilding.graphNode?.latitude,
      longitude: selectedBuilding.graphNode?.longitude
    }

    this.mapComponent.renderMapMarkers([buildingPoint]);
  }
}
