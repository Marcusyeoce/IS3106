import { Injectable } from '@angular/core';

import { Booking } from './booking';
import { Member } from './member';

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

  getMembers(): Member[]
  {
    try 
    {
      return JSON.parse(sessionStorage.members);
    }
    catch
    {
      return null;
    }
  }

  setMembers(members: Member[]): void
  {
    sessionStorage.bookings = JSON.stringify(members);
  }
}
