package io.github.felix.chatspringtest.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDto {
    private Long id;
    private String email;
    private String pseudo;
    private String firstName;
    private String lastName;
    private String telephoneNumber;
    private Date dateRegistered;
    private Date lastLogin;
    private boolean active;

}