import {Component} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'frontend';
  showStatistics = false;
  showFlights = true;
  showAccounting = false;
  flightsTestValues = [{
    "id": 1234,
    "price": 3300,
    "priceOfBaggage": 10,
    "priceOfPriorityRegister": 250,
    "numberOfSeats": 114,
    "departureCountry": "Ukraine",
    "arrivalCountry": "France",
    "departureTime": "12/12/2021:15:35",
    "arrivalTime": "12/12/2021:22:35"
  }, {
    "id": 1239,
    "price": 2600,
    "priceOfBaggage": 400,
    "priceOfPriorityRegister": 500,
    "numberOfSeats": 93,
    "departureCountry": "Ukraine",
    "arrivalCountry": "Poland",
    "departureTime": "01/12/2021:06:10",
    "arrivalTime": "01/12/2021:10:20"
  }, {
    "id": 2345,
    "price": 5900,
    "priceOfBaggage": 30,
    "priceOfPriorityRegister": 710,
    "numberOfSeats": 56,
    "departureCountry": "France",
    "arrivalCountry": "USA",
    "departureTime": "10/07/2021:17:00",
    "arrivalTime": "11/07/2021:00:20"
  }, {
    "id": 6478,
    "price": 1800,
    "priceOfBaggage": 0,
    "priceOfPriorityRegister": 370,
    "numberOfSeats": 120,
    "departureCountry": "France",
    "arrivalCountry": "Germany",
    "departureTime": "06/07/2021:07:15",
    "arrivalTime": "06/07/2021:08:50"
  }, {
    "id": 6490,
    "price": 2340,
    "priceOfBaggage": 300,
    "priceOfPriorityRegister": 90,
    "numberOfSeats": 150,
    "departureCountry": "Germany",
    "arrivalCountry": "Ukraine",
    "departureTime": "11/11/2021:07:15",
    "arrivalTime": "11/11/2021:14:00"
  }, {
    "id": 9988,
    "price": 5730,
    "priceOfBaggage": 300,
    "priceOfPriorityRegister": 90,
    "numberOfSeats": 115,
    "departureCountry": "Greece",
    "arrivalCountry": "Ukraine",
    "departureTime": "08/10/2021:16:15",
    "arrivalTime": "08/10/2021:22:40"
  }];
}
