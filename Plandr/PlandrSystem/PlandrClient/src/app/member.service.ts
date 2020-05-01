import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpErrorResponse } from "@angular/common/http";
import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/operators";

import { SessionService } from "./session.service";
import { Member } from "./member";

const httpOptions = {
  headers: new HttpHeaders({ "Content-Type": "application/json" }),
};

@Injectable({
  providedIn: "root",
})
export class MemberService {
  baseUrl: string = "/api/Member";

  constructor(
    private httpClient: HttpClient,
    private sessionService: SessionService
  ) {}

  memberLogin(username: string, password: string): Observable<any> {
    return this.httpClient
      .get<any>(
        this.baseUrl +
          "/memberLogin?username=" +
          username +
          "&password=" +
          password
      )
      .pipe(catchError(this.handleError));
  }

  registerMember(newMember: Member): Observable<any> {
    let createMemberReq = { "member": newMember };

    return this.httpClient
      .put<any>(this.baseUrl + "/registerMember", createMemberReq, httpOptions)
      .pipe(catchError(this.handleError));
  }

  updateProfile(memberToUpdate: Member): Observable<any> {
    let updateMemberReq = {
      "username": this.sessionService.getUsername(),
      "password": this.sessionService.getPassword(),
      "member": memberToUpdate
    };

    return this.httpClient
      .post<any>(this.baseUrl + "/updateProfile", updateMemberReq, httpOptions)
      .pipe(catchError(this.handleError));
  }

  updateMemberPassword(newPassword: string, rePassword: string): Observable<any> {
    let updateMemberPasswordReq = {
      "username": this.sessionService.getUsername(),
      "password": this.sessionService.getPassword(),
      "newPassword": newPassword,
      "rePassword": rePassword
    };

    return this.httpClient
      .post<any>(
        this.baseUrl + "/updateMemberPassword",
        updateMemberPasswordReq,
        httpOptions
      )
      .pipe(catchError(this.handleError));
  }

  memberSubscribe(subPackage: number): Observable<any> {
    let memberSubscribeReq = {
      "username": this.sessionService.getUsername(),
      "password": this.sessionService.getPassword(),
      "subPackage": subPackage
    };

    return this.httpClient.post<any>(this.baseUrl + "/memberSubscribe", memberSubscribeReq, httpOptions).pipe
		(
			catchError(this.handleError)
		);
  }

  // getMembers(): Member[] {
  //   return this.members;
  // }

  // createNewMember(newMember: Member) {
  //   let member: Member = new Member();
  //   member.memberId = newMember.memberId;
  //   member.name = newMember.name;
  //   member.email = newMember.email;
  //   member.username = newMember.username;
  //   member.password = newMember.password;
  //   member.gender = newMember.gender;
  //   member.dob = newMember.dob;
  //   member.subscribed = newMember.subscribed;
  // }

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
