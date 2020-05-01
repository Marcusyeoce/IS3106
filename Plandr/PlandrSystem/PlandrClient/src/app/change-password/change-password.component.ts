import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { SessionService } from '../session.service';
import { MemberService } from '../member.service';
import { Member } from '../member';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})

export class ChangePasswordComponent implements OnInit {
  submitted: boolean;
  newPassword: string;
  rePassword: string;

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
      this.memberService.updateMemberPassword(this.newPassword, this.rePassword).subscribe(
        response => {
          this.sessionService.setPassword(this.newPassword);

          this.resultSuccess = true;
					this.resultError = false;
					this.message = "Password changed successfully";
        },
        error => {
          this.resultError = true;
					this.resultSuccess = false;
					this.message = "An error has occurred while changing password: " + error;
					
					console.log('********** ChangePasswordComponent.ts: ' + error);
        }
      );
    }
  }

}
