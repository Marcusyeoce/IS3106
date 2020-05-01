import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';
import { Booking } from "./booking";

const httpOptions = {
  headers: new HttpHeaders({ "Content-Type": "application/json" }),
};

@Injectable({
  providedIn: "root",
})
export class BookingService {
  baseUrl: string = "/api/Booking";

  // bookings: Booking[];

  constructor(private httpClient: HttpClient, private sessionService: SessionService) {
    // this.bookings = this.sessionService.getBookings();

    // if (this.bookings == null) {
    //   this.bookings = new Array();

    //   let booking: Booking;

    //   booking = new Booking(1, new Date("01/01/2020"), 10, "NIL");
    //   this.bookings.push(booking);

    //   booking = new Booking(2, new Date("01/01/2020"), 20, "NIL");
    //   this.bookings.push(booking);

    //   booking = new Booking(3, new Date("01/01/2020"), 30, "NIL");
    //   this.bookings.push(booking);

    //   booking = new Booking(4, new Date("01/01/2020"), 40, "NIL");
    //   this.bookings.push(booking);

    //   booking = new Booking(5, new Date("01/01/2020"), 50, "NIL");
    //   this.bookings.push(booking);

    //   this.sessionService.setBookings(this.bookings);
    // }
  }

  getBookings(): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveBookingsByMember?username=" + this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
		(
			catchError(this.handleError)
		);
  }

  getBookingByBookingId(bookingId: number): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveBooking/" + bookingId + "?username=" + 
    this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword()).pipe
		(
			catchError(this.handleError)
		);
  }

  createNewBooking(newBooking: Booking, attractionIds: number[]) {
    let createBookingReq = {
      "username": this.sessionService.getUsername(),
      "password": this.sessionService.getPassword(),
      "booking": newBooking,
      "attractionIds": attractionIds
    };

    return this.httpClient.put<any>(this.baseUrl + "/createBooking", createBookingReq, httpOptions).pipe(catchError(this.handleError));

    // let booking: Booking = new Booking();
    // booking.bookingId = newBooking.bookingId;
    // booking.bookingDate = newBooking.bookingDate;
    // booking.totalPrice = newBooking.totalPrice;
    // booking.description = newBooking.description;

    // this.bookings.push(booking);

    // this.sessionService.setBookings(this.bookings);
  }

  cancelBooking(bookingId: number): Observable<any> {
    return this.httpClient.post<any>(this.baseUrl + "/cancelBooking/" + bookingId + "?username=" + 
    this.sessionService.getUsername() + "&password=" + this.sessionService.getPassword(), null).pipe
		(
			catchError(this.handleError)
		);
  }
  
  private handleError(error: HttpErrorResponse) {
    let errorMessage: string = "";

    if (error.error instanceof ErrorEvent) {
      errorMessage = "An unknown error has occurred: " + error.error.message;
    } else {
      errorMessage =
        "A HTTP error has occurred: " +
        `HTTP ${error.status}: ${error.error.message}`;
    }

    console.error(errorMessage);

    return throwError(errorMessage);
  }
}
