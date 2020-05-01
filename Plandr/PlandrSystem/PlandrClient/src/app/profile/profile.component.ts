import { Component, OnInit } from '@angular/core';

import { BookingService } from '../booking.service';
import { Booking } from '../booking';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  bookings: Booking[];

  constructor(private bookingService: BookingService) 
  { 
  }

  ngOnInit() 
  {
    //this.bookings = this.bookingService.getBookings();
  }

}
