package com.bluthinkInc.spring_security_project.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.HttpStatusCode;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String password;


    private String role;
}
