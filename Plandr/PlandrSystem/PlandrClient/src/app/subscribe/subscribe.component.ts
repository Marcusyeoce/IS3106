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
    this.message = "Select your subscription plan!";
    this.memberToView = this.sessionService.getCurrentMember();
  }

  subscribe(subscribeForm: NgForm) {

    this.submitted = true;

    if(subscribeForm.valid) {

      this.memberService.memberSubscribe(this.subPackage).subscribe(
        response => {
          this.sessionService.setSubscribed(true);
          this.sessionService.setSubscribedUntil(response.subscribedUntil);
          console.log(this.sessionService.getSubscribedUntil());

          this.resultSuccess = true;
          this.resultError = false;

					this.message = "Subscribed successfully!";
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
