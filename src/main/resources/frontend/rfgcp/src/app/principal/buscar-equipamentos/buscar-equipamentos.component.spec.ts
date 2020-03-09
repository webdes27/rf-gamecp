import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BuscarEquipamentosComponent } from './buscar-equipamentos.component';

describe('BuscarEquipamentosComponent', () => {
  let component: BuscarEquipamentosComponent;
  let fixture: ComponentFixture<BuscarEquipamentosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BuscarEquipamentosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BuscarEquipamentosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
