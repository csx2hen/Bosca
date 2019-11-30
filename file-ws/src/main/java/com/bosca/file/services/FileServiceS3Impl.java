package com.bosca.file.services;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.xspec.S;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class FileServiceS3Impl implements FileService {

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    private AmazonS3 s3client;


    public FileServiceS3Impl(@Value("${aws.s3.accessKey}") String accessKey,
                             @Value("${aws.s3.secretKey}") String secretKey) {
        System.out.println("accessKey" + accessKey);
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_WEST_1)
                .build();
    }

    @Override
    public void uploadFile(String fileName, InputStream fileInputStream) {
        PutObjectResult result = s3client.putObject(bucketName, fileName, fileInputStream, null);
    }

    @Override
    public InputStream downloadFile(String fileName) {
        S3Object s3Object = s3client.getObject(bucketName, fileName);
        return s3Object.getObjectContent();
    }

    @Override
    public void removeFile(String fileName) {
        s3client.deleteObject(bucketName, fileName);
    }
}
