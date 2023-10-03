package com.alfish.arealmvp.model;

import com.alfish.arealmvp.enums.UserSex;
import com.alfish.arealmvp.util.InstantConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "user", schema = "public")
@Getter
@Setter
@ToString
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "reset_key")
    private String resetKey;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private UserSex sex;

    @Column(name = "birthday")
    @Convert(converter = InstantConverter.class)
    private Instant birthday;

    @Column(name = "creation_date")
    @Convert(converter = InstantConverter.class)
    private Instant creationDate;

    @Column(name = "views")
    private Integer views;

    @Column(name = "interactions")
    private Integer interactions;

    @Column(name = "writes")
    private Integer writes;

}
