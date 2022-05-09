import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlacesViewerComponent } from './places-viewer.component';

describe('PlacesViewerComponent', () => {
  let component: PlacesViewerComponent;
  let fixture: ComponentFixture<PlacesViewerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PlacesViewerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PlacesViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
