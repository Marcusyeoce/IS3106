import { Component, OnInit } from '@angular/core';

import { SessionService } from '../session.service';
import { ArticleService } from '../article.service';
import { Article } from '../article';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})

export class MainComponent implements OnInit {
  articles: Article[];
  errorMessage: string;

  constructor(public sessionService: SessionService, private articleService: ArticleService) { }

  ngOnInit() {
    this.articleService.getArticles().subscribe(
      response => {
        this.articles = response.articles;
      },
      error => {
        this.errorMessage = error;
        console.log('********** ViewAllArticlesComponent.ts: ' + error);
      }
    );
  }

  parseDate(d: Date)
	{		
		return d.toString().replace('[UTC]', '');
	}

}
