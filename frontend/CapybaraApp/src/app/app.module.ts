import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MapComponent } from './map/map.component';
import { MatTabsModule } from '@angular/material/tabs';

import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { InputLocationsComponent } from './input-locations/input-locations.component';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import {FormsModule,ReactiveFormsModule} from '@angular/forms';


import {ApiModule, BASE_PATH} from '../services/crawl-api';
import { HttpClientModule } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    InputLocationsComponent
  ],
  imports: [
    BrowserModule,
    MatTabsModule,
    ApiModule,
    AppRoutingModule,
    LeafletModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatSelectModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    { provide: BASE_PATH, useValue: environment.baseApiPath },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
