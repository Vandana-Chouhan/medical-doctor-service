package com.hospital.doctor.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DoctorDTO {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Qualification is required")
    private String qualification;

    @NotBlank(message = "Specialization is required")
    private String specialization;

    @Min(value = 0, message = "Experience cannot be negative")
    @Max(value = 50, message = "Experience seems invalid")
    private int experience;

    @NotBlank(message = "Phone number is required")
    @Pattern(
        regexp = "^[6-9][0-9]{9}$",
        message = "Invalid phone number"
    )
    private String mobileNo;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Consultation fee is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Fee must be greater than 0")
    private BigDecimal consultationFee;
}
