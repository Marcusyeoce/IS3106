import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { SessionService } from '../session.service';
import { MemberService } from '../member.service';
import { Member } from '../member';
import { GenderEnum } from '../gender-enum.enum';

@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrls: ['./update-profile.component.css']
})

export class UpdateProfileComponent implements OnInit {
  submitted: boolean;
  memberToUpdate: Member;

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
    this.memberToUpdate = this.sessionService.getCurrentMember();
  }

  update(updateProfileForm: NgForm) {
    this.submitted = true;

    if(updateProfileForm.valid) {
      this.memberToUpdate.username = this.sessionService.getUsername();
      this.memberToUpdate.password = this.sessionService.getPassword();

      this.memberService.updateProfile(this.memberToUpdate).subscribe(
        response => {

          this.resultSuccess = true;
          this.resultError = false;

          this.sessionService.setCurrentMember(this.memberToUpdate);
          
          //any way to display this after routing back?
          this.message = "Profile updated successfully";

          //route back
          this.router.navigate(["/profileDetails"]);
        },
        error => {
          this.resultError = true;
					this.resultSuccess = false;
					this.message = "An error has occurred while updating the profile: " + error;
					
					console.log('********** UpdateProfileComponent.ts: ' + error);
        }
      );
    }
  }

}
