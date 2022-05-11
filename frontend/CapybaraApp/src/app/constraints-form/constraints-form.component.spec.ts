import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConstraintsFormComponent } from './constraints-form.component';

describe('ConstraintsFormComponent', () => {
  let component: ConstraintsFormComponent;
  let fixture: ComponentFixture<ConstraintsFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConstraintsFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConstraintsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
