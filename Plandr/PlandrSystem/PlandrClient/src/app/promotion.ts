export class Promotion {
  promotionId: number;
  name: string;
  startDate: Date;
  endDate: Date;
  discount: number;

  constructor(
    promotionId?: number,
    name?: string,
    startDate?: Date,
    endDate?: Date,
    discount?: number
  ) {
    this.promotionId = promotionId;
    this.name = name;
    this.startDate = startDate;
    this.endDate = endDate;
    this.discount = discount;
  }
}
