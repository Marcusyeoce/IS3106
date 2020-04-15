import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private router: Router) 
  { 
  }

  ngOnInit() {
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
