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
  submitted: boolean;
  subPackage: number;

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
  }

  update(updateProductForm: NgForm) {
    this.submitted = true;

    if(updateProductForm.valid) {
      this.memberService.memberSubscribe(this.subPackage).subscribe(
        response => {
          this.sessionService.getCurrentMember().subscribed = true;
          this.sessionService.getCurrentMember().subscribedUntil = response.subscribedUntil;

          this.resultSuccess = true;
					this.resultError = false;
					this.message = "Subscribed successfully");
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
}
