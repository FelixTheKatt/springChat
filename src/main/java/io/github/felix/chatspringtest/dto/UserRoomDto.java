package io.github.felix.chatspringtest.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserRoomDto {
    private long userId;
    private long roomID;
    private boolean isActive;
    private Date joinedAt;
}
