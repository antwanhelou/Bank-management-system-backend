package com.banksystem.BankSystem.entities.users;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@Builder(toBuilder = true)
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "name", "email"})
public abstract class BaseUser {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", parameters = {
            @org.hibernate.annotations.Parameter (name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy")})
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
