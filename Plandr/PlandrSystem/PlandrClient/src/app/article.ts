export class Article {
  articleId: number;
  title: string;
  picture: string;
  content: string;
  publishedDate: Date;

  constructor(
    articleId?: number,
    title?: string,
    picture?: string,
    content?: string,
    publishedDate?: Date
  ) {
    this.articleId = articleId;
    this.title = title;
    this.picture = picture;
    this.content = content;
    this.publishedDate = publishedDate;
  }
}
