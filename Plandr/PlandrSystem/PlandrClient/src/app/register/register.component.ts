import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { MemberService } from '../member.service';
import { Member } from '../member';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  submitted: boolean;
  newMember: Member;

  constructor(private router: Router, private memberService: MemberService)
  { 
    this.submitted = false;
    this.newMember = new Member();
  }

  ngOnInit() 
  {
  }

  clear()
  {
    this.submitted = false;
    this.newMember = new Member();
  }

  create(registerForm: NgForm) 
  {
    this.submitted = true;

    if (registerForm.valid) 
    {
      this.memberService.registerMember(this.newMember);
      //route to main page?
    }
  }

}
