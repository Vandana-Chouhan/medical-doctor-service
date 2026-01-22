package com.hospital.doctor.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DoctorAvailabilityDTO {

    @NotBlank(message = "Day of week is required")
    private String dayOfWeek;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;
}
