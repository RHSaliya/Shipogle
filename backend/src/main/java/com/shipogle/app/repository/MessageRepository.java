package com.shipogle.app.repository;

import com.shipogle.app.model.Message;
import com.shipogle.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySenderAndReceiverOrderByCreatedAtDesc(User sender, User receiver);

    List<Message> findByReceiverAndSenderOrderByCreatedAtDesc(User receiver, User sender);

}
