import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { MemberService } from '../member.service';
import { Member } from '../member';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  submitted: boolean;
  loginMember: Member;

  constructor(private router: Router, private memberService: MemberService) 
  {
    this.submitted = false;
    this.loginMember = new Member();
  }

  clear()
  {
    this.submitted = false;
    this.loginMember = new Member();
  }

  ngOnInit() 
  {
  }

  login(loginForm: NgForm) 
  {
  }

}
