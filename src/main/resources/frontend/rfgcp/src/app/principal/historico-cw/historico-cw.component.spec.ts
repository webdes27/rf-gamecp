import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoricoCwComponent } from './historico-cw.component';

describe('HistoricoCwComponent', () => {
  let component: HistoricoCwComponent;
  let fixture: ComponentFixture<HistoricoCwComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HistoricoCwComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HistoricoCwComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
