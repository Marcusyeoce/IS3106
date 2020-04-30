import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SessionService } from './session.service';
import { Review } from './review';

const httpOptions = {
  headers: new HttpHeaders({ "Content-Type": "application/json" }),
};

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  baseUrl: string = "/api/Review";

  constructor(private httpClient: HttpClient, private sessionService: SessionService) { }

  createNewReview(newReview: Review, attractionId: number): Observable<any> {
    let createReviewReq = {
      "username": this.sessionService.getUsername(),
      "password": this.sessionService.getPassword(),
      "review": newReview,
      "attractionId": attractionId
    };

    return this.httpClient.put<any>(this.baseUrl + "/createReview", createReviewReq, httpOptions).pipe(catchError(this.handleError));
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
