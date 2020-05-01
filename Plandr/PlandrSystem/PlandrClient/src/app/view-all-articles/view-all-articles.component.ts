import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../session.service';
import { ArticleService } from '../article.service';
import { Article } from '../article';

@Component({
  selector: 'app-view-all-articles',
  templateUrl: './view-all-articles.component.html',
  styleUrls: ['./view-all-articles.component.css']
})

export class ViewAllArticlesComponent implements OnInit {
  articles: Article[];

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private articleService: ArticleService) { }

  ngOnInit() {
    this.articleService.getArticles().subscribe(
      response => {
        this.articles = response.articles;
      },
      error => {
        console.log('********** ViewAllArticlesComponent.ts: ' + error);
      }
    );
  }

}
