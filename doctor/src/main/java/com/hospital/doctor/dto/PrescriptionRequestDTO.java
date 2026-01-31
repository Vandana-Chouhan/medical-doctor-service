package com.hospital.doctor.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PrescriptionRequestDTO {

	private Long doctorId;
    private Long patientId;
    private Long appointmentId;
    private String diagnosis;
    private String notes;
    private LocalDateTime prescribedDate;
}
