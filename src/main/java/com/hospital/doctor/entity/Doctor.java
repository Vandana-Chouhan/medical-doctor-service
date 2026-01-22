package com.hospital.doctor.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "doctors")
@Getter @Setter
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;

    private Long userId;
    private String fullName;
    private String qualification;
    private String specialization;
    private int experience;
    private String phone;
    private String email;
    private String status;
    private BigDecimal consultationFee;
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
