package com.eaglepath.claims;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Pharmacy Claims API
 * A RESTful API for managing pharmacy claims and detecting potential fraud.
 * Built with Java 21, Spring Boot 3, JPA, and OpenAPI documentation.
 *
 * @author Diana Martinez
 */
@SpringBootApplication
public class PharmacyClaimsApplication {
    public static void main(String[] args) {
        SpringApplication.run(PharmacyClaimsApplication.class, args);
    }
}
