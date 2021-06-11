import {Component, OnInit} from '@angular/core';
import {UserService} from '../service/user.service';
import {Ticket} from '../model/ticket';
import {TicketsService} from '../service/tickets.service';
import {Router} from "@angular/router";

@Component({
  selector: 'app-cabinet',
  templateUrl: './cabinet.component.html',
  styleUrls: ['./cabinet.component.css']
})
export class CabinetComponent implements OnInit {

  //user: User;
  tickets: Ticket[];

  constructor(private userService: UserService,
              private ticketsService: TicketsService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getAllTickets();
  }

  redirectFlights() {
    this.router.navigate(['flights']);
  }

  getAllTickets(): void {
    this.ticketsService.getAllTicketsByUser(this.getUser()).pipe().subscribe(
      (data: any[]) => {
        console.log(data);
        this.tickets = data;
      }
    );
  }

  getUser(): any {
    const user = {
      id: 1
    };
    return user;
  }
}
