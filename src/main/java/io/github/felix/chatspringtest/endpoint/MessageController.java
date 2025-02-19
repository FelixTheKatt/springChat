package io.github.felix.chatspringtest.endpoint;

import io.github.felix.chatspringtest.dto.MessageDto;
import io.github.felix.chatspringtest.dto.UserDto;
import io.github.felix.chatspringtest.entity.Message;
import io.github.felix.chatspringtest.service.MessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Tag(name = "Message APi", description = "Gestion des messages")
public class MessageController {
    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<List<MessageDto>> getMessages() { return ResponseEntity.ok(messageService.findAll());}

    @GetMapping("/{id}")
    public ResponseEntity<MessageDto> getMessageById(@PathVariable long id) { return ResponseEntity.ok(messageService.getMessageById(id));}

    @PostMapping
    public ResponseEntity<MessageDto> createMessage(@RequestBody MessageDto messageDto) {return ResponseEntity.ok(messageService.saveMessage(messageDto));}

    @PutMapping("/{id}")
    public ResponseEntity<MessageDto> updateMessage(@PathVariable long id,@RequestBody MessageDto messageDto) {
        Optional<MessageDto> updatedMessage = messageService.updateMessage(id, messageDto);
        return updatedMessage.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> deleteMessage(@PathVariable long id){
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}
