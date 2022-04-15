import { TestBed } from '@angular/core/testing';

import { BaraBackendWrapperService } from './bara-backend-wrapper.service';

describe('BaraBackendWrapperService', () => {
  let service: BaraBackendWrapperService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BaraBackendWrapperService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
