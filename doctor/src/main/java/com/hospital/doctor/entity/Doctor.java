package com.hospital.doctor.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;
    private String fullName;
    private String qualification;
    private String specialization;
    private int experience;
    private String phone;
    private String email;
    private String status;
    private BigDecimal consultationFee;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", updatable = true)
    private LocalDateTime updatedAt;
    
    // Set createdAt automatically on INSERT
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null; // or LocalDateTime.now() if you want both same
    }

    //Set updatedAt automatically on UPDATE
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
