import { Injectable } from "@angular/core";

import { Booking } from "./booking";
import { Member } from "./member";

@Injectable({
  providedIn: "root",
})
export class SessionService {
  constructor() {}

  getIsLogin(): boolean {
    if (sessionStorage.isLogin == "true") {
      return true;
    } else {
      return false;
    }
  }

  setIsLogin(isLogin: boolean): void {
    sessionStorage.isLogin = isLogin;
  }

  getCurrentMember(): Member {
    return JSON.parse(sessionStorage.currentMember);
  }

  setCurrentMember(currentMember: Member): void {
    sessionStorage.currentMember = JSON.stringify(currentMember);
  }

  getUsername(): string {
    return sessionStorage.username;
  }

  setUsername(username: string): void {
    sessionStorage.username = username;
  }

  getPassword(): string {
    return sessionStorage.password;
  }

  setPassword(password: string): void {
    sessionStorage.password = password;
  }

  getBookings(): Booking[] {
    try {
      return JSON.parse(sessionStorage.bookings);
    } catch {
      return null;
    }
  }

  setBookings(bookings: Booking[]): void {
    sessionStorage.bookings = JSON.stringify(bookings);
  }

  // getMembers(): Member[]
  // {
  //   try
  //   {
  //     return JSON.parse(sessionStorage.members);
  //   }
  //   catch
  //   {
  //     return null;
  //   }
  // }

  // setMembers(members: Member[]): void
  // {
  //   sessionStorage.bookings = JSON.stringify(members);
  // }
}
