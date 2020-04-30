export class Review {
  reviewId: number;
  content: string;
  publishedDate: Date;

  constructor(reviewId?: number, content?: string, publishedDate?: Date) {
    this.reviewId = reviewId;
    this.content = content;
    this.publishedDate = publishedDate;
  }
}
