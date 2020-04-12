import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAllBookingsComponent } from './view-all-bookings.component';

describe('ViewAllBookingsComponent', () => {
  let component: ViewAllBookingsComponent;
  let fixture: ComponentFixture<ViewAllBookingsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewAllBookingsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewAllBookingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
