package com.hospital.doctor.service;

import java.util.List;
import com.hospital.doctor.entity.Doctor;
import com.hospital.doctor.dto.DoctorAvailabilityDTO;
import com.hospital.doctor.dto.DoctorDTO;
import com.hospital.doctor.entity.DoctorAvailability;

public interface DoctorService {
    Doctor createDoctor(DoctorDTO  doctorDTO, Long userId);

    Doctor getDoctorProfile(Long userId);
    
    Doctor updateDoctorProfile(Long userId, DoctorDTO doctorDTO);

    DoctorAvailability addAvailability(Long doctorId, DoctorAvailabilityDTO availabilityDTO);

    List<DoctorAvailability> getAvailability(Long doctorId);
}
