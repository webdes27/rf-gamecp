import { TestBed } from '@angular/core/testing';

import { InfoService } from './info-service.service';

describe('InfoServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: InfoService = TestBed.get(InfoService);
    expect(service).toBeTruthy();
  });
});
