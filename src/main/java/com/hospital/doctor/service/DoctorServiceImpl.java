package com.hospital.doctor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.doctor.dto.DoctorDTO;
import com.hospital.doctor.dto.DoctorAvailabilityDTO;
import com.hospital.doctor.entity.Doctor;
import com.hospital.doctor.entity.DoctorAvailability;
import com.hospital.doctor.repository.DoctorAvailabilityRepository;
import com.hospital.doctor.repository.DoctorRepository;
import com.hospital.doctor.exception.ResourceNotFoundException;
import com.hospital.doctor.exception.BadRequestException;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorAvailabilityRepository availabilityRepository;
    
    //Add doctor details service method 
    @Override
    public Doctor createDoctor(DoctorDTO doctorDTO, Long userId) {

        Doctor doctor = new Doctor();
        doctor.setUserId(userId);
        doctor.setFullName(doctorDTO.getFullName());
        doctor.setQualification(doctorDTO.getQualification());
        doctor.setSpecialization(doctorDTO.getSpecialization());
        doctor.setExperience(doctorDTO.getExperience());
        doctor.setPhone(doctorDTO.getMobileNo());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setConsultationFee(doctorDTO.getConsultationFee());
        doctor.setStatus("ACTIVE");

        return doctorRepository.save(doctor);
    }
    // Get doctor details by user id
    @Override
    public Doctor getDoctorProfile(Long userId) {
        return doctorRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found with userId: " + userId
                        )
                );
    }
    //Update doctor details
    @Override
    public Doctor updateDoctorProfile(Long userId, DoctorDTO doctorDTO) {

        Doctor doctor = doctorRepository.findByUserId(userId)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Doctor not found with userId: " + userId)
                );

        // Update allowed fields only
        doctor.setFullName(doctorDTO.getFullName());
        doctor.setQualification(doctorDTO.getQualification());
        doctor.setSpecialization(doctorDTO.getSpecialization());
        doctor.setExperience(doctorDTO.getExperience());
        doctor.setPhone(doctorDTO.getMobileNo());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setConsultationFee(doctorDTO.getConsultationFee());

        return doctorRepository.save(doctor);
    }
    // Add doctor availability
     @Override
    public DoctorAvailability addAvailability(Long doctorId, DoctorAvailabilityDTO dto) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found with id: " + doctorId
                        )
                );

        if (dto.getStartTime().isAfter(dto.getEndTime()) ||
            dto.getStartTime().equals(dto.getEndTime())) {
            throw new BadRequestException("Start time must be before end time");
        }

        DoctorAvailability availability = new DoctorAvailability();
        availability.setDoctorId(doctorId);
        availability.setDayOfWeek(dto.getDayOfWeek());
        availability.setStartTime(dto.getStartTime());
        availability.setEndTime(dto.getEndTime());

        return availabilityRepository.save(availability);
    }
     // Get doctor availability details
    @Override
    public List<DoctorAvailability> getAvailability(Long doctorId) {
        return availabilityRepository.findByDoctorId(doctorId);
    }
}
