package com.hospital.doctor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.doctor.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	Optional<Doctor> findByUserId(Long userId);

	List<Doctor> findBySpecialization(String specialization);
}