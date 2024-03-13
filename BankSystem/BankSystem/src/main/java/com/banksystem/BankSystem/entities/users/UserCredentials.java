package com.banksystem.BankSystem.entities.users;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "credentials")
@ToString(of = {"userName", "password"})
@EqualsAndHashCode(of = {"id"})

public class UserCredentials {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", type = org.hibernate.id.uuid.UuidGenerator.class)
    @Column(name = "user_credentials_id")
    private UUID id = UUID.randomUUID();

    @Column
    private String userName;

    @Column
    private String password;

    @Column
    private String email;

    @OneToOne
    @JoinColumn(name = "user_credentials_id")
    private BaseUser baseUser;

}
