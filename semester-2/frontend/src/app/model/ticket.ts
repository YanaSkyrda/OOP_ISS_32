import {Flight} from './flight';

export interface Ticket {
  id: number;
  haveBaggage: boolean
  baggagePrice: number;
  havePriorityRegister: boolean
  priorityRegisterPrice: number;
  flightId: string;
  flightPrice: number;
  seat: string;
  username: number;
  status: string;
}
