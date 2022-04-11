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

    var coordinates = [[-92.329859,38.943725],[-92.329849,38.94401],[-92.329846,38.944065],[-92.32954,38.944062],[-92.32925,38.944296],[-92.329178,38.944288],[-92.32901,38.94427],[-92.328717,38.944239],[-92.32854,38.94422],[-92.328481,38.944213],[-92.328426,38.944207],[-92.328401,38.944204],[-92.327947,38.944152],[-92.32781,38.944155],[-92.327767,38.944156],[-92.327753,38.944195],[-92.327725,38.944228],[-92.327685,38.944252],[-92.327637,38.944265],[-92.327585,38.944265],[-92.327537,38.944253],[-92.327496,38.944229],[-92.327467,38.944196],[-92.327453,38.944157],[-92.327397,38.944156],[-92.327133,38.94415],[-92.327132,38.944029],[-92.327133,38.943887],[-92.327137,38.943762],[-92.32627,38.943752],[-92.325764,38.943748],[-92.32577,38.943568],[-92.325659,38.943567],[-92.325661,38.943501],[-92.325666,38.9433],[-92.325597,38.943298],[-92.324856,38.943291],[-92.32464,38.943287],[-92.324508,38.943286],[-92.324505,38.943249],[-92.324345,38.943219],[-92.324352,38.942813],[-92.324389,38.942751],[-92.324449,38.942705],[-92.324585,38.942776],[-92.324714,38.942775]]

    let points: Array<Point> = []
    coordinates.forEach(function (value) {
      let testPoint: Point ={longitude:value[0],latitude: value[1]}
      points.push(testPoint);
    });

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
