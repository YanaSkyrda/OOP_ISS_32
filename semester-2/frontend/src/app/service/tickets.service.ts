import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Ticket} from '../model/ticket';
import {User} from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class TicketsService {
  url = '/ticket';
  urlCabinet = '/cabinet';
  urlAdmin = '/admin';

  constructor(private httpClient: HttpClient) {
  }


  getAllTicketsByUser(username: String): Observable<Ticket[]> {
    return this.httpClient.post<Ticket[]>(this.urlCabinet, {'username':username});
  }

  createTicketByUser(ticket: Ticket): any {
    return this.httpClient.post<Ticket>(this.url, ticket);
  }

  getAllTickets(): Observable<Ticket[]> {
    return this.httpClient.get<Ticket[]>(this.url);
  }

  updateTicket(flight_id: number, id: number): Observable<any> {
    return this.httpClient.post<any>(this.urlAdmin + '?ticket_id=' + id + '&flight_id=' + flight_id, {});
  }

  deleteTicket(id: number): Observable<any> {
    return this.httpClient.get<any>(this.urlAdmin + '?ticket_id=' + id);
  }
}
