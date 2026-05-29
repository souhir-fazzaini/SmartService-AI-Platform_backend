package org.example.SmartService.services;

import org.example.SmartService.entity.Booking;
import org.example.SmartService.repositories.ServiceRepository;
import org.springframework.stereotype.Service;
import org.example.SmartService.repositories.BookingRepository;

import java.util.List;
import java.util.Date;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ServiceRepository serviceRepository;


    public BookingService(BookingRepository bookingRepository,ServiceRepository serviceRepository) {
        this.serviceRepository= serviceRepository;
        this.bookingRepository = bookingRepository;
    }

    // CREATE booking
    public Booking createBooking(Booking booking) {
        System.out.println("serviceId received: " + booking.getService());
        System.out.println("userId received: " + booking.getUserId());

        booking.setCreatedAt(new Date());
        booking.setUpdatedAt(new Date());
        booking.setStatus("PENDING");

        return bookingRepository.save(booking);
    }

    // GET all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // GET by id
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    // GET by user
    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    // UPDATE status
    public Booking updateStatus(Long id, String status) {
        Booking booking = getBookingById(id);
        booking.setStatus(status);
        booking.setUpdatedAt(new Date());
        return bookingRepository.save(booking);
    }

    // DELETE booking
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
