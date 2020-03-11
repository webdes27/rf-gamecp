import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemInventarioComponent } from './item-inventario.component';

describe('ItemInventarioComponent', () => {
  let component: ItemInventarioComponent;
  let fixture: ComponentFixture<ItemInventarioComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ItemInventarioComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ItemInventarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
