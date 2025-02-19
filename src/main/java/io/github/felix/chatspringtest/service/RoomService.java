package io.github.felix.chatspringtest.service;

import io.github.felix.chatspringtest.dto.RoomDto;
import io.github.felix.chatspringtest.entity.Room;
import io.github.felix.chatspringtest.repository.RoomRepository;
import io.github.felix.chatspringtest.utils.mapper.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public List<RoomDto> getAllRoom() {
        return roomRepository.findAll().stream()
                .map(room -> GenericMapper.map(room, RoomDto.class))
                .collect(Collectors.toList());
    }

    public RoomDto getRoomById(Long id) {
        return GenericMapper.map(roomRepository.findById(id), RoomDto.class);
    }

    @Transactional
    public RoomDto createRoom(RoomDto roomDto) {
        Room room = GenericMapper.map(roomDto, Room.class);
        room = roomRepository.save(room);

        return GenericMapper.map(room, RoomDto.class);
    }

    @Transactional
    public Optional<RoomDto> updateRoom(Long id , RoomDto roomDto) {
        return roomRepository.findById(id).map( room -> {
                    room.setRoomName(roomDto.getRoomName());
                    room.setRoomCreationDate(roomDto.getRoomCreationDate());
                    roomRepository.save(room);
                    return GenericMapper.map(room, RoomDto.class);
                });
    }

    public void deleteRoom(Long id) { roomRepository.deleteById(id);}

}
