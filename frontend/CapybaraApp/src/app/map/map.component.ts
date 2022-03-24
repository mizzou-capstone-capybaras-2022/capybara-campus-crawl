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

  streetMaps = tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
     maxZoom: 15, attribution: '&copy; <a href="https://openstreetmap.org/copyright">OpenStreetMap contributors</a>' });

  wMaps = tileLayer('http://maps.wikimedia.org/osm-intl/{z}/{x}/{y}.png', {
       attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
  });

  route = polyline([[ 38.946831, -92.329229 ],
    [ 38.944311, -92.328049 ]]);

  layersControl = {
    baseLayers: {
      'Street Maps': this.streetMaps,
      'Wikimedia Maps': this.wMaps
    },
    overlays: {
      'Destination route': this.route
    }
  };

  options = {
    layers: [ this.streetMaps, this.wMaps, this.route ],
    zoom: 15,
    center: latLng(38.945095, -92.329261)
  };

  private initMap(): void {

  }

  constructor() {
    this.streetMaps = tileLayer(environment.tileservice + "/{z}/{x}/{y}.jpg" + "?key=" + environment.tileKey, { maxZoom: 18, attribution: '...' });
    
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
      zoom: 17,
      center: latLng(38.945095, -92.329261)
    };
  }

  ngAfterViewInit(): void {
    this.initMap();
  }

}
