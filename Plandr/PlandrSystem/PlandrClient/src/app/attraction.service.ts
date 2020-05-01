import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
} from "@angular/common/http";
import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/operators";

import { SessionService } from "./session.service";
import { Attraction } from "./attraction";

@Injectable({
  providedIn: 'root'
})
export class AttractionService {
  baseUrl: string = "/api/Attraction";

  constructor(
    private httpClient: HttpClient,
    private sessionService: SessionService
  ) {}

  getAttractions(): Observable<any> {
    return this.httpClient
      .get<any>(this.baseUrl + "/retrieveAllAttractions")
      .pipe(catchError(this.handleError));
  }

  getAttractionByAttractionId(attractionId: number): Observable<any> {
    return this.httpClient
      .get<any>(this.baseUrl + "/retrieveAttraction/" + attractionId)
      .pipe(catchError(this.handleError));
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