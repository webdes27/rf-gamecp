import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaConcelhoComponent } from './lista-concelho.component';

describe('ListaConcelhoComponent', () => {
  let component: ListaConcelhoComponent;
  let fixture: ComponentFixture<ListaConcelhoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListaConcelhoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListaConcelhoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
