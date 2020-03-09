import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalhePersonagemComponent } from './detalhe-personagem.component';

describe('DetalhePersonagemComponent', () => {
  let component: DetalhePersonagemComponent;
  let fixture: ComponentFixture<DetalhePersonagemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetalhePersonagemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetalhePersonagemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
