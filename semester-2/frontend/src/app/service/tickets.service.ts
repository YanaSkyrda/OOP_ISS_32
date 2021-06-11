import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Ticket} from '../model/ticket';
import {User} from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class TicketsService {
  url = 'http://localhost:8085/Gradle___com_example___lab_1_1_0_SNAPSHOT_war/ticket';
  urlCabinet = 'http://localhost:8085/Gradle___com_example___lab_1_1_0_SNAPSHOT_war/cabinet';
  urlAdmin = 'http://localhost:8085/Gradle___com_example___lab_1_1_0_SNAPSHOT_war/admin';

  constructor(private httpClient: HttpClient) {
  }


  getAllTicketsByUser(user: User): Observable<Ticket[]> {
    return this.httpClient.post<Ticket[]>(this.urlCabinet, user);
  }

  createTicketByUser(ticket: Ticket): any {

    return this.httpClient.post<Ticket>(this.url, ticket);
  }

  getAllTickets(): Observable<Ticket[]> {
    return this.httpClient.get<Ticket[]>(this.url);
  }

  updateTicket(flightId: number, id: number): Observable<any> {
    return this.httpClient.post<any>(this.urlAdmin + '?ticketId=' + id + '&flightId=' + flightId, {});
  }

  deleteTicket(id: number): Observable<any> {
    return this.httpClient.get<any>(this.urlAdmin + '?ticketId=' + id);
  }
}
