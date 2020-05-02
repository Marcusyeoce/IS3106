import { GenderEnum } from "./gender-enum.enum";
import { Booking } from "./booking";

export class Member {
  memberId: number;
  name: string;
  email: string;
  contactNumber: number;
  username: string;
  password: string;
  gender: GenderEnum;
  dob: Date;
  subscribed: boolean;
  subscribedUntil: Date;
  creditCard: string;

  bookings: Booking[];

  constructor(
    memberId?: number,
    name?: string,
    email?: string,
    contactNumber?: number,
    username?: string,
    password?: string,
    gender?: GenderEnum,
    dob?: Date,
    subscribed?: boolean,
    subscribedUntil?: Date,
    creditCard?: string
  ) {
    this.memberId = memberId;
    this.name = name;
    this.email = email;
    this.contactNumber = contactNumber;
    this.username = username;
    this.password = password;
    this.gender = gender;
    this.dob = dob;
    this.subscribed = subscribed;
    this.subscribedUntil = subscribedUntil;
    this.creditCard = creditCard;
  }
}
