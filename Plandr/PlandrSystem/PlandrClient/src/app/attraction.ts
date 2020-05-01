import { Tag } from "./tag";
import { Promotion } from "./promotion";
import { Review } from "./review";

export class Attraction {
  attractionId: number;
  name: string;
  description: string;
  location: string;
  picture: string;
  startTimestamp: Date;
  endTimestamp: Date;

  promotions: Promotion[];
  tags: Tag[];
  reviews: Review[];

  constructor(
    attractionId?: number,
    name?: string,
    description?: string,
    location?: string,
    picture?: string,
    startTimestamp?: Date,
    endTimestamp?: Date
  ) {
    this.attractionId = attractionId;
    this.name = name;
    this.description = description;
    this.location = location;
    this.picture = picture;
    this.startTimestamp = startTimestamp;
    this.endTimestamp = endTimestamp;
  }
}
