import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {FlightsComponent} from "../flights/flights.component";
import {Router} from "@angular/router";
import {AppComponent} from "../app.component";

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {

  formGroup: FormGroup;

  constructor(private flightsComponent: FlightsComponent,
              private formBuilder: FormBuilder,
              private router: Router,
              public app: AppComponent) {
  }

  ngOnInit(): void {
    this.formGroup = this.formBuilder.group(
      {
        have_baggage: ['', Validators.required],
        have_priority_register: ['', Validators.required],
      }
    );
    this.flightsComponent.getAllFlights()
  }

  redirect() {
    this.router.navigate(['ticket']);
  }

  getAllProfitFromPrice(): any {
    return this.flightsComponent.getAllProfitFromPrice();
  }

  getAllProfitFromBaggage(): any {
    return this.flightsComponent.getAllProfitFromBaggage();
  }

  getAllProfitFromPriority(): any {
    return this.flightsComponent.getAllProfitFromPriority();
  }

  getTopProfitFlights(): any {
    return this.flightsComponent.getTopProfitFlights();
  }

  publishStatistics() {
    this.flightsComponent.publishStatistics();
  }

}
