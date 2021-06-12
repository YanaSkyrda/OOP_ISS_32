import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {CabinetComponent} from './cabinet/cabinet.component';
import {LoginComponent} from './login/login.component';
import {FlightsComponent} from './flights/flights.component';
import {TicketsComponent} from './tickets/tickets.component';
import {AdminComponent} from './admin/admin.component';
import {RouterModule, Routes} from "@angular/router";
import {AuthGuard} from "./guard/auth.guard";

const routes: Routes = [
  {path: 'cabinet', component: CabinetComponent},
  {path: 'login', component: LoginComponent},
  {path: 'flights', component: FlightsComponent},
  {path: 'ticket', component: TicketsComponent},
  {path: 'admin', component: AdminComponent},
  {path: '', component: AuthGuard , canActivate: [AuthGuard]},
  {path: '**', redirectTo: '' }
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
