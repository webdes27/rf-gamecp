import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaBanidosComponent } from './lista-banidos.component';

describe('ListaBanidosComponent', () => {
  let component: ListaBanidosComponent;
  let fixture: ComponentFixture<ListaBanidosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListaBanidosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListaBanidosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
