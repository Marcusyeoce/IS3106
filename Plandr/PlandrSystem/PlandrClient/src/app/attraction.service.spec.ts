import { TestBed } from '@angular/core/testing';

import { AttractionService } from './attraction.service';

describe('AttractionService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AttractionService = TestBed.get(AttractionService);
    expect(service).toBeTruthy();
  });
});
