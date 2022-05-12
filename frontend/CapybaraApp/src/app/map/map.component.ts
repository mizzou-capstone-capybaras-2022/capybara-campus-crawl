import { Component, AfterViewInit } from '@angular/core';
import { LeafletControlLayersConfig } from '@asymmetrik/ngx-leaflet';

import { latLng, LatLng, tileLayer, polyline, TileLayer, Polyline, LatLngExpression, Marker } from 'leaflet';
import { Point } from 'src/services/crawl-api/model/point';
import { PointUtil } from 'src/share/geoutils/point/pointutil';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent {

  streetMaps: TileLayer;
  layersControl: LeafletControlLayersConfig;
  layers: any[] = [];
  options: {};

  private getMapTileLayer(tileserviceUrl: string, tileKey: string): TileLayer {
    return tileLayer(tileserviceUrl + "/{z}/{x}/{y}.jpg" + "?key=" + tileKey, { maxZoom: 18, attribution: '...' });
  }

  constructor() {
    this.streetMaps = this.getMapTileLayer(environment.tileservice, environment.tileKey);

    this.layersControl = {
      baseLayers: {
        'Street Maps': this.streetMaps,
      },
      overlays: {}
    };

    this.options = {
      layers: [ this.streetMaps ],
      zoom: 17,
      center: latLng(38.945095, -92.329261)
    };
  }

  renderMapMarkers(points: Point[]){
    let mapMarkers: Marker<any>[] = <Marker<any>[]> points.map(point => {
      return PointUtil.convertPointToMarker(point);
    }).filter(point => {
      return point != undefined
    });

    this.layers = mapMarkers;
  }

  renderRoute(routePoints: Point[]){
    let routePolyline = PointUtil.convertPointsToPolyline(routePoints);
    this.layers = [routePolyline];
  }

  resetMap(){
    this.layers = [];
  }
  
}
