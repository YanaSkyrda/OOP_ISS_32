import {Component} from '@angular/core';
import {Flight} from "./model/flight";
import {TicketsService} from "./service/tickets.service";
import {FormBuilder} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthGuard} from "./guard/auth.guard";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {

  constructor(public auth: AuthGuard) {
  }

  async getUsername() : Promise<string> {
    return await this.auth.getUsername()
  }

  title = 'frontend';
  chosenFlight: Flight;
  showAccounting = false;
}
