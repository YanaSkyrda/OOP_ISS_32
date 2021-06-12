import {Component, OnInit} from '@angular/core';
import {FlightsService} from '../service/flights.service';
import {Router} from "@angular/router";
import {Flight} from "../model/flight";
import {AppComponent} from "../app.component";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Statistic} from "../model/statistic";
import {formatDate} from "@angular/common";
import {AuthGuard} from "../guard/auth.guard";

@Component({
  selector: 'app-flights',
  templateUrl: './flights.component.html',
  styleUrls: ['./flights.component.css']
})
export class FlightsComponent implements OnInit {

  flights: Flight[];
  flightsForm: FormGroup;

  constructor(private flightsService: FlightsService,
              private router: Router,
              private formBuilder: FormBuilder,
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

  redirect() {
    this.router.navigate(['flights']);
  }

  getAllFlights(): void {
    this.flightsService.getAllFlights().subscribe(flights => {
      console.log(flights);
      this.flights = flights;
    });
  }

  buyTicket(flightId : number) : void {
    for (let flight of this.flights) {
      if (flight.id == flightId && flight.numberOfSeats > 0) {
        flight.numberOfSeats--;
      }
    }
  }

  // publishStatistics() {
    // let mapPrice = this.getAllProfitFromPrice()
    // let mapPriceBaggage = this.getAllProfitFromBaggage()
    // let mapPricePriority = this.getAllProfitFromPriority()
    // let mapPriceTotal = this.getTopProfitFlights()
  //   let mapStatistics = new Map()
  //   for (let entry of mapPrice.entries()) {
  //     if (mapStatistics.has(entry[0].id)) {
  //       let statistic = mapStatistics.get(entry[0].id)
  //       statistic.price_sum = entry[1]
  //       mapStatistics.set(entry[0].id, statistic)
  //     } else {
  //       let statistic: Statistic = {
  //         flight_id: entry[0].id,
  //         price_sum: 0,
  //         price_baggage_sum: 0,
  //         price_priority_sum: 0,
  //         price_total_sum: 0,
  //         date: formatDate(new Date(), 'dd/MMM/yyyy:HH:mm', 'en').toString()
  //       };
  //       mapStatistics.set(entry[0].id, statistic);
  //     }
  //   }
  //
  //   for (let entry of mapPriceBaggage.entries()) {
  //       let statistic = mapStatistics.get(entry[0].id)
  //       statistic.price_baggage_sum = entry[1]
  //       mapStatistics.set(entry[0].id, statistic)
  //   }
  //
  //   for (let entry of mapPricePriority.entries()) {
  //     let statistic = mapStatistics.get(entry[0].id)
  //     statistic.price_priority_sum = entry[1]
  //     mapStatistics.set(entry[0].id, statistic)
  //   }
  //
  //   for (let entry of mapPriceTotal.entries()) {
  //     let statistic = mapStatistics.get(entry[0].id)
  //     statistic.price_total_sum = entry[1]
  //     mapStatistics.set(entry[0].id, statistic)
  //   }
  //
  //   for (let entry of mapStatistics.entries()) {
  //     this.flightsService.createNewStatistics(entry[1]);
  //   }
  // }
}

