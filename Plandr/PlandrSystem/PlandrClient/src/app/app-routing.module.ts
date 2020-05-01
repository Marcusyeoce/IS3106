import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { MainComponent } from './main/main.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { ProfileDetailsComponent } from './profile-details/profile-details.component';
import { UpdateProfileComponent } from './update-profile/update-profile.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { SubscribeComponent } from './subscribe/subscribe.component';
import { ViewAllBookingsComponent } from './view-all-bookings/view-all-bookings.component';
import { ViewBookingComponent } from './view-booking/view-booking.component';
import { ViewAllArticlesComponent } from './view-all-articles/view-all-articles.component';
import { ViewArticleComponent } from './view-article/view-article.component';
import { ViewAllAttractionsComponent } from './view-all-attractions/view-all-attractions.component';
import { ViewAttractionComponent } from './view-attraction/view-attraction.component'; 
import { MakeBookingComponent } from './make-booking/make-booking.component';
import { CancelBookingComponent } from './cancel-booking/cancel-booking.component';
import { PaymentComponent } from './payment/payment.component';
import { WriteReviewComponent } from './write-review/write-review.component';
import { SearchAttractionsComponent } from './search-attractions/search-attractions.component';
import { FaqComponent } from './faq/faq.component';


const routes: Routes = [
  { path: '', redirectTo: '/main', pathMatch: 'full' },
  { path: 'main', component: MainComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'profileDetails', component: ProfileDetailsComponent },
  { path: 'updateProfile', component: UpdateProfileComponent },
  { path: 'changePassword', component: ChangePasswordComponent },
  { path: 'subscribe', component: SubscribeComponent },
  { path: 'viewAllBookings', component: ViewAllBookingsComponent },
  { path: 'viewBooking', component: ViewBookingComponent },
  { path: 'viewBooking/:bookingId', component: ViewBookingComponent },
  { path: 'cancelBooking', component: CancelBookingComponent },
  { path: 'cancelBooking/:bookingId', component: CancelBookingComponent },
  { path: 'viewAllArticles', component: ViewAllArticlesComponent },
  { path: 'viewArticle', component: ViewArticleComponent },
  { path: 'viewArticle/:articleId', component: ViewArticleComponent },
  { path: 'viewAllAttractions', component: ViewAllAttractionsComponent },
  { path: 'viewAttraction', component: ViewAttractionComponent },
  { path: 'viewAttraction/:attractionId', component: ViewAttractionComponent },
  { path: 'writeReview', component: WriteReviewComponent },
  { path: 'writeReview/:bookingId', component: WriteReviewComponent },
  { path: 'searchAttractions', component: SearchAttractionsComponent },
  { path: 'makeBooking', component: MakeBookingComponent },
  { path: 'payment', component: PaymentComponent },
  { path: 'faq', component: FaqComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
