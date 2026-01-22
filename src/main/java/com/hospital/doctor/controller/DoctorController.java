package com.hospital.doctor.controller;

import java.util.List;

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
import com.hospital.doctor.entity.Doctor;
import com.hospital.doctor.entity.DoctorAvailability;
import com.hospital.doctor.service.DoctorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService service;
     // Add doctor details using POST API
    @PostMapping
    public Doctor createDoctor(
    		 @Valid @RequestBody DoctorDTO doctorDto ,
            @RequestHeader("X-User-Id") Long userId) {

        return service.createDoctor(doctorDto, userId);
    }
    // Get doctor details
    @GetMapping("/profile")
    public Doctor getProfile(
            @RequestHeader("X-User-Id") Long userId) {

        return service.getDoctorProfile(userId);
    }
    
    //update doctor details by userId
    @PutMapping("/profile")
    public Doctor updateProfile(
            @Valid @RequestBody DoctorDTO doctorDTO,
            @RequestHeader("X-User-Id") Long userId) {

        return service.updateDoctorProfile(userId, doctorDTO);
    }
    // Add doctor availability details
    @PostMapping("/{doctorId}/availability")
    public DoctorAvailability addAvailability(
            @PathVariable Long doctorId,
           @Valid @RequestBody DoctorAvailabilityDTO availabilityDTO) {

        return service.addAvailability(doctorId, availabilityDTO);
    }
     //Get doctor availability details 
    @GetMapping("/{doctorId}/availability")
    public List<DoctorAvailability> getAvailability(@PathVariable Long doctorId) {
        return service.getAvailability(doctorId);
    }
}