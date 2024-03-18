package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.DTOs.BaseUserDTO;
import com.banksystem.BankSystem.DTOs.NewUserCredentialsDTO;
import com.banksystem.BankSystem.DTOs.UserCredentialsDTO;
import com.banksystem.BankSystem.entities.users.BaseUser;
import com.banksystem.BankSystem.entities.users.UserCredentials;
import com.banksystem.BankSystem.exceptions.object_not_found.UserNotFoundException;
import com.banksystem.BankSystem.exceptions.credentials_exceptions.ResetCredentialsException;
import com.banksystem.BankSystem.exceptions.credentials_exceptions.UserNameAlreadyExistsException;
import com.banksystem.BankSystem.repository.BaseUserRepository;
import com.banksystem.BankSystem.utilities.ResultHolder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseUserService<T extends BaseUser> {

    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private EmailService emailService;
    @Transactional
    public ResponseEntity<Map<String, String>> registerUser(UserCredentialsDTO userCredentialsDTO, T baseUser) throws UserNameAlreadyExistsException {

        UserCredentials userCredentials = registrationService.registerUser(baseUser, userCredentialsDTO);
        baseUser.setUserCredentials(userCredentials);

        this.getUserRepository().save(baseUser);
        return new ResponseEntity<>(ResultHolder.success(), HttpStatus.OK);


    }
    public ResponseEntity<Map<String, String>> updateUserPassword(NewUserCredentialsDTO newUserCredentialsDTO){
        try{
            registrationService.updateUserCredentials(newUserCredentialsDTO);
            return new ResponseEntity<>(ResultHolder.success(), HttpStatus.OK);
        } catch (ResetCredentialsException e) {
            return new ResponseEntity<>(ResultHolder.fail(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<Map<String, String>> updateBaseUserDetails(BaseUserDTO baseUserDTO) throws UserNotFoundException {
        final UUID id = baseUserDTO.getId();
        final T user = this.getUser(id);
        user.set(baseUserDTO);
        this.getUserRepository().save(user);
        return new ResponseEntity<>(ResultHolder.success(), HttpStatus.OK);
    }


    public ResponseEntity<Map<String, String>> deleteBaseUser(final UUID id) throws UserNotFoundException {


        final T user = this.getUser(id);
        this.getUserRepository().delete(user);
        return new ResponseEntity<>(ResultHolder.success(), HttpStatus.OK);
    }

    public T getUser(final UUID id) throws UserNotFoundException {
        final Optional<T> searchResult = this.getUserRepository().findById(id);
        if(searchResult.isEmpty()){
            throw new UserNotFoundException("Provided User ID does not match any user");
        }
        return searchResult.get();
    }

    public Iterable<T> getAllUsers(){
        return this.getUserRepository().findAll();
    }

    protected abstract BaseUserRepository<T> getUserRepository();


}
