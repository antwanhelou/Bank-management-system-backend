package com.banksystem.BankSystem.entities.users;

import com.banksystem.BankSystem.DTOs.BaseUserDTO;
import com.banksystem.BankSystem.entities.schedule.CustomerRequest;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;



@Data
@SuperBuilder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"id", "name"})
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

@EqualsAndHashCode(of = "id")
abstract public class BaseUser implements Serializable{

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "UUID", type = org.hibernate.id.uuid.UuidGenerator.class)
    @Column(nullable = false, name = "user_id")
    private UUID id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String address;

    @Column
    private boolean isVerified;


    @Column
    private String phoneNumber;
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
