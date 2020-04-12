import { Injectable } from '@angular/core';

import { Booking } from './booking';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  constructor() 
  { 
  }

  getBookings(): Booking[]
  {
    try 
    {
      return JSON.parse(sessionStorage.bookings);
    }
    catch
    {
      return null;
    }
  }

  setBookings(bookings: Booking[]): void
  {
    sessionStorage.bookings = JSON.stringify(bookings);
  }
}
