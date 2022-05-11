export * from './buildingController.service';
import { BuildingControllerService } from './buildingController.service';
export * from './doorController.service';
import { DoorControllerService } from './doorController.service';
export * from './graphNodeController.service';
import { GraphNodeControllerService } from './graphNodeController.service';
export * from './routeController.service';
import { RouteControllerService } from './routeController.service';
export * from './metricsController.service';
export * from './placesController.service';
import { PlacesControllerService } from './placesController.service';
import { MetricsControllerService } from './metricsController.service';
export const APIS = [PlacesControllerService, MetricsControllerService, BuildingControllerService, DoorControllerService, GraphNodeControllerService, RouteControllerService];
