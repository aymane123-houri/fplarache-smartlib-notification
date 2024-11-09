package com.example.notificationservice.service;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import org.springframework.stereotype.Service;


@Service
public class SqsProducerService {

    private final SqsClient sqsClient;
    String queueUrl = "https://sqs.eu-north-1.amazonaws.com/774305596814/notification-sqs";

    public SqsProducerService(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    public void sendMessage(String messageBody) {
        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .build();
        sqsClient.sendMessage(sendMsgRequest);
    }
}

