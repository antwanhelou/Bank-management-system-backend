package com.banksystem.BankSystem.repository;

import com.banksystem.BankSystem.entities.otp.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OTPRepository extends JpaRepository<OTP, UUID> {


    public Optional<OTP> findByOtpAndEmail(int otp, String Email);

}
