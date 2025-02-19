package io.github.felix.chatspringtest.endpoint;

import io.github.felix.chatspringtest.dto.RoomDto;
import io.github.felix.chatspringtest.service.RoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
@Tag(name = "Room API", description = "Gestion des room")
public class RoomController {
    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRoom(){ return ResponseEntity.ok(roomService.getAllRoom()); }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long id){ return ResponseEntity.ok(roomService.getRoomById(id)); }

    @PostMapping
    public ResponseEntity<RoomDto> createRoom(@RequestBody RoomDto room){ return ResponseEntity.ok(roomService.createRoom(room)); }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> updatedRoom(@PathVariable long id, @RequestBody RoomDto roomDto){
        Optional<RoomDto> updatedRoom = roomService.updateRoom(id,roomDto);
        return updatedRoom.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> deletedRoom(@RequestBody RoomDto room){ return ResponseEntity.noContent().build(); }
}
