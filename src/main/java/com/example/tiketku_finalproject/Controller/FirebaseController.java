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

    @PostMapping
    public String sendNotificationByToken(@RequestBody NotificationMessage notificationMessage){
        return firebaseMessagingService.sendNotificationByToken(notificationMessage);
    }
    @GetMapping
    private List<NotificationMessage> getNotificationByUUID(@RequestParam UUID uuidRequest) {
        log.info("Notification sent successfully");
        return firebaseMessagingService.getByUUID(uuidRequest);
    }
}
