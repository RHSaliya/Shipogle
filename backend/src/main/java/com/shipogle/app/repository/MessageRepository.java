package com.shipogle.app.repository;

import com.shipogle.app.model.Message;
import com.shipogle.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySenderAndReceiverOrderByCreatedAtDesc(User sender, User receiver);

    List<Message> findByReceiverAndSenderOrderByCreatedAtDesc(User receiver, User sender);

    @Query("SELECT DISTINCT c.sender FROM Message c WHERE c.receiver = :receiver")
    List<User> findAllSenderIdsByReceiverId(@Param("receiver") User receiver);

    @Query("SELECT DISTINCT c.receiver FROM Message c WHERE c.sender = :sender")
    List<User> findAllReceiverIdsBySenderId(@Param("sender") User sender);


    // Query to find all users that the sender has chatted with
    /*@Query("SELECT DISTINCT m.receiver as user FROM Message m " +
            "UNION " +
            "SELECT DISTINCT m.sender as user FROM Message m")
    List<User> findAllChatUsers(@Param("user") User user);*/

//    @Query("SELECT DISTINCT c.sender FROM Message c WHERE c.receiver = :user UNION SELECT DISTINCT c.receiver FROM Message c WHERE c.sender = :user")
//    List<Long> findDistinctSenderAndReceiverIdsByUserId(@Param("user") User user);

    @Query(value = "SELECT DISTINCT m.receiver_id as id FROM messages as m WHERE m.sender_id = :userId UNION SELECT DISTINCT m.sender_id as id FROM messages as m WHERE m.receiver_id = :userId", nativeQuery = true)
    List<Integer> findDistinctSenderAndReceiverIdsByUserId(@Param("userId") Integer userId);

    void deleteMessagesByReceiverAndSender(User receiver, User sender);

}
