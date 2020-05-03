import { Attraction } from "./attraction";

export class Booking {
  bookingId: number;
  bookingDate: Date;
  paymentDate: Date;
  totalPrice: number;
  description: string;
  cancelled: boolean;

  attractionEntities: Attraction[];

  constructor(
    bookingId?: number,
    bookingDate?: Date,
    paymentDate?: Date,
    totalPrice?: number,
    description?: string,
    cancelled?: boolean
  ) {
    this.bookingId = bookingId;
    this.bookingDate = bookingDate;
    this.paymentDate = paymentDate;
    this.totalPrice = totalPrice;
    this.description = description;
    this.cancelled = cancelled;
  }
}
