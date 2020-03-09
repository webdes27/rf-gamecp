import { TestBed } from '@angular/core/testing';

import { InfoServiceService } from './info-service.service';

describe('InfoServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: InfoServiceService = TestBed.get(InfoServiceService);
    expect(service).toBeTruthy();
  });
});
