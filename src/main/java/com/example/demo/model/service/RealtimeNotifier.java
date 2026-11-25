package com.example.demo.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import com.example.demo.model.dto.SeatMessage;
import com.example.demo.model.dto.Notification;

@Component // [12주차] 단계3 : 트랜잭션이커밋된뒤에실시간메시지를보낸다
public class RealtimeNotifier {
    @Autowired
    private SimpMessagingTemplate messagingTemplate; // 브로커로메시지를보내는도구(스프링지원)

    @TransactionalEventListener // 기본= AFTER_COMMIT : 커밋이성공했을때만(롤백되면보내지않는다)
    public void sendSeats(SeatMessage message) {
        messagingTemplate.convertAndSend("/topic/seats", message); // /topic/seats 를구독한모든사람에게
    }
    
    @TransactionalEventListener // 단계4 : 개인알림도커밋뒤에
    public void sendNotification(Notification notification) {
        messagingTemplate.convertAndSendToUser(notification.to(), "/queue/notifications", notification); // 그사람에게만
    }
}