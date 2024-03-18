package com.banksystem.BankSystem.services;


import com.banksystem.BankSystem.DTOs.OtpDTO;
import com.banksystem.BankSystem.entities.otp.OTP;
import com.banksystem.BankSystem.repository.OTPRepository;
import com.banksystem.BankSystem.utilities.VerificationCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Random;

@Service
public class VerificationService {

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private EmailService emailService;

    Random randomObject = new Random();

    public void sendOTP(String email){
        String verificationCode = VerificationCodeUtil.generateVerificationCode();
        OTP otp = OTP.builder().build();
        otp.setOtp(generateOTPCode());
        otp.setEmail(email);
        otp.setExpirationTime(Instant.now().plusSeconds(60 * 120));
        String emailBody = "Welcome to AnHus Bank! Please verify your email using this code: " + verificationCode;
        emailService.sendVerificationMessage(email, "Email Verification", emailBody);

    }

    private int generateOTPCode(){
        int otp = 100000;
        otp += randomObject.nextInt(900000);
        return otp;
    }

//    public ResponseEntity<Map<String, String>> verifyOTP(OtpDTO otpDTO){
//
//    }

}
