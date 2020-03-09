import { TestBed } from '@angular/core/testing';

import { PersonagemService } from './personagem.service';

describe('PersonagemService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PersonagemService = TestBed.get(PersonagemService);
    expect(service).toBeTruthy();
  });
});
