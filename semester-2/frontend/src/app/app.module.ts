import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {AdminComponent} from './admin/admin.component';
import {CabinetComponent} from './cabinet/cabinet.component';
import {LoginComponent} from './login/login.component';
import {TicketsComponent} from './tickets/tickets.component';
import {FlightsComponent} from './flights/flights.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { StatisticsComponent } from './statistics/statistics.component';

@NgModule({
  declarations: [
    AppComponent,
    AdminComponent,
    CabinetComponent,
    LoginComponent,
    TicketsComponent,
    FlightsComponent,
    StatisticsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
  ],
  providers: [FlightsComponent, AppComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
