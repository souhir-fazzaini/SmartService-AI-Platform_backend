package org.example.SmartService.controllers;

import org.example.SmartService.entity.Booking;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.SmartService.services.BookingService;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // CREATE booking
    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        System.out.println("==> Raw booking received: " + booking);
        System.out.println("==> userId: " + booking.getUserId());
        return bookingService.createBooking(booking);
    }

    // GET all bookings
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // GET booking by id
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    // GET bookings by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUser(userId));
    }

    // UPDATE status
    @PutMapping("/{id}/status")
    public ResponseEntity<Booking> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(bookingService.updateStatus(id, status));
    }

    // DELETE booking
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
