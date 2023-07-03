package com.example.tiketku_finalproject;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "Notification")
@Api("Notification")
public class NotificationController {
    @Autowired
    FirebaseMessageService firebaseMessageService;

    @PostMapping
    public String sendNotificationByToken(@RequestBody NotificationMessage notificationMessage){
        return firebaseMessageService.sendNotificationByToken(notificationMessage);
    }
}
