
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Hotel {
  id: number;
  name: string;
  location: string;
  description: string;
  amenities: string;
}

export interface Room {
  id: number;
  roomType: string;
  price: number;
  capacity: number;
}

@Injectable({
  providedIn: 'root'
})
export class HotelService {

  private apiUrl = 'http://localhost:8080/api/hotels';
  private roomApiUrl = 'http://localhost:8080/api/rooms';

  constructor(private http: HttpClient) {}

  getHotels(): Observable<Hotel[]> {
    return this.http.get<Hotel[]>(this.apiUrl);
  }

  getRooms(hotelId: number): Observable<Room[]> {
    return this.http.get<Room[]>(
      `${this.roomApiUrl}?hotelId=${hotelId}`
    );
  }
}
