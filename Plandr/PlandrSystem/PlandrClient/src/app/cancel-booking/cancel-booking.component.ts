import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { SessionService } from '../session.service';
import { BookingService } from '../booking.service';
import { Booking } from '../booking';

@Component({
  selector: 'app-cancel-booking',
  templateUrl: './cancel-booking.component.html',
  styleUrls: ['./cancel-booking.component.css']
})

export class CancelBookingComponent implements OnInit {
  bookingId: number;
  bookingToCancel: Booking;
  error: boolean;
  errorMessage: string;
  message: string;

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              public sessionService: SessionService,
              private bookingService: BookingService)
  {
    this.error = false;
  }

  ngOnInit() {
    this.bookingId = parseInt(this.activatedRoute.snapshot.paramMap.get('bookingId'));

    this.bookingService.getBookingByBookingId(this.bookingId).subscribe(
      response => {
        this.bookingToCancel = response.booking;
        this.error = false;
      },
      error => {
        this.error = true;
        this.errorMessage = error;
        console.log('********** ViewBookingComponent.ts: ' + error);
      }
    );
  }

  cancel() {
    this.bookingService.cancelBooking(this.bookingId).subscribe(
      response => {
        this.message = "Booking " + this.bookingId + " cancelled successfully";
      },
      error => {
        this.error = true;
        this.errorMessage = error;
        this.message = "An error has occurred while cancelling: " + error;
        console.log('********** CancelBookingComponent.ts: ' + error);
      }
    );
  }

}
