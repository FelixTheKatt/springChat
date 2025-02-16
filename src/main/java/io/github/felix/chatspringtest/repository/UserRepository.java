package io.github.felix.chatspringtest.repository;

import io.github.felix.chatspringtest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.github.felix.chatspringtest.dto.UserView;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String userEmail);
    List<UserView> findByActiveTrue();
    Optional<User> findByPseudo(String pseudo);
}
