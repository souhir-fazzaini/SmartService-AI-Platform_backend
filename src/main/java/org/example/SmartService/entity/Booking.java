package org.example.SmartService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "bookings")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString  // add this to your Booking class
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = true)
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "service_id", insertable = false, updatable = false)
    private ServiceEntity service; // 👈 ajouté dans Booking.java

    @Column(name = "booking_date")
    private Date bookingDate;

    @Column(name = "status")
    private String status; // PENDING, CONFIRMED, CANCELLED

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
