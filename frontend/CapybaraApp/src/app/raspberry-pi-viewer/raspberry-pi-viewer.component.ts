import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-raspberry-pi-viewer',
  templateUrl: './raspberry-pi-viewer.component.html',
  styleUrls: ['./raspberry-pi-viewer.component.scss']
})
export class RaspberryPiViewerComponent {

  @Output() metricEvent: EventEmitter<void> = new EventEmitter<void>();

  constructor() { }

  onSubmit(){
    this.metricEvent.emit();
  }

}
