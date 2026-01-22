package com.hospital.doctor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.doctor.entity.DoctorAvailability;

public interface DoctorAvailabilityRepository
extends JpaRepository<DoctorAvailability, Long> {

List<DoctorAvailability> findByDoctorId(Long doctorId);
}

