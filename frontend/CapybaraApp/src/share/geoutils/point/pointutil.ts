import { Icon, icon, IconOptions, LatLng, marker, Marker, Polyline } from "leaflet";
import { Place, Point } from "src/services/crawl-api";

export class PointUtil {
    static generateMarkerIcon(markerType: Place.PlaceTypeEnum | "Raspberry" | undefined): Icon<IconOptions> {
        let iconOptions: IconOptions = <IconOptions>{
            iconSize: [ 25, 41 ],
            iconAnchor: [ 13, 41 ],
            iconUrl: '/assets/marker-icon-x1.png',
            iconRetinaUrl: '/assets/marker-icon-x2.png',
            shadowUrl: '/assets/leaflet/marker-shadow.png'
        }
        
        if (typeof markerType == "string"){
            switch (markerType){
                case Place.PlaceTypeEnum.Classroom:
                    iconOptions.iconUrl = '/assets/marker-icon-classroom-x1.png';
                    iconOptions.iconRetinaUrl = '/assets/marker-icon-classroom-x2.png';
                    break;
                case Place.PlaceTypeEnum.Dining:
                    iconOptions.iconUrl = '/assets/marker-icon-food-x1.png';
                    iconOptions.iconRetinaUrl = '/assets/marker-icon-food-x2.png';
                    break;
                case Place.PlaceTypeEnum.Study:
                    iconOptions.iconUrl = '/assets/marker-icon-study-x1.png';
                    iconOptions.iconRetinaUrl = '/assets/marker-icon-study-x2.png';
                    break;
                case "Raspberry":
                    iconOptions.iconUrl = '/assets/marker-icon-iot-x1.png';
                    iconOptions.iconRetinaUrl = '/assets/marker-icon-iot-x2.png';
                    break;
            }
        }


        let iconToReturn: Icon<IconOptions> = icon(iconOptions);
        return iconToReturn;
    }

    static convertPointToMarker(point: Point): Marker | undefined{
        if (point.latitude != undefined && point.longitude != undefined){
            return marker(
                [point.latitude, point.longitude],
                {icon: PointUtil.generateMarkerIcon(undefined)}
            );
        }else{
            return undefined;
        }
    }

    static convertPlaceToMarker(place: Place): Marker | undefined {
        let placePointLocation: Point = <Point>{
            latitude: place.node?.latitude,
            longitude: place.node?.longitude
        }

        if (placePointLocation.latitude != undefined && placePointLocation.longitude != undefined){
            //TODO abstract into seperate method
            //TODO utilize the place enum that returns dynamicaly changes the marker icon
            return marker(
                [placePointLocation.latitude, placePointLocation.longitude],
                {icon: PointUtil.generateMarkerIcon(place.placeType)}

            )
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