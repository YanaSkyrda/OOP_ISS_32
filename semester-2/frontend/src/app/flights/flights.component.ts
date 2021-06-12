import {Component, OnInit, Output} from '@angular/core';
import {FlightsService} from '../service/flights.service';
import {Router} from "@angular/router";
import {Flight} from "../model/flight";
import {FormBuilder, FormGroup} from "@angular/forms";
import {AuthGuard} from "../guard/auth.guard";
import {TicketsComponent} from "../tickets/tickets.component";
import { EventEmitter } from '@angular/core';
import {AppComponent} from "../app.component";

@Component({
  selector: 'app-flights',
  templateUrl: './flights.component.html',
  styleUrls: ['./flights.component.css']
})
export class FlightsComponent implements OnInit {
  flights: Flight[];
  flightsForm: FormGroup;

  @Output() newItemEvent = new EventEmitter<Flight>();

  constructor(private flightsService: FlightsService,
              private router: Router,
              private formBuilder: FormBuilder,
              private app: AppComponent,
              public auth: AuthGuard) {
    this.flightsForm = this.formBuilder.group(
      {
        departureFrom: '',
        arrivalTo: '',
        departureTime: '',
        arrivalTime: '',
        number_of_seats: '',
        price: '',
        priceBaggage: '',
        pricePriority: '',
      }
    );
  }


  ngOnInit(): void {
    this.getAllFlights();
  }

  buildFlight(): Flight {
    let flight: Flight = {
      id: Math.floor(Math.random() * (9999 - 1100 + 1) + 1100),
      price: this.inputs.price,
      priceOfBaggage: this.inputs.priceBaggage,
      priceOfPriorityRegister: this.inputs.pricePriority,
      numberOfSeats: this.inputs.number_of_seats,
      departureCountry: this.inputs.departureFrom,
      arrivalCountry: this.inputs.arrivalTo,
      departureTime: this.inputs.departureTime,
      arrivalTime: this.inputs.arrivalTime
    };
    console.log(flight);
    return flight;
  }

  get inputs(): any {
    return this.flightsForm.value;
  }

  createNewFlight(): any {
    let flight = this.buildFlight();
    console.log(flight);
    this.flights.push(flight);
    //return this.flightsService.createNewFlight(this.buildFlight()).subscribe((d: any) => console.log(d));
  }

  redirectToTicket() {
    this.router.navigate(['/ticket']);
  }

  getAllFlights(): void {
    this.flightsService.getAllFlights().subscribe(flights => {
      console.log(flights);
      this.flights = flights;
    });
  }

  buyTicket(flight_id : number) : void {
    //TODO POST UPDATE TO DB
    for (let flight of this.flights) {
      if (flight.id == flight_id && flight.numberOfSeats > 0) {
        flight.numberOfSeats--;
        this.app.chosenFlight = flight;
        console.log(flight)
      }
    }
    this.redirectToTicket()
  }
}

