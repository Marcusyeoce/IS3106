import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNewBookingComponent } from './create-new-booking.component';

describe('CreateNewBookingComponent', () => {
  let component: CreateNewBookingComponent;
  let fixture: ComponentFixture<CreateNewBookingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateNewBookingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewBookingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
