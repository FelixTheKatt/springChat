package io.github.felix.chatspringtest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "users_rooms") // Nom explicite pour la table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_room_id")
    private long id;

    private boolean isActive;

    @Temporal(TemporalType.TIMESTAMP)
    private Date joinedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id",nullable = false)
    private Room room;
}
