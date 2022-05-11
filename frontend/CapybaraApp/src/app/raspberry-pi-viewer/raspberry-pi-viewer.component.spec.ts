import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RaspberryPiViewerComponent } from './raspberry-pi-viewer.component';

describe('RaspberryPiViewerComponent', () => {
  let component: RaspberryPiViewerComponent;
  let fixture: ComponentFixture<RaspberryPiViewerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RaspberryPiViewerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RaspberryPiViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
