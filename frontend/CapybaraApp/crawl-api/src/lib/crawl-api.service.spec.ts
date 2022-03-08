import { TestBed } from '@angular/core/testing';

import { CrawlApiService } from './crawl-api.service';

describe('CrawlApiService', () => {
  let service: CrawlApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CrawlApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
