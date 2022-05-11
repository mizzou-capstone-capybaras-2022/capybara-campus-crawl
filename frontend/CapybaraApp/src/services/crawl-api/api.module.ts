import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { Configuration } from './configuration';
import { HttpClient } from '@angular/common/http';

import { BuildingControllerService } from './api/buildingController.service';
import { DoorControllerService } from './api/doorController.service';
import { GraphNodeControllerService } from './api/graphNodeController.service';
import { RouteControllerService } from './api/routeController.service';
import { MetricsControllerService } from './api/metricsController.service';
import { PlacesControllerService } from './api/placesController.service';

@NgModule({
  imports:      [],
  declarations: [],
  exports:      [],
  providers: [
    BuildingControllerService,
    DoorControllerService,
    GraphNodeControllerService,
    MetricsControllerService,
    PlacesControllerService,
    RouteControllerService 
  ]
})
export class ApiModule {
    public static forRoot(configurationFactory: () => Configuration): ModuleWithProviders<ApiModule> {
        return {
            ngModule: ApiModule,
            providers: [ { provide: Configuration, useFactory: configurationFactory } ]
        };
    }

    constructor( @Optional() @SkipSelf() parentModule: ApiModule,
                 @Optional() http: HttpClient) {
        if (parentModule) {
            throw new Error('ApiModule is already loaded. Import in your base AppModule only.');
        }
        if (!http) {
            throw new Error('You need to import the HttpClientModule in your AppModule! \n' +
            'See also https://github.com/angular/angular/issues/20575');
        }
    }
}
