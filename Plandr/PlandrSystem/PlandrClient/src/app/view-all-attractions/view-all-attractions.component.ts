import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../session.service';
import { AttractionService } from '../attraction.service';
import { Attraction } from '../attraction';

@Component({
  selector: 'app-view-all-attractions',
  templateUrl: './view-all-attractions.component.html',
  styleUrls: ['./view-all-attractions.component.css']
})

export class ViewAllAttractionsComponent implements OnInit {

  attractions: Attraction[];
  errorMessage: string;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private attractionService: AttractionService) { }

  ngOnInit() {
    this.attractionService.getAttractions().subscribe(
      response => {
        this.attractions = response.attractions;
      },
      error => {
        this.errorMessage = error;
        console.log('********** ViewAllAttractionsComponent.ts: ' + error);
      }
    );
  }

}
