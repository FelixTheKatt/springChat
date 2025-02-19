package io.github.felix.chatspringtest.dto;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDto {

    private long id;
    private String message;
    private byte[] image;
}
