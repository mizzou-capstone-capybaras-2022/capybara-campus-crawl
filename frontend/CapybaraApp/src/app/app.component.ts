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
    /**
    console.log("TesT");
    fetch('http://localhost:8090/buildings')
      .then(response => response.json())
      .then(data => console.log(data));
    **/

    this.buildingDao.getBuildings().subscribe((value) => {
      console.log(value);
    })
  }
}
