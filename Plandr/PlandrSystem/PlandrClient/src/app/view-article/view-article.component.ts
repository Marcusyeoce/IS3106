import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../session.service';
import { ArticleService } from '../article.service';
import { Article } from '../article';

@Component({
  selector: 'app-view-article',
  templateUrl: './view-article.component.html',
  styleUrls: ['./view-article.component.css']
})

export class ViewArticleComponent implements OnInit {
  
  articleId: number;
  articleToView: Article;
  retrieveArticleError: boolean;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private articleService: ArticleService) 
  {
    this.retrieveArticleError = false;
  }

  ngOnInit() {

    this.articleId = parseInt(this.activatedRoute.snapshot.paramMap.get('articleId'));

    this.articleService.getArticleByArticleId(this.articleId).subscribe(
      response => {
        this.articleToView = response.article;
      },
      error => {
        this.retrieveArticleError = true;
        console.log('********** ViewArticleComponent.ts: ' + error);
      }
    );
  }

  parseDate(d: Date)
	{		
		return d.toString().replace('[UTC]', '');
	}

}
