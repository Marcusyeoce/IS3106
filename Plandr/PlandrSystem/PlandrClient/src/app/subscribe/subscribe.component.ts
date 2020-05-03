import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { SessionService } from '../session.service';
import { MemberService } from '../member.service';
import { Member } from '../member';

@Component({
  selector: 'app-subscribe',
  templateUrl: './subscribe.component.html',
  styleUrls: ['./subscribe.component.css']
})

export class SubscribeComponent implements OnInit {

  memberToView: Member;
  submitted: boolean;
  subPackage: number;
  subMonths: number;
  subCost: number;

  resultSuccess: boolean;
	resultError: boolean;
	message: string;

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    public sessionService: SessionService,
    private memberService: MemberService)
  { 
    this.submitted = false;	
		this.resultSuccess = false;
		this.resultError = false;
  }

  ngOnInit() {
    this.memberToView = this.sessionService.getCurrentMember();
  }

  subscribe(subscribeForm: NgForm) {

    this.submitted = true;

    console.log(this.subPackage);

    if(subscribeForm.valid) { //this part?
      switch(Number(this.subPackage)) {
        case 1:
          this.subMonths = 1;
          this.subCost = 4.99;
        case 2:
          this.subMonths = 3;
          this.subCost = 9.99;
        case 3:
          this.subMonths = 6;
          this.subCost = 14.99;
        default:
          this.subMonths = 0;
          this.subCost = 0;
      }

      this.memberService.memberSubscribe(this.subPackage).subscribe(
        response => {
          this.sessionService.setSubscribed(true);
          this.sessionService.setSubscribedUntil(response.subscribedUntil);
          console.log(this.sessionService.getSubscribedUntil());

          this.resultSuccess = true;
          this.resultError = false;

					this.message = "Subscribed successfully for " + this.subMonths + " months, $" + this.subCost + " will be deducted from credit card";
        },
        error => {
          this.resultError = true;
					this.resultSuccess = false;
					this.message = "An error has occurred while subscribing: " + error;
					
					console.log('********** SubscribeComponent.ts: ' + error);
        }
      );
    }
  }

  parseDate(d: Date)
	{		
		return d.toString().replace('[UTC]', '');
  }
}
