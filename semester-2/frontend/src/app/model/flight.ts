export interface Flight {
  id: number;
  price: number;
  priceOfBaggage: number;
  priceOfPriorityRegister: number;
  numberOfSeats: number;
  departureCountry: string;
  arrivalCountry: string;
  departureTime: string;
  arrivalTime: string;
}
