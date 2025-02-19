package io.github.felix.chatspringtest.dto;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RoomDto {

    private long id;

    private String roomName;

    private Date roomCreationDate;
}
