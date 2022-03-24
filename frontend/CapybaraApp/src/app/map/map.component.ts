import { Component, AfterViewInit } from '@angular/core';
import { LeafletControlLayersConfig } from '@asymmetrik/ngx-leaflet';

import { latLng, LatLng, tileLayer, polyline, TileLayer, Polyline } from 'leaflet';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements AfterViewInit {

  streetMaps: TileLayer;
  route: Polyline
  layersControl: LeafletControlLayersConfig;
  options: {};

  private initMap(): void {

  }

  constructor() {
    this.streetMaps = tileLayer(environment.tileservice + "/{z}/{x}/{y}.png", { maxZoom: 18, attribution: '...' });
    
    this.route = polyline([[ 38.946831, -92.329229 ],
      [ 38.944311, -92.328049 ]]);

    this.layersControl = {
      baseLayers: {
        'Street Maps': this.streetMaps,
      },
      overlays: {
        'Destination route': this.route
      }
    };

    this.options = {
      layers: [ this.streetMaps, this.route ],
      zoom: 15,
      center: latLng(38.945095, -92.329261)
    };
  }

  ngAfterViewInit(): void {
    this.initMap();
  }

}
