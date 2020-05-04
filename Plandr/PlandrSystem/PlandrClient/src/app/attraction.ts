import { Tag } from "./tag";
import { Promotion } from "./promotion";
import { Review } from "./review";
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';

export class Attraction {
  attractionId: number;
  name: string;
  description: string;
  location: string;
  picture: string;
  unitPrice: number;
  startDate: Date;
  endDate: Date;
  openingTime: Date;
  closingTime: Date;

  promotionEntities: Promotion[];
  tagEntities: Tag[];
  reviewEntities: Review[];

  constructor(
    attractionId?: number,
    name?: string,
    description?: string,
    location?: string,
    picture?: string,
    unitPrice?: number,
    startDate?: Date,
    endDate?: Date,
    openingTime?: Date,
    closingTime?: Date
  ) {
    this.attractionId = attractionId;
    this.name = name;
    this.description = description;
    this.location = location;
    this.picture = picture;
    this.unitPrice = unitPrice;
    this.startDate = startDate;
    this.endDate = endDate;
    this.openingTime = openingTime;
    this.closingTime = closingTime;
    this.unitPrice = unitPrice;
  }
}
