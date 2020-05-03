import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { SessionService } from '../session.service';
import { BookingService } from '../booking.service';
import { Booking } from '../booking';
import { AttractionService } from '../attraction.service';
import { Attraction } from '../attraction';
import { TagService } from '../tag.service';
import { Tag } from '../tag';

@Component({
  selector: 'app-make-booking',
  templateUrl: './make-booking.component.html',
  styleUrls: ['./make-booking.component.css']
})
export class MakeBookingComponent implements OnInit {

  generateSubmitted: boolean;
  generateSuccess: boolean;
  bookingDate: Date;
  startTime: Date;
  endTime: Date;
  numPax: number;
  priceLimit: number;
  tagIds: string[];
  attractionIds: number[];

  errorBool: boolean; 
  createSubmitted: boolean;
  createSuccess: boolean;

  tags: Tag[];
  attractions: Attraction[];
  totalPrice: number;
  description: string;
  newBooking: Booking;

  message: string;

  constructor(private router: Router, private bookingService: BookingService, private attractionService: AttractionService,
              private tagService: TagService, private sessionService: SessionService, private activatedRoute: ActivatedRoute) 
  { 
    this.generateSubmitted = false;
    this.createSubmitted = false;

    this.errorBool = false;
    this.generateSuccess = false;
    this.createSuccess = false;

    this.tagIds = new Array();
    this.newBooking = new Booking();
  }

  ngOnInit() 
  {
    this.tagService.getTags().subscribe(
			response => {
				this.tags = response.tags;
			},
			error => {
				console.log('********** SearchAttractionsComponent.ts: ' + error);
			}
		);
  }

  generate(generateBookingForm: NgForm)
  {
    let longTagIds: number[] = new Array();
		
		for(var i = 0; i < this.tagIds.length; i++)
		{
			longTagIds.push(parseInt(this.tagIds[i]));
		}		

    this.generateSubmitted = true;

    if (generateBookingForm.valid)
    {
      this.bookingService.generateBooking(this.numPax, this.priceLimit, longTagIds, this.bookingDate.toString(), 
                                          this.startTime.toString(), this.endTime.toString()).subscribe(
        response => {
          this.generateSuccess = true;
          this.errorBool = false;
          this.attractions = response.attractions;
          this.attractionIds = response.attractionIds;
          this.totalPrice = response.totalTicketPrice;
          console.log(this.totalPrice);
          console.log(this.attractions);
          console.log(this.attractionIds);
        },
        error => {
          this.generateSuccess = false;
          this.errorBool = true;
          this.message = "An error has occurred while generating booking: " + error;

          console.log('********** MakeBookingComponent.ts: ' + error);
        }
      );
    }

  }

  create() {
    this.createSubmitted = true;
    let bookDate = new Date(this.bookingDate.toString() + "T00:00:00");
    this.newBooking.bookingDate = bookDate;
    this.newBooking.totalPrice = this.totalPrice;
    this.newBooking.description = this.description;

    this.bookingService.createNewBooking(this.newBooking, this.attractionIds).subscribe(
      response => {
        this.createSuccess = true;
        this.errorBool = false;
        this.message = "Booking created successfully. Booking ID: " + response.bookingId;
      },
      error => {
        this.createSuccess = false;
        this.errorBool = true;
        this.message = "An error has occurred while creating booking: " + error;

        console.log('********** MakeBookingComponent.ts: ' + error);
      }
    )
  }
}
