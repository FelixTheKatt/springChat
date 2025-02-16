package io.github.felix.chatspringtest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateDTO {
    private String email;
    private String pseudo;
    private String firstName;
    private String lastName;
    private String password; // On accepte le mot de passe à la création
    private String telephoneNumber;
}
