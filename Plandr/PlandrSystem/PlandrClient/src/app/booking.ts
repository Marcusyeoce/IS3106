export class Booking 
{
    bookingId: number;
    bookingDate: string;
    totalPrice: number;
    bookingDescription: string;

    constructor(bookingId?: number, bookingDate?: string, totalPrice?: number, bookingDescription?: string)
    {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.totalPrice = totalPrice;
        this.bookingDescription = bookingDescription;
    }
}
