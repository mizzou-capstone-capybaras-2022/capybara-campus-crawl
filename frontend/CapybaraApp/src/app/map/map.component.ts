import { Component, AfterViewInit } from '@angular/core';
import { LeafletControlLayersConfig } from '@asymmetrik/ngx-leaflet';

import { latLng, LatLng, tileLayer, polyline, TileLayer, Polyline, LatLngExpression } from 'leaflet';
import { Point } from 'src/services/crawl-api/model/point';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements AfterViewInit {

  streetMaps: TileLayer
  route: Polyline;
  layersControl: LeafletControlLayersConfig;
  layers: any[] = [];
  
  options: {};

  private initMap(): void {
    
  }

  constructor() {
    this.streetMaps = tileLayer(environment.tileservice + "/{z}/{x}/{y}.jpg" + "?key=" + environment.tileKey, { maxZoom: 18, attribution: '...' });
  
    let points: Array<Point> = [
      <Point>{latitude: 38.946831, longitude: -92.329229},
      <Point>{latitude: 38.944311, longitude: -92.328049 }
    ]

    let pointsPlural: LatLngExpression[] = points.map(point => <LatLngExpression>[point.latitude,point.longitude]);

    this.route = polyline(pointsPlural);

    this.layers = [this.route]

    this.layersControl = {
      baseLayers: {
        'Street Maps': this.streetMaps,
      },
      overlays: {
        'Destination route': this.route
      }
    };

    this.options = {
      layers: [ this.streetMaps ],
      zoom: 17,
      center: latLng(38.945095, -92.329261)
    };

  }

  ngAfterViewInit(): void {
    this.initMap();
  }

  rerenderMap(){
    console.log("fix");

    let points: Array<Point> = [
      <Point>{latitude: 38.946831, longitude: -92.329229},
      <Point>{latitude: 0, longitude: 0}
    ]

    let pointsPlural: LatLngExpression[] = points.map(point => <LatLngExpression>[point.latitude,point.longitude]);
    
    this.route = polyline(pointsPlural);

    this.layers = [this.route]

    this.layersControl = <LeafletControlLayersConfig> {
      baseLayers: {
        'Street Maps': this.streetMaps,
      },
      overlays: {
        'Destination route': this.route
      }
    };
  }

}
