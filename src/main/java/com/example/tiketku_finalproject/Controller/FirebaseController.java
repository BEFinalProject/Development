package com.example.tiketku_finalproject.Controller;

import com.example.tiketku_finalproject.Service.FirebaseMessagingService;
import com.example.tiketku_finalproject.Firebase.NotificationMessage;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "Notification")
@Api("Notification")
@Slf4j
public class FirebaseController {

    @Autowired
    FirebaseMessagingService firebaseMessagingService;

    @PostMapping(value = "/postNotification")
    public String sendNotificationByToken(@RequestBody NotificationMessage notificationMessage){
        log.info("Received request to send notification with title: '{}' and body: '{}'", notificationMessage.getTitle(), notificationMessage.getBody());
        return firebaseMessagingService.sendNotificationByToken(notificationMessage);
    }
    @GetMapping
    private List<NotificationMessage> getNotificationByUUID(@RequestParam UUID uuidRequest) {
        log.info("Notification sent successfully");
        return firebaseMessagingService.getByUUID(uuidRequest);
    }
}
