
USE hotel_booking;

#Sample Hotels
INSERT INTO hotel (id, name, location, description, amenities)
VALUES
(1, 'Grand Palace Hotel', 'Mumbai', 'Luxury hotel in Mumbai city', 'WiFi, Pool, Restaurant'),
(2, 'Sea View Resort', 'Goa', 'Beautiful beachside resort', 'WiFi, Beach Access, Pool'),
(3, 'Mountain Retreat', 'Manali', 'Peaceful mountain hotel', 'WiFi, Parking, Restaurant'),
(4, 'Royal Orchid Hotel', 'Pune', 'Comfortable business hotel', 'WiFi, Gym, Restaurant'),
(5, 'City Comfort Inn', 'Delhi', 'Affordable city hotel', 'WiFi, Parking, AC');

#Sample Rooms
INSERT INTO room (id, room_type, price, capacity, hotel_id)
VALUES
(1, 'Standard Room', 2500, 5, 1),
(2, 'Deluxe Room', 4000, 3, 1),
(3, 'Sea View Room', 3500, 5, 2),
(4, 'Premium Room', 5000, 2, 2),
(5, 'Standard Room', 2000, 5, 3),
(6, 'Mountain View Room', 3500, 3, 3),
(7, 'Deluxe Room', 3000, 4, 4),
(8, 'Executive Room', 4500, 2, 4),
(9, 'Standard Room', 1800, 5, 5),
(10, 'Family Room', 3200, 3, 5);
