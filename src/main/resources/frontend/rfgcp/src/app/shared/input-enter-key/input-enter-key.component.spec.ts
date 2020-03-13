import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InputEnterKeyComponent } from './input-enter-key.component';

describe('InputEnterKeyComponent', () => {
  let component: InputEnterKeyComponent;
  let fixture: ComponentFixture<InputEnterKeyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InputEnterKeyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InputEnterKeyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
