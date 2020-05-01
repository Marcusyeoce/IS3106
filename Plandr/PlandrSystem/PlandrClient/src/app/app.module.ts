import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material";

import { ButtonModule } from "primeng/button";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";

import { MainComponent } from "./main/main.component";
import { ViewAllArticlesComponent } from "./view-all-articles/view-all-articles.component";
import { ViewAllAttractionsComponent } from "./view-all-attractions/view-all-attractions.component";
import { ViewAllBookingsComponent } from "./view-all-bookings/view-all-bookings.component";
import { RegisterComponent } from "./register/register.component";
import { LoginComponent } from "./login/login.component";
import { PaymentComponent } from "./payment/payment.component";
import { ProfileComponent } from "./profile/profile.component";
import { FaqComponent } from "./faq/faq.component";
import { ViewArticleComponent } from "./view-article/view-article.component";
import { ViewAttractionComponent } from "./view-attraction/view-attraction.component";
import { HeaderComponent } from "./header/header.component";
import { FooterComponent } from "./footer/footer.component";
import { MakeBookingComponent } from "./make-booking/make-booking.component";
import { ProfileDetailsComponent } from "./profile-details/profile-details.component";
import { ViewBookingComponent } from './view-booking/view-booking.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { SubscribeComponent } from './subscribe/subscribe.component';
import { WriteReviewComponent } from './write-review/write-review.component';
import { SearchAttractionsComponent } from './search-attractions/search-attractions.component';
import { CancelBookingComponent } from './cancel-booking/cancel-booking.component';
import { UpdateProfileComponent } from './update-profile/update-profile.component';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    ViewAllArticlesComponent,
    ViewAllAttractionsComponent,
    ViewAllBookingsComponent,
    RegisterComponent,
    LoginComponent,
    PaymentComponent,
    ProfileComponent,
    FaqComponent,
    ViewArticleComponent,
    ViewAttractionComponent,
    HeaderComponent,
    FooterComponent,
    MakeBookingComponent,
    ProfileDetailsComponent,
    ViewBookingComponent,
    ChangePasswordComponent,
    SubscribeComponent,
    WriteReviewComponent,
    SearchAttractionsComponent,
    CancelBookingComponent,
    UpdateProfileComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ButtonModule,
    MatButtonModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
