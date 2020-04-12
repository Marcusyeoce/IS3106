import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { ButtonModule } from 'primeng/button';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { MainComponent } from './main/main.component';
import { ViewAllArticlesComponent } from './view-all-articles/view-all-articles.component';
import { ViewAllAttractionsComponent } from './view-all-attractions/view-all-attractions.component';
import { ViewAllBookingsComponent } from './view-all-bookings/view-all-bookings.component';
import { CreateNewBookingComponent } from './create-new-booking/create-new-booking.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { PaymentComponent } from './payment/payment.component';
import { ProfileComponent } from './profile/profile.component';
import { FaqComponent } from './faq/faq.component';
import { ViewArticleComponent } from './view-article/view-article.component';
import { ViewAttractionComponent } from './view-attraction/view-attraction.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    ViewAllArticlesComponent,
    ViewAllAttractionsComponent,
    ViewAllBookingsComponent,
    CreateNewBookingComponent,
    RegisterComponent,
    LoginComponent,
    PaymentComponent,
    ProfileComponent,
    FaqComponent,
    ViewArticleComponent,
    ViewAttractionComponent,
    HeaderComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ButtonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
