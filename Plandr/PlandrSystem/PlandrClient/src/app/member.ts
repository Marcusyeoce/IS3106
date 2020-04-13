export class Member {

    memberId: number;
    name: string; 
    email: string;
    contactNum: number;
    username: string;
    password: string;
    gender: string;
    dob: string;
    //not required in constructor
    subscribed: boolean;
    subscribedUntil: string; 
    ccNum: number;

    constructor(memberId?: number, name?: string, email?: string, contactNum?: number, username?: string, password?: string, gender?: string, dob?:string)
    {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.contactNum = contactNum;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.dob = dob;

        this.subscribed = false;
    }

}
