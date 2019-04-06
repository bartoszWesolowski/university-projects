import { TestBed, inject } from '@angular/core/testing';

import { CompareSetsService } from './compare-sets.service';

describe('CompareSetsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CompareSetsService]
    });
  });

  it('should be created', inject([CompareSetsService], (service: CompareSetsService) => {
    expect(service).toBeTruthy();
  }));
});
