import { Injectable } from "@angular/core";

import { Booking } from "./booking";
import { Member } from "./member";
import { GenderEnum } from "./gender-enum.enum";

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

  getSubscribedUntil(): Date {
    return sessionStorage.subscribedUntil;
  }

  setSubscribedUntil(subscribedUntil: Date): void {
    sessionStorage.subscribedUntil = subscribedUntil;
  }

  getName(): string {
    return sessionStorage.name;
  }

  setName(name: string): void {
    sessionStorage.name = name;
  }

  getEmail(): string {
    return sessionStorage.email;
  }
 
  setEmail(email: string): void {
    sessionStorage.email = email;
  }

  getContactNumber(): number {
    return sessionStorage.contactNumber;
  }

  setContactNumber(contactNumber: number): void {
    sessionStorage.contactNumber = contactNumber;
  }

  getGender(): GenderEnum {
    return sessionStorage.gender;
  }

  setGender(gender: GenderEnum): void {
    sessionStorage.gender = gender;
  }

  getDob(): Date {
    return sessionStorage.dob;
  }

  setDob(dob: Date): void {
    sessionStorage.dob = dob;
  }

  getCreditCard(): string {
    return sessionStorage.creditCard;
  }

  setCreditCard(creditCard: string): void {
    sessionStorage.creditCard = creditCard;
  }

  getSubscribed(): boolean {
    if (sessionStorage.subscribed == "true") {
      return true;
    } else {
      return false;
    }
  }

  setSubscribed(subscribed: boolean): void {
    sessionStorage.subscribed = subscribed;
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
