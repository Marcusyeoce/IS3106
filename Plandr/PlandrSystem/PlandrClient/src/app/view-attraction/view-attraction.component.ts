import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-view-attraction',
  templateUrl: './view-attraction.component.html',
  styleUrls: ['./view-attraction.component.css']
})
export class ViewAttractionComponent implements OnInit {

  id: string;

  constructor(private activatedRoute: ActivatedRoute) 
  { 
  }

  ngOnInit() {
    this.id = this.activatedRoute.snapshot.paramMap.get('attractionId');
  }

}
