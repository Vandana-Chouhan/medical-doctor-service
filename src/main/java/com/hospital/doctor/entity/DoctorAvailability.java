package com.hospital.doctor.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "doctor_availability")
@Getter @Setter
public class DoctorAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long availabilityId;

    private Long doctorId;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}