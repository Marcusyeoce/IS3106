import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { AttractionService } from '../attraction.service';
import { ReviewService } from '../review.service';
import { Attraction } from '../attraction';
import { Review } from '../review';

@Component({
  selector: 'app-write-review',
  templateUrl: './write-review.component.html',
  styleUrls: ['./write-review.component.css']
})
export class WriteReviewComponent implements OnInit {

  submitted: boolean;
  attractionId: number;
  attraction: Attraction;
  newReview: Review;
  message: string;

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private attractionService: AttractionService, private reviewService: ReviewService) 
  { 
    this.submitted = false;
    this.newReview = new Review();
  }

  ngOnInit() {
    this.attractionId = parseInt(this.activatedRoute.snapshot.paramMap.get('attractionId'));
  }

  writeReview(reviewForm: NgForm) 
  {
    this.submitted = true;

    if (reviewForm.valid)
    {
      this.reviewService.createNewReview(this.newReview, this.attractionId);
      this.message = "Successfully made review"
    }
  }

}
