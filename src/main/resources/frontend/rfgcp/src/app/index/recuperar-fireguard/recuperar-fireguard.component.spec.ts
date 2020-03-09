import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecuperarFireguardComponent } from './recuperar-fireguard.component';

describe('RecuperarFireguardComponent', () => {
  let component: RecuperarFireguardComponent;
  let fixture: ComponentFixture<RecuperarFireguardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecuperarFireguardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecuperarFireguardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
