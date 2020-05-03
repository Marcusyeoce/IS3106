import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { BookingService } from '../booking.service';
import { Booking } from '../booking';
import { AttractionService } from '../attraction.service';
import { Attraction } from '../attraction';

@Component({
  selector: 'app-make-booking',
  templateUrl: './make-booking.component.html',
  styleUrls: ['./make-booking.component.css']
})
export class MakeBookingComponent implements OnInit {

  generateSubmitted: boolean;
  bookingDate: Date;
  startTime: Date;
  endTime: Date;
  numPax: number;
  priceLimit: number;

  createSubmitted: boolean;

  attractionIds: number[];
  attractions: Attraction[];
  totalCost: number;
  newBooking: Booking;

  message: string;

  constructor(private router: Router, private bookingService: BookingService, private attractionService: AttractionService) 
  { 
    this.generateSubmitted = false;
    this.createSubmitted = false;
    this.newBooking = new Booking();
  }

  ngOnInit() 
  {
    //to test
    this.message = "Insert Message here e.g. booking made $123 paid etc";
  }

  generate(generateBookingForm: NgForm)
  {
    this.generateSubmitted = true;

    if (generateBookingForm.valid)
    {
      //attractions = this.attractionService.generateNewBooking(this.bookingDate, this.startTime, this.endTime, this.numPax, this.priceLimit);
      //this.totalCost = this.attractionService.calculateTotalCost(attractions, numPax);
    }

  }

  create(createBookingForm: NgForm) 
  {
    this.createSubmitted = true;

    if (createBookingForm.valid) 
    {
      this.bookingService.createNewBooking(this.newBooking, this.attractionIds);
    }
  }
}
