package com.hospital.doctor.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.doctor.dto.DoctorAvailabilityDTO;
import com.hospital.doctor.dto.DoctorDTO;
import com.hospital.doctor.dto.PrescriptionResponseDTO;
import com.hospital.doctor.entity.Doctor;
import com.hospital.doctor.entity.DoctorAvailability;
import com.hospital.doctor.service.DoctorPrescriptionService;
import com.hospital.doctor.service.DoctorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

	@Autowired
	private DoctorService service;

	private final DoctorPrescriptionService doctorPrescriptionService;

	@PostMapping("/{doctorId}/patients/{patientId}/prescriptions")
	public PrescriptionResponseDTO createPrescription(@RequestHeader("Authorization") String token,
			@PathVariable Long doctorId, @PathVariable Long patientId, @PathVariable Long appointmentId) {

		return doctorPrescriptionService.createPrescription(token, doctorId, patientId, appointmentId);
	}

	// Add doctor details using POST API
	@PostMapping
	public Doctor createDoctor(@Valid @RequestBody DoctorDTO doctorDto, Authentication authentication) {

		Long userId = (Long) authentication.getPrincipal();
		return service.createDoctor(doctorDto, userId);
	}

	// Get doctor details
	@GetMapping("/profile")
	public Doctor getProfile(Authentication authentication) {
		Long userId = (Long) authentication.getPrincipal();
		return service.getDoctorProfile(userId);
	}

	// update doctor details by userId
     @PutMapping("/profile")
	public Doctor updateProfile(@Valid @RequestBody DoctorDTO doctorDTO, Authentication authentication) {

		Long userId = (Long) authentication.getPrincipal();
		return service.updateDoctorProfile(userId, doctorDTO);
	}

	// Add doctor availability details
	@PostMapping("/{doctorId}/availability")
	public DoctorAvailability addAvailability(@PathVariable Long doctorId,
			@Valid @RequestBody DoctorAvailabilityDTO availabilityDTO) {

		return service.addAvailability(doctorId, availabilityDTO);
	}

	// Get doctor availability details
	@GetMapping("/{doctorId}/availability")
	public List<DoctorAvailability> getAvailability(@PathVariable Long doctorId) {
		return service.getAvailability(doctorId);
	}
}