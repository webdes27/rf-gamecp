import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TopOnlineComponent } from './top-online.component';

describe('TopOnlineComponent', () => {
  let component: TopOnlineComponent;
  let fixture: ComponentFixture<TopOnlineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TopOnlineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TopOnlineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
