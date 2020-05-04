import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../session.service';
import { BookingService } from '../booking.service';
import { Booking } from '../booking';
import { Attraction } from '../attraction';

@Component({
  selector: 'app-view-booking',
  templateUrl: './view-booking.component.html',
  styleUrls: ['./view-booking.component.css']
})

export class ViewBookingComponent implements OnInit {
  
  bookingId: number;
  bookingToView: Booking;
  retrieveBookingError: boolean;
  cancelProcess: boolean;
  message: string;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private bookingService: BookingService) 
  { 
    this.retrieveBookingError = false;
    this.cancelProcess = false;
  }

  ngOnInit() {
    this.bookingId = parseInt(this.activatedRoute.snapshot.paramMap.get('bookingId'));

    this.bookingService.getBookingByBookingId(this.bookingId).subscribe(
      response => {
        this.bookingToView = response.booking;
      },
      error => {
        this.retrieveBookingError = true;
        console.log('********** ViewBookingComponent.ts: ' + error);
      }
    );
  }

  parseDate(d: Date)
	{		
		return d.toString().replace('[UTC]', '');
  }
  
  cancelProc() {
    this.cancelProcess = !this.cancelProcess;
  }

  cancel() {
    this.bookingService.cancelBooking(this.bookingId).subscribe(
      response => {
        this.bookingToView.cancelled = true;

        this.message = "Your booking has been refunded."
      },
      error => {
        this.message = error;
      }
    )
  }

}
