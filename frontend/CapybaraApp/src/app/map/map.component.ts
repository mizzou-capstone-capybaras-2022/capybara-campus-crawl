import { Component, AfterViewInit } from '@angular/core';

import { latLng, LatLng, tileLayer, polyline } from 'leaflet';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements AfterViewInit {

  streetMaps = tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 18, attribution: '...' });

  route = polyline([[ 38.946831, -92.329229 ],
    [ 38.944311, -92.328049 ]]);

  layersControl = {
    baseLayers: {
      'Street Maps': this.streetMaps,
    },
    overlays: {
      'Destination route': this.route
    }
  };

  options = {
    layers: [ this.streetMaps, this.route ],
    zoom: 15,
    center: latLng(38.945095, -92.329261)
  };

  private initMap(): void {

  }

  constructor() { }

  ngAfterViewInit(): void {
    this.initMap();
  }

}
