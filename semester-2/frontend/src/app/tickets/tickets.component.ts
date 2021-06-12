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

  buildTicket(): any {
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
      have_baggage: this.inputs.baggage,
      have_priority_register: this.inputs.priority,
      username: this.auth.getUsername(),
      flight_id: this.app.chosenFlight.id,
      flight_price: this.app.chosenFlight.price,
      baggage_price: baggagePrice,
      priority_register_price: priorityPrice,
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

  createNewTicketByUser(): any {
    this.state = 'BOOKED';
    console.log(this.app.chosenFlight);
    this.redirectToFlights();
    return this.ticketsService.createTicketByUser(this.buildTicket()).subscribe((d: any) => console.log(d));
  }

}
