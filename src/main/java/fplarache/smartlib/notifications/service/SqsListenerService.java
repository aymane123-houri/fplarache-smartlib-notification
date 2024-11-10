package fplarache.smartlib.notifications.service;



import fplarache.smartlib.notifications.model.Emprunt;
import fplarache.smartlib.notifications.notification.ConfirmationNotification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.core.exception.SdkClientException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class SqsListenerService {

    @Value("${sqs.queue.url}")
    private String queueUrl;
    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    private static final int MAX_RETRIES = 3;

    public SqsListenerService(SqsClient sqsClient, EmailService emailService) {
        this.sqsClient = sqsClient;
        this.emailService = emailService;
        this.objectMapper = new ObjectMapper();
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
            processMessageWithRetry(message, MAX_RETRIES);
        }
    }

    private void processMessageWithRetry(Message message, int remainingRetries) {
        try {
            // Deserialize the message to Emprunt
            Emprunt emprunt = objectMapper.readValue(message.body(), Emprunt.class);

            // Process the message (business logic)
            emailService.sendConfirmationNotificationEmail(new ConfirmationNotification(new Date(), emprunt));

            // After processing, delete the message
            deleteMessageFromQueue(message);

        } catch (IOException e) {
            System.err.println("Error deserializing message: " + e.getMessage());
            retryOrLogFailure(message, remainingRetries, e);

        } catch (SdkClientException e) {
            System.err.println("SQS client error: " + e.getMessage());
            retryOrLogFailure(message, remainingRetries, e);

        } catch (Exception e) {
            System.err.println("Unexpected error while processing message: " + e.getMessage());
            retryOrLogFailure(message, remainingRetries, e);
        }
    }

    private void retryOrLogFailure(Message message, int remainingRetries, Exception e) {
        if (remainingRetries > 0) {
            System.out.println("Retrying message in 1 minute. Remaining retries: " + remainingRetries);
            try {
                Thread.sleep(60000); // Attendre 1 minute avant de réessayer
                processMessageWithRetry(message, remainingRetries - 1);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                System.err.println("Retry interrupted: " + ie.getMessage());
            }
        } else {
            System.err.println("Max retries reached. Message moved to DLQ or logged for manual handling: " + message.body());
        }
    }

    private void deleteMessageFromQueue(Message message) {
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
}


