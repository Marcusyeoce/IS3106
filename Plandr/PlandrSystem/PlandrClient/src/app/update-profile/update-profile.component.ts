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
  inputDob: Date;

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
    this.inputDob = this.memberToUpdate.dob;
  }

  update(updateProfileForm: NgForm) {
    this.submitted = true;

    if(updateProfileForm.valid) {
      let dob = new Date(this.inputDob.toString() + "T00:00:00");
      this.memberToUpdate.dob = dob;
      this.memberToUpdate.username = this.sessionService.getUsername();
      this.memberToUpdate.password = this.sessionService.getPassword();
      this.memberToUpdate.bookingEntities = [];
      this.memberToUpdate.subscribed = false;

      this.memberService.updateProfile(this.memberToUpdate).subscribe(
        response => {

          this.resultSuccess = true;
          this.resultError = false;

          this.sessionService.setCurrentMember(this.memberToUpdate);
          this.sessionService.setName(this.memberToUpdate.name);
          this.sessionService.setEmail(this.memberToUpdate.email);
          this.sessionService.setContactNumber(this.memberToUpdate.contactNumber);
          this.sessionService.setGender(this.memberToUpdate.gender);
          this.sessionService.setDob(this.memberToUpdate.dob);		
          this.sessionService.setCreditCard(this.memberToUpdate.creditCard);	
          
          this.message = "Profile updated successfully";

          // //route back
          // this.router.navigate(["/profileDetails"]);
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
