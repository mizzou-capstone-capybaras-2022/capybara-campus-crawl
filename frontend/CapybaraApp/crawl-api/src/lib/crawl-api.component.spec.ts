import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrawlApiComponent } from './crawl-api.component';

describe('CrawlApiComponent', () => {
  let component: CrawlApiComponent;
  let fixture: ComponentFixture<CrawlApiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CrawlApiComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CrawlApiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
