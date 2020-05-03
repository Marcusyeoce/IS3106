import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { SessionService } from '../session.service';
import { AttractionService } from '../attraction.service';
import { TagService } from '../tag.service';
import { Attraction } from '../attraction';
import { Tag } from '../tag';

@Component({
  selector: 'app-search-attractions',
  templateUrl: './search-attractions.component.html',
  styleUrls: ['./search-attractions.component.css']
})

export class SearchAttractionsComponent implements OnInit {
  submitted: boolean;
  searchParam: string;
  tagIds: string[];
  priceLimit: number;
  visitDate: Date;
  visitTime: Date;

  tags: Tag[];
  attractions: Attraction[];
  
  resultSuccess: boolean;
  resultError: boolean;
  errorMessage: string;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    private sessionService: SessionService,
    private attractionService: AttractionService,
    private tagService: TagService) 
  { 
    this.submitted = false;
    this.tagIds = new Array;
    this.searchParam = "";

    this.resultSuccess = false;
    this.resultError = false;
  }

  ngOnInit() {
    this.tagService.getTags().subscribe(
			response => {
				this.tags = response.tags;
			},
			error => {
				console.log('********** SearchAttractionsComponent.ts: ' + error);
			}
		);
  }

  search(searchAttractionsForm: NgForm) {
    let longTagIds: number[] = new Array();
		
		for(var i = 0; i < this.tagIds.length; i++)
		{
			longTagIds.push(parseInt(this.tagIds[i]));
		}			
	
    this.submitted = true;
    
    if (searchAttractionsForm.valid) {
      this.attractionService.searchAttractions(this.searchParam, longTagIds, this.priceLimit, this.visitDate.toString(), this.visitTime.toString()).subscribe(
        response => {
          this.resultSuccess = true;
          this.resultError = false;
          this.attractions = response.attractions;
        },
        error => {
          this.resultSuccess = false;
          this.resultError = true;
          this.errorMessage = "An error has occurred while searching: " + error;

          console.log('********** SearchAttractionsComponent.ts: ' + error);
        }
      );
    }
  }

}
