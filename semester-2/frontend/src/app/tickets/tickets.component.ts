import {Component, Injectable, OnInit} from '@angular/core';
import {TicketsService} from '../service/tickets.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from "@angular/router";
import {FlightsService} from "../service/flights.service";
import {FlightsComponent} from "../flights/flights.component";
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
  user = 1;
  state = 'PENDING';
  private ticketsService: TicketsService;

  constructor(private flightsComponent: FlightsComponent,
              private formBuilder: FormBuilder,
              private router: Router,
              public app: AppComponent) {
  }

  ngOnInit(): void {
    this.ticketForm = this.formBuilder.group(
      {
        have_baggage: ['', Validators.required],
        have_priority_register: ['', Validators.required],
      }
    );
    this.flightsComponent.getAllFlights()
  }

  buildTicket(): any {
    const ticket = {
      have_baggage: this.inputs.have_baggage,
      have_priority_register: this.inputs.have_priority_register,
      userByUserId: this.user,
      status: this.state
    };
    console.log(ticket);
    return ticket;
  }

  get inputs(): any {
    // @ts-ignore
    return this.ticketForm.controls;
  }

  redirect() {
    this.router.navigate(['ticket']);
  }

  createNewTicketByUser(): any {
    return this.ticketsService.createTicketByUser(this.buildTicket()).subscribe((d: any) => console.log(d));
  }

}
