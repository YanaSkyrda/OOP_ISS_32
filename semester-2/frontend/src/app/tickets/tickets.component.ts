import {Component, Injectable, OnInit} from '@angular/core';
import {TicketsService} from '../service/tickets.service';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Router} from "@angular/router";
import {AuthGuard} from "../guard/auth.guard";
import {Flight} from "../model/flight";
import {AppComponent} from "../app.component";

@Component({
  selector: 'app-booking',
  templateUrl: './tickets.component.html',
  styleUrls: ['./tickets.component.css']
})
@Injectable({
  providedIn: 'root'
})
export class TicketsComponent implements OnInit {
  ticketForm: FormGroup;
  state = 'PENDING';

  constructor(private ticketsService: TicketsService,
              private formBuilder: FormBuilder,
              private app: AppComponent,
              private router: Router,
              public auth: AuthGuard) {
  }

  ngOnInit(): void {
    this.ticketForm = this.formBuilder.group(
      {
        baggage: [false],
        priority: [false],
        seat: ['Standard']
      }
    );
  }

  async buildTicket(): Promise<any> {
    console.log(this.app.chosenFlight);

    let baggagePrice = 0
    if (this.inputs.baggage) {
        baggagePrice = this.app.chosenFlight.priceOfBaggage
    }

    let priorityPrice = 0
    if (this.inputs.priority) {
      priorityPrice = this.app.chosenFlight.priceOfPriorityRegister
    }
    const ticket = {
      haveBaggage: this.inputs.baggage,
      havePriorityRegister: this.inputs.priority,
      username: await this.app.getUsername(),
      flightId: this.app.chosenFlight.id,
      flightPrice: this.app.chosenFlight.price,
      baggagePrice: baggagePrice,
      priorityRegisterPrice: priorityPrice,
      seat: this.inputs.seat,
      status: this.state
    };
    console.log(ticket);
    return ticket;
  }

  get inputs(): any {
    return this.ticketForm.value;
  }

  redirectToFlights() {
    this.router.navigate(['/flights']);
  }

  async createNewTicketByUser(): Promise<any> {
    this.state = 'BOOKED';
    console.log(this.app.chosenFlight);
    this.redirectToFlights();
    return this.ticketsService.createTicketByUser(await this.buildTicket()).subscribe((d: any) => console.log(d));
  }

}
