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
  attractionIds: number[];
  newBooking: Booking;

  message: string;

  constructor(private router: Router, private bookingService: BookingService) 
  { 
    this.submitted = false;
    this.newBooking = new Booking();
  }

  ngOnInit() 
  {
    //to test
    this.attractionIds = [1, 2];
    this.message = "Insert Message here e.g. booking made $123 paid etc";
  }

  clear()
  {
    this.submitted = false;
    this.newBooking = new Booking();
  }

  generate(generateBookingForm: NgForm)
  {
  }

  create(createBookingForm: NgForm) 
  {
    this.submitted = true;

    if (createBookingForm.valid) 
    {
      this.bookingService.createNewBooking(this.newBooking, this.attractionIds);
      this.router.navigate(["/profile"]);
    }
  }
}
