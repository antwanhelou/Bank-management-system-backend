package com.banksystem.BankSystem.services;

import com.banksystem.BankSystem.DTOs.AdminDTO;
import com.banksystem.BankSystem.DTOs.BankAccountDTO;
import com.banksystem.BankSystem.DTOs.BaseUserDTO;
import com.banksystem.BankSystem.DTOs.UserCredentialsDTO;
import com.banksystem.BankSystem.entities.users.Admin;
import com.banksystem.BankSystem.entities.users.Customer;
import com.banksystem.BankSystem.exceptions.UserNotFoundException;
import com.banksystem.BankSystem.repository.AdminRepository;
import com.banksystem.BankSystem.repository.BaseUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

public class AdminService extends BaseUserService<Admin> {


    @Autowired
    AdminRepository adminRepository;


    public ResponseEntity<Map<String, String>> addAdmin(final UserCredentialsDTO userCredentialsDTO) {
        final Admin admin = Admin.builder().build();
        return this.registerUser(userCredentialsDTO, admin);
    }


    public ResponseEntity<Map<String, String>> updateAdmin(final BaseUserDTO baseUserDTO) throws UserNotFoundException {
        return updateBaseUserDetails(baseUserDTO);
    }

    public ResponseEntity<Map<String, String>> deleteAdmin(final BaseUserDTO baseUserDTO) throws UserNotFoundException {
        return deleteBaseUser(baseUserDTO);
    }

    public ResponseEntity<AdminDTO> getAdmin(final UUID adminID) throws UserNotFoundException {
        Admin admin = getUser(adminID);
        AdminDTO adminDTO = AdminDTO.builder().build();
        adminDTO.set(admin);
        return new ResponseEntity<>(adminDTO, HttpStatus.OK);
    }

    public ResponseEntity<Iterable<AdminDTO>> getAllCustomer(){
        Iterable<Admin> admins = this.getAllUsers();
        List<AdminDTO> adminsDTOs = new ArrayList<>();
        for(Admin admin: admins){
            AdminDTO adminDTO = AdminDTO.builder().build();
            adminDTO.set(admin);
            adminsDTOs.add(adminDTO);
        }
        return new ResponseEntity<>(adminsDTOs, HttpStatus.OK);
    }




    @Override
    protected BaseUserRepository<Admin> getUserRepository() {
        return this.adminRepository;
    }




}
