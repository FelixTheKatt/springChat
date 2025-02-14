package io.github.felix.chatspringtest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "rooms")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private long id;

    @Column(nullable = false)
    private String roomName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date roomCreationDate;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "room",cascade = CascadeType.ALL)
    private List<UserRoom> userRoomList;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "room",cascade = CascadeType.ALL)
    private List<Message> messageList;
}
