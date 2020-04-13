import { Injectable } from '@angular/core';

import { SessionService } from './session.service';
import { Member } from './member';

@Injectable({
  providedIn: 'root'
})
export class MemberService {

  members: Member[];

  constructor(private sessionService: SessionService) {
    this.members = this.sessionService.getMembers();
  }

  getMembers(): Member[]
  {
    return this.members;
  }

  createNewMember(newMember: Member)
  {
    let member: Member = new Member();
    member.memberId = newMember.memberId;
    member.name = newMember.name;
    member.email = newMember.email;
    member.username = newMember.username;
    member.password = newMember.password;
    member.gender = newMember.gender;
    member.dob = newMember.dob;
    member.subscribed = newMember.subscribed;
  }
}
