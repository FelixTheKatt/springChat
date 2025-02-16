package io.github.felix.chatspringtest.configuration;

import io.github.felix.chatspringtest.dto.UserDto;
import io.github.felix.chatspringtest.entity.User;
import io.github.felix.chatspringtest.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByPseudo("admin").isEmpty()) {
            UserDto adminDto = new UserDto();
            adminDto.setEmail("admin@example.com");
            adminDto.setPseudo("admin");
            adminDto.setActive(true);
            adminDto.setTelephoneNumber("0601020304");

            User admin = new User();
            admin.setId(adminDto.getId());
            admin.setEmail(adminDto.getEmail());
            admin.setPseudo(adminDto.getPseudo());
            admin.setActive(adminDto.isActive());
            admin.setTelephoneNumber(adminDto.getTelephoneNumber());
            admin.setPassword(passwordEncoder.encode("admin123"));

            userRepository.save(admin);
        }

        if (userRepository.findByPseudo("user").isEmpty()) {
            UserDto userDto = new UserDto();
            userDto.setEmail("user@example.com");
            userDto.setPseudo("user");
            userDto.setActive(true);
            userDto.setTelephoneNumber("0611223344");

            User user = new User();
            user.setId(userDto.getId());
            user.setEmail(userDto.getEmail());
            user.setPseudo(userDto.getPseudo());
            user.setActive(userDto.isActive());
            user.setTelephoneNumber(userDto.getTelephoneNumber());
            user.setPassword(passwordEncoder.encode("user123"));

            userRepository.save(user);
        }
    }
}
