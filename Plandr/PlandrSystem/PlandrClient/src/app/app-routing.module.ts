import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { MainComponent } from './main/main.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { ViewAllArticlesComponent } from './view-all-articles/view-all-articles.component';
import { ViewArticleComponent } from './view-article/view-article.component';
import { ViewAllAttractionsComponent } from './view-all-attractions/view-all-attractions.component';
import { ViewAttractionComponent } from './view-attraction/view-attraction.component'; 
import { ViewAllBookingsComponent } from './view-all-bookings/view-all-bookings.component';
import { CreateNewBookingComponent } from './create-new-booking/create-new-booking.component';
import { PaymentComponent } from './payment/payment.component';
import { FaqComponent } from './faq/faq.component';


const routes: Routes = [
  { path: '', redirectTo: '/main', pathMatch: 'full' },
  { path: 'main', component: MainComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'viewAllArticles', component: ViewAllArticlesComponent},
  { path: 'viewArticle', component: ViewArticleComponent },
  { path: 'viewAllAttractions', component: ViewAllAttractionsComponent},
  { path: 'viewAttraction', component: ViewAttractionComponent },
  { path: 'viewAllBookings', component: ViewAllBookingsComponent},
  { path: 'createNewBooking', component: CreateNewBookingComponent},
  { path: 'payment', component: PaymentComponent },
  { path: 'faq', component: FaqComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
