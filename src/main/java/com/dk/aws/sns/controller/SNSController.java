package com.dk.aws.sns.controller;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.dk.aws.sns.dto.Notification;
import com.dk.aws.sns.dto.Subscription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class SNSController {

    @Value(value = "${sns.arn}")
    private String SNSARN;

    @Autowired
    private AmazonSNSClient amazonSNSClient;

    @PostMapping(value = "/addSubscription")
    @ResponseStatus(code = HttpStatus.OK)
    public void addSubscription(@RequestBody Subscription subscription){
        log.info("Add new value {} with protocol {} subscription to the topic {}",subscription.getValue(),subscription.getProtocol(),SNSARN);
        SubscribeRequest request=new SubscribeRequest(SNSARN,subscription.getProtocol(),subscription.getValue());
        amazonSNSClient.subscribe(request);
        log.info("{} protocol {} added to the topic {} successfully",subscription.getValue(),subscription.getProtocol(),SNSARN);
    }

    @PostMapping(value = "/sendNotification")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<String> sendNotification(@RequestBody Notification notification){
        log.info("Publishing notification {} to the topic {}",notification,SNSARN);
        PublishRequest request=new PublishRequest(SNSARN, notification.getMessage());
        amazonSNSClient.publish(request);
        log.info("Notification {} published to the topic {} successfully!",notification,SNSARN);
        return  ResponseEntity.ok("Notification sent successfully!");
    }
}
