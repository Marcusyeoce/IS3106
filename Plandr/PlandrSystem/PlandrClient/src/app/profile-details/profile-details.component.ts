import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../session.service';
import { Member } from '../member';

@Component({
  selector: 'app-profile-details',
  templateUrl: './profile-details.component.html',
  styleUrls: ['./profile-details.component.css']
})
export class ProfileDetailsComponent implements OnInit {

  member: Member;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService) { }

  ngOnInit() {
    this.member = this.sessionService.getCurrentMember();
  }

}
