import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../session.service';
import { BookingService } from '../booking.service';
import { Booking } from '../booking';

@Component({
  selector: 'app-view-all-bookings',
  templateUrl: './view-all-bookings.component.html',
  styleUrls: ['./view-all-bookings.component.css']
})

export class ViewAllBookingsComponent implements OnInit {
  bookings: Booking[];
  errorMessage: string;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private bookingService: BookingService) { }

  ngOnInit() {
    this.bookingService.getBookings().subscribe(
      response => {
        this.bookings = response.bookings;
      },
      error => {
        this.errorMessage = error;
        console.log('********** ViewAllBookingsComponent.ts: ' + error);
      }
    );
  }

  parseDate(d: Date)
	{		
		return d.toString().replace('[UTC]', '');
	}

}
