
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

import {
  HotelService,
  Hotel,
  Room
} from './services/hotel.service';

interface BookingResponse {
  id: number;
  bookingReference: string;
  customerName: string;
  email: string;
  checkIn: string;
  checkOut: string;
  totalAmount: number;
  status: string;
  room: Room;
}

@Component({
  selector: 'app-root',
  imports: [CommonModule, FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {

  private bookingApiUrl = 'http://localhost:8080/api/bookings';

  hotels: Hotel[] = [];
  allHotels: Hotel[] = [];
  rooms: Room[] = [];

  selectedHotel: Hotel | null = null;
  selectedRoom: Room | null = null;

  customerName = '';
  customerEmail = '';

  checkIn = '';
  checkOut = '';

  errorMessage = '';
  roomError = '';
  bookingError = '';
  bookingMessage = '';
  isBooking = false;

  // My Booking properties
  searchReference = '';
  searchEmail = '';
  foundBooking: BookingResponse | null = null;
  lookupError = '';
  lookupMessage = '';
  isSearching = false;
  isCancelling = false;

  constructor(
    private hotelService: HotelService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.loadHotels();
  }

  loadHotels(): void {
    this.hotelService.getHotels().subscribe({
      next: (data: Hotel[]) => {
        this.allHotels = data;
        this.hotels = data;
        this.errorMessage = '';
      },
      error: () => {
        this.errorMessage = 'Unable to load hotels.';
      }
    });
  }

  searchHotels(location: string): void {
    const searchValue = location.trim().toLowerCase();

    if (!searchValue) {
      this.hotels = this.allHotels;
      return;
    }

    this.hotels = this.allHotels.filter(hotel =>
      hotel.location.toLowerCase().includes(searchValue)
    );
  }

  viewDetails(hotel: Hotel): void {
    this.selectedHotel = hotel;
    this.selectedRoom = null;
    this.rooms = [];
    this.roomError = '';
    this.bookingError = '';
    this.bookingMessage = '';

    this.hotelService.getRooms(hotel.id).subscribe({
      next: (data: Room[]) => {
        this.rooms = data;
      },
      error: () => {
        this.roomError = 'Unable to load rooms.';
      }
    });
  }

  selectRoom(room: Room): void {
    this.selectedRoom = room;
    this.bookingError = '';
    this.bookingMessage = '';
  }

  get totalNights(): number {
    if (!this.checkIn || !this.checkOut) {
      return 0;
    }

    const start = new Date(this.checkIn + 'T00:00:00');
    const end = new Date(this.checkOut + 'T00:00:00');

    const difference = end.getTime() - start.getTime();

    return difference > 0
      ? difference / (1000 * 60 * 60 * 24)
      : 0;
  }

  get totalAmount(): number {
    return this.selectedRoom
      ? this.selectedRoom.price * this.totalNights
      : 0;
  }

  createBooking(): void {
    this.bookingError = '';
    this.bookingMessage = '';

    if (!this.selectedRoom) {
      this.bookingError = 'Please select a room first.';
      return;
    }

    if (!this.customerName.trim()) {
      this.bookingError = 'Please enter your name.';
      return;
    }

    if (!this.customerEmail.trim()) {
      this.bookingError = 'Please enter your email.';
      return;
    }

    if (!this.checkIn || !this.checkOut) {
      this.bookingError = 'Please select booking dates.';
      return;
    }

    const today = new Date();
    today.setHours(0, 0, 0, 0);

    const start = new Date(this.checkIn + 'T00:00:00');
    const end = new Date(this.checkOut + 'T00:00:00');

    if (start < today) {
      this.bookingError = 'Check-in cannot be in the past.';
      return;
    }

    if (end <= start) {
      this.bookingError = 'Check-out must be after check-in.';
      return;
    }

    const bookingData = {
      customerName: this.customerName.trim(),
      email: this.customerEmail.trim(),
      roomId: this.selectedRoom.id,
      checkIn: this.checkIn,
      checkOut: this.checkOut
    };

    this.isBooking = true;

    this.http.post<BookingResponse>(
      this.bookingApiUrl,
      bookingData
    ).subscribe({
      next: (response) => {
        this.bookingMessage =
          'Booking confirmed! Reference: ' +
          response.bookingReference;

        this.isBooking = false;
      },
      error: (error) => {
        this.isBooking = false;
        this.bookingError =
          error.error?.message ||
          (typeof error.error === 'string' ? error.error : '') ||
          'Booking failed. Please try again.';
      }
    });
  }

  // Search booking using reference and email
  lookupBooking(): void {
    this.lookupError = '';
    this.lookupMessage = '';
    this.foundBooking = null;

    if (!this.searchReference.trim() || !this.searchEmail.trim()) {
      this.lookupError =
        'Please enter booking reference and email.';
      return;
    }

    this.isSearching = true;

    const reference = encodeURIComponent(
      this.searchReference.trim()
    );

    const email = encodeURIComponent(
      this.searchEmail.trim()
    );

    this.http.get<BookingResponse>(
      `${this.bookingApiUrl}/${reference}?email=${email}`
    ).subscribe({
      next: (booking) => {
        this.foundBooking = booking;
        this.isSearching = false;
      },
      error: () => {
        this.lookupError =
          'Booking not found. Please check your details.';
        this.isSearching = false;
      }
    });
  }

  // Cancel booking
  cancelBooking(): void {
    if (!this.foundBooking) {
      return;
    }

    if (this.foundBooking.status === 'CANCELLED') {
      this.lookupError = 'This booking is already cancelled.';
      return;
    }

    this.isCancelling = true;
    this.lookupError = '';

    const reference = encodeURIComponent(
      this.foundBooking.bookingReference
    );

    const email = encodeURIComponent(
      this.searchEmail.trim()
    );

    this.http.delete(
      `${this.bookingApiUrl}/${reference}?email=${email}`,
      { responseType: 'text' }
    ).subscribe({
      next: () => {
        if (this.foundBooking) {
          this.foundBooking.status = 'CANCELLED';
        }

        this.lookupMessage = 'Booking cancelled successfully.';
        this.isCancelling = false;
      },
      error: () => {
        this.lookupError =
          'Unable to cancel booking. Please try again.';
        this.isCancelling = false;
      }
    });
  }

  closeDetails(): void {
    this.selectedHotel = null;
    this.selectedRoom = null;
    this.rooms = [];
    this.bookingError = '';
    this.bookingMessage = '';
  }
}
