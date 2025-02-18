package io.github.felix.chatspringtest.service;

import io.github.felix.chatspringtest.dto.UserDto;
import io.github.felix.chatspringtest.entity.User;
import io.github.felix.chatspringtest.exception.userException.UserNotFoundException;
import io.github.felix.chatspringtest.repository.UserRepository;
import io.github.felix.chatspringtest.utils.mapper.GenericMapper;
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

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> GenericMapper.map(user,UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> GenericMapper.map(user,UserDto.class))
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
    }

    public List<UserView> getActiveUsers() {
        return userRepository.findByActiveTrue();
    }

    @Transactional
    public UserDto createUser(UserDto userDTO) {
        // Mapper UserDto vers User (Entity)
        User user = GenericMapper.map(userDTO, User.class);

        // Définir une valeur supplémentaire
        user.setActive(true);

        // Sauvegarde en base de données
        user = userRepository.save(user);

        // Mapper User (Entity) vers UserDto avant de retourner
        return GenericMapper.map(user, UserDto.class);
    }

    @Transactional
    public Optional<UserDto> updateUser(Long id, UserDto userDto) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setPseudo(userDto.getPseudo());
            existingUser.setEmail(userDto.getEmail());
            existingUser.setActive(userDto.isActive());
            userRepository.save(existingUser);
            return GenericMapper.map(existingUser, UserDto.class);
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
