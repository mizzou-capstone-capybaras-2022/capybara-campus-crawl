import { Component } from '@angular/core';

import { BuildingControllerService } from 'src/services/crawl-api';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'CapybaraApp';

  constructor(private buildingDao: BuildingControllerService){

  }

  ngOnInit(){
    this.buildingDao.index3().subscribe((value) => {
      console.log(JSON.stringify(value));
    })
  }
}
