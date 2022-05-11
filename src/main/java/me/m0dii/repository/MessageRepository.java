package me.m0dii.repository;

import me.m0dii.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<List<Message>> findBySenderEmail(String senderEmail);

    Optional<List<Message>> findByReceiverEmail(String receiverEmail);

    int countBySenderEmail(String senderEmail);

    int countByReceiverEmail(String receiverEmail);

    Optional<Message> findFirstBySenderEmailOrderByTimestampDesc(String senderEmail);

    Optional<Message> findFirstBySenderEmailOrderByTimestampAsc(String senderEmail);
}
