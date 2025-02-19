package io.github.felix.chatspringtest.service;

import io.github.felix.chatspringtest.dto.UserRoomDto;
import io.github.felix.chatspringtest.entity.Room;
import io.github.felix.chatspringtest.entity.User;
import io.github.felix.chatspringtest.entity.UserRoom;
import io.github.felix.chatspringtest.repository.RoomRepository;
import io.github.felix.chatspringtest.repository.UserRepository;
import io.github.felix.chatspringtest.repository.UserRoomRepository;
import io.github.felix.chatspringtest.utils.mapper.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoomService {

    private final UserRoomRepository userRoomRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public UserRoomDto assignRoom(UserRoomDto userRoomDto) {
        User user = userRepository.findById(userRoomDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Room room = roomRepository.findById(userRoomDto.getRoomID()).orElseThrow(() -> new RuntimeException("Room not found"));
        UserRoom userRoom = GenericMapper.map(userRoomDto, UserRoom.class);
        userRoom.setRoom(room);
        userRoom.setUser(user);
        UserRoom savedUserRoom = userRoomRepository.save(userRoom);
        return GenericMapper.map(savedUserRoom,UserRoomDto.class);
    }
}
