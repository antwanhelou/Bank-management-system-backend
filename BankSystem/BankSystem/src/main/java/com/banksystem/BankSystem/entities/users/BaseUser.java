package com.banksystem.BankSystem.entities.users;

import com.banksystem.BankSystem.DTOs.BaseUserDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

@Data

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "name", "email"})
abstract public class BaseUser implements Serializable{

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", type = org.hibernate.id.uuid.UuidGenerator.class)
    @Column(nullable = false)
    private UUID id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private boolean isVerified;

    @Column
    private String nationalID;

    @OneToOne(mappedBy = "baseUser", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private UserCredentials userCredentials;


    public void set(BaseUserDTO baseUserDTO){
        this.name = baseUserDTO.getName();
        this.address = baseUserDTO.getAddress();
        this.userCredentials.setEmail(baseUserDTO.getEmail());
        if(baseUserDTO.isVerified()){
            this.isVerified = baseUserDTO.isVerified();
        }
    }

}
