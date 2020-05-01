import { Injectable } from "@angular/core";
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
} from "@angular/common/http";
import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/operators";

import { SessionService } from "./session.service";
import { Article } from "./article";

@Injectable({
  providedIn: "root",
})
export class ArticleService {
  baseUrl: string = "/api/Article";

  constructor(
    private httpClient: HttpClient,
    private sessionService: SessionService
  ) {}

  getArticles(): Observable<any> {
    return this.httpClient
      .get<any>(this.baseUrl + "/retrieveAllArticles")
      .pipe(catchError(this.handleError));
  }

  getArticleByArticleId(articleId: number): Observable<any> {
    return this.httpClient
      .get<any>(this.baseUrl + "/retrieveArticle/" + articleId)
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
