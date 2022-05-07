package me.m0dii.repository;

import me.m0dii.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long>
{
    Optional<List<Message>> findBySenderEmail(String senderEmail);
    Optional<List<Message>> findByReceiverEmail(String receiverEmail);
}
