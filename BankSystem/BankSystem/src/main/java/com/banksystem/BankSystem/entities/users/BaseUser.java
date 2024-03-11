package com.banksystem.BankSystem.entities.users;

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
abstract public  class BaseUser implements Serializable{
    @Id

    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    public String userName;
    @Column(nullable = false)
    public String password;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String address;
}
