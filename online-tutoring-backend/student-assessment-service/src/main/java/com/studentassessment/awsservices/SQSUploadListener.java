package com.studentassessment.awsservices;


import com.studentassessment.model.S3EventNotification;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class SQSUploadListener {
    private static final Logger LOG = LoggerFactory.getLogger(SQSUploadListener.class);
    private final S3Presigner s3Presigner;
    private final S3Client s3Client;

    @SqsListener("${questionpaper.queue.name}")
    public void processOrder(S3EventNotification s3EventNotificationRecord) {
        if (s3EventNotificationRecord != null && s3EventNotificationRecord.getRecords() != null) {
            S3EventNotification.S3 s3Entity = s3EventNotificationRecord.getRecords().get(0).getS3();
            LOG.info("Bucket Name {}", s3Entity.getBucket().getName());
            LOG.info("Key Name {}", s3Entity.getObject().getKey());


            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(s3Entity.getBucket().getName())
                    .key(s3Entity.getObject().getKey())
                    .build();
            GetObjectResponse getObjectResponse = s3Client.getObject(getObjectRequest).response();

            if (getObjectResponse.metadata().containsKey("assignmentid"))
                LOG.info("Assignment Id in Lister:{}", getObjectResponse.metadata().get("assignmentid"));
            GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(60))
                    .getObjectRequest(getObjectRequest)
                    .build();

            PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(getObjectPresignRequest);
            String theUrl = presignedGetObjectRequest.url().toString();

            System.out.println(theUrl);
        }


    }

}
