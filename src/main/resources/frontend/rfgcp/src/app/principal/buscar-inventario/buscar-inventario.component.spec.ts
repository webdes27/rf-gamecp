import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BuscarInventarioComponent } from './buscar-inventario.component';

describe('BuscarInventarioComponent', () => {
  let component: BuscarInventarioComponent;
  let fixture: ComponentFixture<BuscarInventarioComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BuscarInventarioComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BuscarInventarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
