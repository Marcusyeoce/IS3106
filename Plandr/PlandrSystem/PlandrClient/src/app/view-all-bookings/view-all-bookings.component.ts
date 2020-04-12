import { Component, OnInit } from '@angular/core';

import { BookingService } from '../booking.service';
import { Booking } from '../booking';

@Component({
  selector: 'app-view-all-bookings',
  templateUrl: './view-all-bookings.component.html',
  styleUrls: ['./view-all-bookings.component.css']
})
export class ViewAllBookingsComponent implements OnInit {

  bookings: Booking[];

  constructor(private bookingService: BookingService) 
  {
  }

  ngOnInit() 
  {
    this.bookings = this.bookingService.getBookings();
  }

}
