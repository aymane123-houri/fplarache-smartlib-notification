package com.example.notificationservice.service;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SqsProducerService {

    private final SqsClient sqsClient;

    @Value("${aws.sqs.queue.url}")
    private String queueUrl;

    @Autowired
    public SqsProducerService(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    public void sendNotification(String messageBody) {
        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .build();
        sqsClient.sendMessage(sendMsgRequest);
    }
}

