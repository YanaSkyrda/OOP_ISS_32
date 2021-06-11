import {Component, OnInit} from '@angular/core';
import {AuthService} from '../service/auth-service';
import {TicketsService} from '../service/tickets.service';
import {FlightsService} from '../service/flights.service';
import {Flight} from '../model/flight';
import {Ticket} from '../model/ticket';
import {Router} from '@angular/router';
import {Flights} from '../model/flights';

//TODO THIS

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  flights: Flight[];
  tickets: Ticket[];
  selectedFlight: number;

  constructor(private authService: AuthService,
              private ticketsService: TicketsService,
              private flightsService: FlightsService,
              private router: Router,
  ) {

  }

  ngOnInit(): void {
    this.getAllTickets();
    this.getAllFlights();
  }

  getAllTickets(): void {
    this.ticketsService.getAllTickets().pipe().subscribe(data => {
      this.tickets = data;
      console.log(data);
    });
  }

  getAllFlights(): void {
    this.flightsService.getAllFlights().pipe().subscribe(data => {
      this.flights = data;
      console.log(data);
    });
  }

  updateTicket(id: number): void {
    console.log(id, this.selectedFlight);
    // @ts-ignore
    this.ticketsService.updateTicket(this.selectedFlight, id).pipe().subscribe(data => console.log(data));
  }

  deleteTicket(id: number): void {
    this.ticketsService.deleteTicket(id).pipe().subscribe();
  }

  onSelect(value: any): void {
    this.selectedFlight = value;
    console.log(this.selectedFlight);

  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
