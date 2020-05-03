import { Tag } from "./tag";
import { Promotion } from "./promotion";
import { Review } from "./review";

export class Attraction {
  attractionId: number;
  name: string;
  description: string;
  location: string;
  picture: string;
  unitPrice: number;
  startTimestamp: Date;
  endTimestamp: Date;
  unitPrice: number;

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
    startTimestamp?: Date,
    endTimestamp?: Date,
    unitPrice?: number
  ) {
    this.attractionId = attractionId;
    this.name = name;
    this.description = description;
    this.location = location;
    this.picture = picture;
    this.unitPrice = unitPrice;
    this.startTimestamp = startTimestamp;
    this.endTimestamp = endTimestamp;
    this.unitPrice = unitPrice;
  }
}
