package io.github.felix.chatspringtest.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users") // Nom explicite pour la table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrémentation de l'ID
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true) // Email unique et obligatoire
    private String email;

    @Column(nullable = false)
    private String password;

    private String firstName;

    private String lastName;

    @Column(nullable = false, unique = true)
    private String pseudo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistered;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

    private boolean isActive;

    @Lob
    private byte[] avatar; // Remplacement de Blob par byte[]

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserRoom> userRoomList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "message", cascade = CascadeType.ALL)
    private List<Message> messageList;
}
