package com.example.tiketku_finalproject.Service;

import com.example.tiketku_finalproject.Firebase.NotificationMessage;
import com.example.tiketku_finalproject.Repository.NotificationMessageRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FirebaseMessagingService {
    @Autowired
    private FirebaseMessaging firebaseMessaging;
    @Autowired
    private NotificationMessageRepository repository;

    public String sendNotificationByToken(NotificationMessage notificationMessage){
        Notification notification = Notification
                .builder()
                .setTitle(notificationMessage.getTitle())
                .setBody(notificationMessage.getBody())
                .setImage(notificationMessage.getImage())
                .build();

        Message message = Message
                .builder()
                .setToken(notificationMessage.getRecipientToken())
                .setNotification(notification)
                .putAllData(notificationMessage.getData())
                .build();

        try {
            firebaseMessaging.send(message);
            return "Success Sending Notification";
        }catch (FirebaseMessagingException e){
            e.printStackTrace();
            return "Error Sending Message";
        }
    }

    @Scheduled(cron = "0/10 55-58 4 * * *")
    public String sendNotificationAutoByToken(NotificationMessage notificationMessage) {

        Notification notification = Notification.builder()
                .setTitle("Special Promo")
                .setBody("Just For U Get This Trip to Maldives For 99k Rupiah ")
                .setImage("https://minio.havehalalwilltravel.com/hhwt-upload/original_images/16012023053229_1622429872960_maldives2.png")
                .build();

        Message message = Message.builder()
                .setToken(notificationMessage.getRecipientToken())
                .setNotification(notification)
                .build();

        try {
            firebaseMessaging.send(message);
            return "Success Sending Notification";
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return "Error Sending Notification";

        }
    }

    public List<NotificationMessage> getByUUID(UUID uuidRequest) {
        return repository.findByUuidUser(uuidRequest);
    }
}
