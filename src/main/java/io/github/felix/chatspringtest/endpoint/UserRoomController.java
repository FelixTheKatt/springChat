package io.github.felix.chatspringtest.endpoint;

import io.github.felix.chatspringtest.dto.UserRoomDto;
import io.github.felix.chatspringtest.entity.UserRoom;
import io.github.felix.chatspringtest.service.UserRoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user_room")
@RequiredArgsConstructor
@Tag(name = "User room API", description = "Assignation des users au room")
public class UserRoomController {
    private final UserRoomService userRoomService;

    @PostMapping
    public ResponseEntity<UserRoomDto> assignUserRoom(@RequestBody UserRoomDto userRoomDto) {
        UserRoomDto assignedRoom = userRoomService.assignRoom(userRoomDto);
        return ResponseEntity.ok(assignedRoom);
    }
}
