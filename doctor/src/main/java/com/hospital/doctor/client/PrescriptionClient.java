package com.hospital.doctor.client;

import com.hospital.doctor.dto.PrescriptionRequestDTO;
import com.hospital.doctor.dto.PrescriptionResponseDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
    name = "prescription-service",
    url = "http://localhost:8083"    // or we can add url inside application.properties or application.yml file
)
public interface PrescriptionClient {

    @PostMapping("/prescriptions")
    PrescriptionResponseDTO createPrescription(    //create proxy for prescription service 
    		//internally use RestTemplate -> build the request
    	
    	@RequestHeader("Authorization") String token, 
        @RequestBody PrescriptionRequestDTO dto
    );
}
