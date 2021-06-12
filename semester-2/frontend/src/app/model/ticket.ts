import {Flight} from './flight';

export interface Ticket {
  id: number;
  have_baggage: boolean
  baggage_price: number;
  have_priority_register: boolean
  priority_register_price: number;
  flight_id: string;
  flight_price: number;
  seat: string;
  username: number;
  status: string;
}
