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
import {MatButtonModule} from '@angular/material/button';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { MatInputModule } from '@angular/material/input';
import { SearchBuildingsComponent } from './search-buildings/search-buildings.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatCardModule} from '@angular/material/card';
import {MatDividerModule} from '@angular/material/divider';
import {ApiModule, BASE_PATH} from '../services/crawl-api';
import { HttpClientModule } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { PlacesViewerComponent } from './places-viewer/places-viewer.component';
import { RaspberryPiViewerComponent } from './raspberry-pi-viewer/raspberry-pi-viewer.component';
import { ConstraintsFormComponent } from './constraints-form/constraints-form.component';

@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    InputLocationsComponent,
    SearchBuildingsComponent,
    PlacesViewerComponent,
    RaspberryPiViewerComponent,
    ConstraintsFormComponent
  ],
  imports: [
    BrowserModule,
    MatTabsModule,
    MatCardModule,
    MatDividerModule,
    ApiModule,
    MatToolbarModule,
    AppRoutingModule,
    LeafletModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatSelectModule,
    FormsModule,ReactiveFormsModule,
    MatButtonModule,
    MatCheckboxModule,
    MatInputModule,
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
