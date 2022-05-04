export * from './buildingController.service';
import { BuildingControllerService } from './buildingController.service';
export * from './doorController.service';
import { DoorControllerService } from './doorController.service';
export * from './graphNodeController.service';
import { GraphNodeControllerService } from './graphNodeController.service';
export * from './routeController.service';
import { RouteControllerService } from './routeController.service';
export const APIS = [BuildingControllerService, DoorControllerService, GraphNodeControllerService, RouteControllerService];
