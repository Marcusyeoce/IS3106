import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { BookingService } from '../booking.service';
import { Booking } from '../booking';

@Component({
  selector: 'app-make-booking',
  templateUrl: './make-booking.component.html',
  styleUrls: ['./make-booking.component.css']
})
export class MakeBookingComponent implements OnInit {

  submitted: boolean;
  newBooking: Booking;

  constructor(private router: Router, private bookingService: BookingService) 
  { 
    this.submitted = false;
    this.newBooking = new Booking();
  }

  ngOnInit() 
  {
  }

  clear()
  {
    this.submitted = false;
    this.newBooking = new Booking();
  }

  create(createBookingForm: NgForm) 
  {
    this.submitted = true;

    if (createBookingForm.valid) 
    {
      this.bookingService.createNewBooking(this.newBooking);
      this.router.navigate(["/profile"]);
    }
  }
}
