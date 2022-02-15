import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'CapybaraApp';

  ngOnInit(){
    console.log("TesT");
    fetch('http://localhost:8090/buildings')
      .then(response => response.json())
      .then(data => console.log(data));
  }
}
