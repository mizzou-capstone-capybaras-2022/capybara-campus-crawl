import { icon, LatLng, marker, Marker, Polyline } from "leaflet";
import { Point } from "src/services/crawl-api";

export class PointUtil {
    
    static convertPointToMarker(point: Point): Marker | undefined{
        if (point.latitude != undefined && point.longitude != undefined){
            //TODO abstract into seperate method
            //TODO utilize the place enum that returns dynamicaly changes the marker icon
            return marker([point.latitude, point.longitude],
                {
                    icon: icon({
                        iconSize: [ 25, 41 ],
                        iconAnchor: [ 13, 41 ],
                        iconUrl: '/assets/marker-icon-x1.png',
                        iconRetinaUrl: '/assets/marker-icon-x2.png',
                        shadowUrl: '/assets/leaflet/marker-shadow.png'
                    })
                })
        }else{
            return undefined;
        }
    }

    static convertPointsToPolyline(routePoints: Point[]): Polyline | undefined {
        return new Polyline(routePoints.map(point => {
            return <LatLng>{
                lat: point.latitude,
                lng: point.longitude
            }
        }));
    }

}