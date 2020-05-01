import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../session.service';
import { MemberService } from '../member.service';
import { Member } from '../member';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})

export class HeaderComponent implements OnInit {
  @Output() 
	childEvent = new EventEmitter();	
	
	username: string;
	password: string;
	loginError: boolean;
	errorMessage: string;


  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              public sessionService: SessionService,
              private memberService: MemberService) {
    this.loginError = false;
  }

  ngOnInit() {
  }

  memberLogin(): void {
    this.sessionService.setUsername(this.username);
    this.sessionService.setPassword(this.password);
    
    this.memberService.memberLogin(this.username, this.password).subscribe(
      response => {
        let loginMember: Member = response.member;

        if(loginMember != null) {
          this.sessionService.setIsLogin(true);
					this.sessionService.setCurrentMember(loginMember);					
					this.loginError = false;
					
					this.childEvent.emit();
					
					this.router.navigate(["/main"]);
        } else {
          this.loginError = true;
        }
      },
      error => {
        this.loginError = true;
				this.errorMessage = error
      }
    )
  }

  memberLogout(): void {
		this.sessionService.setIsLogin(false);
		this.sessionService.setCurrentMember(null);
		
		this.router.navigate(["/main"]);
	}

  redirectToMain() 
  { 
    this.router.navigate(["/main"]);
  }

  redirectToRegister() 
  { 
    this.router.navigate(["/register"]);
  }

  redirectToLogin() 
  { 
    this.router.navigate(["/login"]);
  }

  redirectToArticles() 
  { 
    this.router.navigate(["/viewAllArticles"]);
  }

  redirectToAttractions() 
  { 
    this.router.navigate(["/viewAllAttractions"]);
  }

  redirectToBookings() 
  { 
    this.router.navigate(["/makeBooking"]);
  }

  redirectToProfile() 
  { 
    this.router.navigate(["/profile"]);
  }

  redirectToFaq() 
  { 
    this.router.navigate(["/faq"]);
  }

}
