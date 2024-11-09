package com.example.notificationservice.service;

import com.example.notificationservice.model.Emprunt;
import com.example.notificationservice.notification.ConfirmationNotification;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.sqs.model.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class SqsListenerService {

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    public String queueUrl = "https://sqs.eu-north-1.amazonaws.com/774305596814/notification-sqs";

    public SqsListenerService(SqsClient sqsClient, EmailService emailService) {
        this.sqsClient = sqsClient;
        this.emailService = emailService;
        this.objectMapper = new ObjectMapper();
    }

    private void deleteMessageFromQueue(Message message) {
        // Delete the message from SQS after processing
        DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(message.receiptHandle())
                .build();

        try {
            sqsClient.deleteMessage(deleteMessageRequest);
        } catch (Exception e) {
            System.err.println("Failed to delete message: " + e.getMessage());
        }
    }

    @Scheduled(fixedRate = 5000) // Poll every 5 seconds, adjust as needed
    public void pollMessages() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(10)  // Adjust based on your needs
                .waitTimeSeconds(20)  // Long polling for better efficiency
                .build();

        List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();
        for (Message message : messages) {
            try {
                // Deserialize the message to Emprunt
                Emprunt emprunt = objectMapper.readValue(message.body(), Emprunt.class);

                // Process the message (your business logic here)
                emailService.sendConfirmationNotificationEmail(new ConfirmationNotification(new Date(), emprunt));

                // After processing, delete the message
               deleteMessageFromQueue(message);

            } catch (IOException e) {
                System.err.println("Error deserializing message: " + e.getMessage());
            } catch (SdkClientException e) {
                System.err.println("SQS client error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected error while processing message: " + e.getMessage());
            }
        }
    }
}

