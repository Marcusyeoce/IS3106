import { Injectable } from '@angular/core';

import { SessionService } from './session.service';
import { Booking } from './booking';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  bookings: Booking[];

  constructor(private sessionService: SessionService) 
  { 
    this.bookings = this.sessionService.getBookings();

    if (this.bookings == null)
    {
      this.bookings = new Array();

      let booking: Booking;

      booking = new Booking(1, "01/01/2020", 10, "NIL");
      this.bookings.push(booking);

      booking = new Booking(2, "01/01/2020", 20, "NIL");
      this.bookings.push(booking);

      booking = new Booking(3, "01/01/2020", 30, "NIL");
      this.bookings.push(booking);

      booking = new Booking(4, "01/01/2020", 40, "NIL");
      this.bookings.push(booking);

      booking = new Booking(5, "01/01/2020", 50, "NIL");
      this.bookings.push(booking);

      this.sessionService.setBookings(this.bookings);
    }
  }

  getBookings() 
  {
    return this.bookings;
  }

  createNewBooking(newBooking: Booking)
  {
    let booking: Booking = new Booking();
    booking.bookingId = newBooking.bookingId;
    booking.bookingDate = newBooking.bookingDate;
    booking.totalPrice = newBooking.totalPrice;
    booking.bookingDescription = newBooking.bookingDescription;

    this.bookings.push(booking);

    this.sessionService.setBookings(this.bookings);
  }
}
