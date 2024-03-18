package com.banksystem.BankSystem.services;


import com.banksystem.BankSystem.DTOs.NewUserCredentialsDTO;
import com.banksystem.BankSystem.DTOs.UserCredentialsDTO;
import com.banksystem.BankSystem.entities.otp.OTP;
import com.banksystem.BankSystem.entities.users.BaseUser;
import com.banksystem.BankSystem.entities.users.UserCredentials;
import com.banksystem.BankSystem.exceptions.credentials_exceptions.ResetCredentialsException;
import com.banksystem.BankSystem.exceptions.credentials_exceptions.SameUserCredentialsException;
import com.banksystem.BankSystem.exceptions.credentials_exceptions.UserNameAlreadyExistsException;
import com.banksystem.BankSystem.exceptions.credentials_exceptions.WrongUserNameAndPasswordException;
import com.banksystem.BankSystem.repository.UserCredentialsRepository;
import com.banksystem.BankSystem.utilities.ResultHolder;
import com.banksystem.BankSystem.utilities.VerificationCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Map;
import java.util.Optional;

@Service
public class RegistrationService {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationService verificationService;


    public UserCredentials registerUser(BaseUser user, UserCredentialsDTO userCredentialsDTO) throws UserNameAlreadyExistsException {
        Optional<UserCredentials> existingUser = userCredentialsRepository.findByUserName(userCredentialsDTO.getUserName());
        if (existingUser.isPresent()) {
            throw new UserNameAlreadyExistsException("User Name selected is already used.");
        }
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserName(userCredentialsDTO.getUserName());
        userCredentials.setPassword(passwordEncoder.encode(userCredentialsDTO.getPassword()));
        userCredentials.setEmail(userCredentialsDTO.getEmail());
        user.setVerified(false);
        userCredentials.setBaseUser(user);
        UserCredentials savedCredentials = userCredentialsRepository.save(userCredentials);
        verificationService.sendOTP(userCredentials.getEmail());
        return savedCredentials;
    }


    public ResponseEntity<Map<String, String>> updateUserCredentials(NewUserCredentialsDTO newUserCredentialsDTO) throws ResetCredentialsException {
        String userName = newUserCredentialsDTO.getUserName();
        String oldPassword = newUserCredentialsDTO.getOldPassword();
        String newPassword = newUserCredentialsDTO.getNewPassword();
        String encodedOldPassword = passwordEncoder.encode(oldPassword);

        Optional<UserCredentials> oldCredentials = userCredentialsRepository.findByUserNameAndPassword(userName, encodedOldPassword);
        if(oldCredentials.isEmpty()){
            throw new WrongUserNameAndPasswordException("One of the provided credentials is wrong");
        }
        UserCredentials userCredentials = oldCredentials.get();


        if(newPassword.equals(oldPassword)){
            throw new SameUserCredentialsException("Password Cannot be the same password");
        }
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        userCredentials.setPassword(encodedNewPassword);
        userCredentialsRepository.save(userCredentials);
        return new ResponseEntity<>(ResultHolder.success(), HttpStatus.OK);

    }


    //todo add mechanism of resetting credentials
}
