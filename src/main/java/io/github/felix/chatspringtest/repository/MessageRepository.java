package io.github.felix.chatspringtest.repository;

import io.github.felix.chatspringtest.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // Trouver tous les messages d'un utilisateur spécifique
    List<Message> findByUserId(Long userId);

    List<Message> findByRoomId(Long roomId);
}