import { Component, ViewEncapsulation } from '@angular/core';

import { BuildingControllerService } from 'src/services/crawl-api';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent {
  title = 'CapybaraApp';

  constructor(){

  }

  ngOnInit(){

  }
}
