package com.banksystem.BankSystem.controllers;

import com.banksystem.BankSystem.DTOs.EmailVerificationDTO;
import com.banksystem.BankSystem.entities.users.UserCredentials;
import com.banksystem.BankSystem.repository.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
@RestController
@RequestMapping("/api")
public class VerificationController {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @PostMapping("/verifyEmail")
    public ResponseEntity<?> verifyEmail(@RequestBody EmailVerificationDTO verificationDTO) {
        Optional<UserCredentials> userOpt = userCredentialsRepository.findByEmail(verificationDTO.getEmail());
        if (userOpt.isPresent()) {
            UserCredentials user = userOpt.get();
            if (user.getVerificationCode().equals(verificationDTO.getVerificationCode())) {
                user.setEmailVerified(true);
                user.setVerificationCode(null); // Clear the verification code
                userCredentialsRepository.save(user);
                return ResponseEntity.ok().body("Email successfully verified.");
            } else {
                return ResponseEntity.badRequest().body("Verification code is incorrect.");
            }
        }
        return ResponseEntity.badRequest().body("User not found.");
    }
}
