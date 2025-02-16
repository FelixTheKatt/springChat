package io.github.felix.chatspringtest.service;

import io.github.felix.chatspringtest.dto.UserDto;
import io.github.felix.chatspringtest.entity.User;
import io.github.felix.chatspringtest.exception.userException.UserNotFoundException;
import io.github.felix.chatspringtest.mapper.UserMapper;
import io.github.felix.chatspringtest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.felix.chatspringtest.dto.UserView;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
    }

    public List<UserView> getActiveUsers() {
        return userRepository.findByActiveTrue();
    }

    @Transactional
    public UserDto createUser(UserDto userDTO) {
        User user = userMapper.toEntity(userDTO);
        user.setActive(true);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Transactional
    public Optional<UserDto> updateUser(Long id, UserDto userDto) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setPseudo(userDto.getPseudo());
            existingUser.setEmail(userDto.getEmail());
            existingUser.setActive(userDto.isActive());
            userRepository.save(existingUser);
            return userMapper.toDto(existingUser);
        });
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean authenticateUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && user.get().getPassword().equals(password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByPseudo(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getPseudo(),
                        user.getPassword(),
                        Collections.emptyList())) // Ajouter les rôles si nécessaire
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé"));
    }

}
