import {Component, OnInit} from '@angular/core';
import {UserService} from '../service/user.service';
import {Ticket} from '../model/ticket';
import {TicketsService} from '../service/tickets.service';
import {Router} from "@angular/router";
import {AppComponent} from "../app.component";
import {Observable} from "rxjs";

@Component({
  selector: 'app-cabinet',
  templateUrl: './cabinet.component.html',
  styleUrls: ['./cabinet.component.css']
})
export class CabinetComponent implements OnInit {

  tickets: Observable<Ticket[]>|undefined;

  constructor(private userService: UserService,
              private ticketsService: TicketsService,
              private app: AppComponent,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getAllTickets();
  }

  redirectFlights() {
    this.router.navigate(['flights']);
  }

  async getAllTickets(): Promise<void> {
    console.log(this.tickets)
    this.tickets = this.ticketsService.getAllTicketsByUser(await this.app.getUsername());
    console.log(this.tickets.pipe().subscribe(data => console.log(data)))
  }
}
