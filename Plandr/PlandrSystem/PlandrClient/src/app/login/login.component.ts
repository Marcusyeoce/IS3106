import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { SessionService } from '../session.service';
import { MemberService } from '../member.service';
import { Member } from '../member';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: string;
  password: string;

  submitted: boolean;
  loginError: boolean;
  errorMessage: string;
  message: string;

  constructor(private router: Router, private memberService: MemberService, private sessionService: SessionService) 
  {
    this.submitted = false;
    this.loginError = false;
  }

  ngOnInit() 
  {
  }

  login(loginForm: NgForm) {
    this.submitted = true;

    if (loginForm.valid) {
      this.sessionService.setUsername(this.username);
      this.sessionService.setPassword(this.password);
      
      this.memberService.memberLogin(this.username, this.password).subscribe(
        response => {
          let loginMember: Member = response.member;

          if(loginMember != null) {
            this.sessionService.setIsLogin(true);
            this.sessionService.setCurrentMember(loginMember);
            this.sessionService.setName(loginMember.name);
            this.sessionService.setEmail(loginMember.email);
            this.sessionService.setContactNumber(loginMember.contactNumber);
            this.sessionService.setGender(loginMember.gender);
            this.sessionService.setDob(loginMember.dob);
            this.sessionService.setSubscribed(loginMember.subscribed);
            this.sessionService.setSubscribedUntil(loginMember.subscribedUntil);		
            this.sessionService.setCreditCard(loginMember.creditCard);	
            this.loginError = false;
            
            this.router.navigate(["/main"]);
          } else {
            this.loginError = true;
          }
        },
        error => {
          this.message = "Username or password does not match! Please try again"
          this.loginError = true;
          this.errorMessage = error
        }
      );
    }
  }

}
