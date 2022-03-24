import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-component-add-constraints',
  templateUrl: './component-add-constraints.component.html',
  styleUrls: ['./component-add-constraints.component.scss']
})
export class ComponentAddConstraintsComponent implements OnInit {

  addConstraints = new FormGroup({
    indoor: new FormControl(''),
    food: new FormControl(''),
    time: new FormControl('')
  });

  constructor() { }

  ngOnInit(): void {
  }

  addConstraintsfn() {
    console.log(this.addConstraints.value);
  }
  isShown: boolean = false ;
  toggleShow() {

    this.isShown = ! this.isShown;
    
    }
}
