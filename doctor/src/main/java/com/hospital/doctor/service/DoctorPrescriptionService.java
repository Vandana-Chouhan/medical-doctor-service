package com.hospital.doctor.service;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.hospital.doctor.client.PrescriptionClient;
import com.hospital.doctor.dto.PrescriptionRequestDTO;
import com.hospital.doctor.dto.PrescriptionResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorPrescriptionService {

    private final PrescriptionClient prescriptionClient;

    public PrescriptionResponseDTO createPrescription(
            String token,
            Long doctorId,
            Long patientId,
            Long appointmentId) 
        {
        // 1️ Build request DTO (Doctor-side)
        PrescriptionRequestDTO dto = new PrescriptionRequestDTO();
        dto.setDoctorId(doctorId);
        dto.setPatientId(patientId);
        dto.setAppointmentId(appointmentId);
        
        dto.setDiagnosis("Viral Fever");
        dto.setNotes("Take rest and drink fluids");
        dto.setPrescribedDate(LocalDateTime.now());

        // 2️ Call Prescription microservice via Feign
        return prescriptionClient.createPrescription(token, dto);
    }
}