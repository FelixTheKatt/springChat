package io.github.felix.chatspringtest.service;

import io.github.felix.chatspringtest.dto.MessageDto;
import io.github.felix.chatspringtest.entity.Message;
import io.github.felix.chatspringtest.repository.MessageRepository;
import io.github.felix.chatspringtest.utils.mapper.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public List<MessageDto> findAll() {
        return messageRepository.findAll().stream().map(message -> GenericMapper.map(message,MessageDto.class)).toList();
    }

    public MessageDto getMessageById(Long id) { return GenericMapper.map(messageRepository.findById(id),MessageDto.class);}

    @Transactional
    public MessageDto saveMessage(MessageDto messageDto) {
        Message message = GenericMapper.map(messageDto,Message.class);
        messageRepository.save(message);
        return GenericMapper.map(message,MessageDto.class);
    }

    @Transactional
    public Optional<MessageDto> updateMessage(long id, MessageDto messageDto) {
        Message message = GenericMapper.map(messageDto,Message.class);
        message.setMessage(messageDto.getMessage());
        message.setImage(messageDto.getImage());
        message = messageRepository.save(message);
        return Optional.ofNullable(GenericMapper.map(message, MessageDto.class));
    }

    public void deleteMessage(Long id) { messageRepository.deleteById(id);}
}
