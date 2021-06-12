import {APP_INITIALIZER, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {AdminComponent} from './admin/admin.component';
import {CabinetComponent} from './cabinet/cabinet.component';
import {LoginComponent} from './login/login.component';
import {TicketsComponent} from './tickets/tickets.component';
import {FlightsComponent} from './flights/flights.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AppRoutingModule} from "./app-routing.module";
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import {initializeKeycloak} from "./init/keycloak-init.factory";

@NgModule({
  declarations: [
    AppComponent,
    AdminComponent,
    CabinetComponent,
    LoginComponent,
    TicketsComponent,
    FlightsComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    KeycloakAngularModule,
    ReactiveFormsModule,
    AppRoutingModule,
    FormsModule,
  ],
  providers: [FlightsComponent, AppComponent,
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService],
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
