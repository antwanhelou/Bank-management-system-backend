package com.banksystem.BankSystem.services;


import com.banksystem.BankSystem.DTOs.NewUserCredentialsDTO;
import com.banksystem.BankSystem.DTOs.UserCredentialsDTO;
import com.banksystem.BankSystem.entities.users.BaseUser;
import com.banksystem.BankSystem.entities.users.UserCredentials;
import com.banksystem.BankSystem.exceptions.credentials_exceptions.ResetCredentialsException;
import com.banksystem.BankSystem.exceptions.credentials_exceptions.SameUserCredentialsException;
import com.banksystem.BankSystem.exceptions.credentials_exceptions.UserNameAlreadyExistsException;
import com.banksystem.BankSystem.exceptions.credentials_exceptions.WrongUserNameAndPasswordException;
import com.banksystem.BankSystem.repository.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;



import java.util.Optional;

@Service
public class RegistrationService {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserCredentials registerUser(UserCredentialsDTO userCredentialsDTO, BaseUser baseUser) throws UserNameAlreadyExistsException {
        String userName = userCredentialsDTO.getUserName();
        String password = userCredentialsDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        Optional<UserCredentials> result = userCredentialsRepository.findByUserNameAndPassword(userName, encodedPassword);

        if(result.isPresent()){
            throw new UserNameAlreadyExistsException("User Name selected is already used.");
        }
        UserCredentials userCredentials = UserCredentials.builder().userName(userName).password(encodedPassword).build();
        userCredentials.setBaseUser(baseUser);
        userCredentialsRepository.save(userCredentials);
        return userCredentials;
    }

    public void updateUserCredentials(NewUserCredentialsDTO newUserCredentialsDTO) throws ResetCredentialsException {
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
    }


    //todo add mechanism of resetting credentials
}
