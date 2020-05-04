import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SessionService } from '../session.service';
import { MemberService } from '../member.service';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})

export class HeaderComponent implements OnInit {


  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              public sessionService: SessionService,
              private memberService: MemberService) {}

  ngOnInit() {
  }

  memberLogout(): void {
		this.sessionService.setIsLogin(false);
		this.sessionService.setCurrentMember(null);
		
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

}
