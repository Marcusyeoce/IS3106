import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../session.service';
import { AttractionService } from '../attraction.service';
import { Attraction } from '../attraction';

@Component({
  selector: 'app-view-attraction',
  templateUrl: './view-attraction.component.html',
  styleUrls: ['./view-attraction.component.css']
})

export class ViewAttractionComponent implements OnInit {

  attractionId: number;
  attractionToView: Attraction;
  retrieveAttractionError: boolean;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private attractionService: AttractionService) 
  {
    this.retrieveAttractionError = false;
  }

  ngOnInit() {

    this.attractionId = parseInt(this.activatedRoute.snapshot.paramMap.get('attractionId'));

    this.attractionService.getAttractionByAttractionId(this.attractionId).subscribe(
      response => {
        this.attractionToView = response.attraction;
      },
      error => {
        this.retrieveAttractionError = true;
        console.log('********** ViewAttractionComponent.ts: ' + error);
      }
    );
  }


}
