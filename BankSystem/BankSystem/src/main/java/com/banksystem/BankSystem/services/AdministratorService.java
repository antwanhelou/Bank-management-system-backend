package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.DTOs.AdministratorDTO;
import com.banksystem.BankSystem.DTOs.BaseUserDTO;
import com.banksystem.BankSystem.DTOs.UserCredentialsDTO;
import com.banksystem.BankSystem.beans.configurations.AdminConfig;
import com.banksystem.BankSystem.entities.users.Administrator;
import com.banksystem.BankSystem.exceptions.credentials_exceptions.UserNameAlreadyExistsException;
import com.banksystem.BankSystem.exceptions.object_not_found.UserNotFoundException;
import com.banksystem.BankSystem.repository.AdministratorRepository;
import com.banksystem.BankSystem.repository.BaseUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdministratorService extends BaseUserService<Administrator> {


    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    AdministratorRepository administratorRepository;

    public ResponseEntity<Map<String, String>> addAdmin(final UserCredentialsDTO userCredentialsDTO) throws UserNameAlreadyExistsException {
        final Administrator administrator = Administrator.builder().build();
        administrator.setAuthorized(adminConfig.getPrivilegedAdmins().contains(userCredentialsDTO.getEmail()));
        return this.registerUser(userCredentialsDTO, administrator);
    }

    public ResponseEntity<Map<String, String>> updateAdministratorDetails(final BaseUserDTO baseUserDTO) throws UserNotFoundException {
        return updateBaseUserDetails(baseUserDTO);
    }

    public ResponseEntity<Map<String, String>> deleteAdministrator(final UUID id) throws UserNotFoundException {
        return deleteBaseUser(id);
    }

    public ResponseEntity<AdministratorDTO> getAdministrator(final UUID adminID) throws UserNotFoundException {
        Administrator administrator = getUser(adminID);
        AdministratorDTO administratorDTO = AdministratorDTO.builder().build();
        administratorDTO.set(administrator);
        return new ResponseEntity<>(administratorDTO, HttpStatus.OK);
    }

    public ResponseEntity<Iterable<AdministratorDTO>> getAllAdministrators(){
        Iterable<Administrator> admins = this.getAllUsers();
        List<AdministratorDTO> adminsDTOs = new ArrayList<>();
        for(Administrator administrator : admins){
            AdministratorDTO administratorDTO = AdministratorDTO.builder().build();
            administratorDTO.set(administrator);
            adminsDTOs.add(administratorDTO);
        }
        return new ResponseEntity<>(adminsDTOs, HttpStatus.OK);
    }

    @Override
    protected BaseUserRepository<Administrator> getUserRepository() {
        return this.administratorRepository;
    }

}
