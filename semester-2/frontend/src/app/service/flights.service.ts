import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Flights} from '../model/flights';
import {Flight} from "../model/flight";
import {Statistic} from "../model/statistic";

@Injectable({
  providedIn: 'root'
})
export class FlightsService {
  url = 'http://localhost:8085/Gradle___com_example___lab_1_1_0_SNAPSHOT_war/flight';

  constructor(private httpClient: HttpClient) {
  }

  getAllFlights(): Observable<Flight[]> {
    return this.httpClient.get<Flight[]>(this.url);
  }

  createNewFlight(flight: Flight) {
    return this.httpClient.post<Flight>(this.url, flight);
  }

  createNewStatistics(statistic: Statistic) {
    return this.httpClient.post<Statistic>(this.url + "/statistic", statistic);
  }
}
