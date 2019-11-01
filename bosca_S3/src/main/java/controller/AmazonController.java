package controller;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class AmazonController {
    @Value("${aws.accessKey}")
    private String accessKeyID = "AKIAWAV7CBP3SGZV36HH";
    @Value("${aws.secretKey}")
    private String secretKey = "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";
    @Value("${aws.Bucket}")
    private String bucket = "bosca";

    private AmazonS3 s3client;

    public AmazonController(){
        AWSCredentials credentials = new BasicAWSCredentials(accessKeyID, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setSignerOverride("S3SignerType");//凭证验证方式
        clientConfig.setProtocol(Protocol.HTTP);//访问协议
        s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_WEST_1)
                .build();
    }

    @RequestMapping(value = "/amazonS3/upload", method = RequestMethod.POST)
    private boolean upload(@RequestBody MultipartFile file){
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            if (file == null){
                System.out.println("no file!");
                return false;
            }
            System.out.println(file.isEmpty());
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            metadata.addUserMetadata("x-amz-meta-title", "aws file");
            PutObjectResult result = s3client.putObject(new PutObjectRequest(bucket, "1/" + file.getOriginalFilename(), file.getInputStream(), metadata));
            return true;
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
            return false;
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/amazonS3/list", method = RequestMethod.GET)
    private ArrayList<String> listObjects(){
        ArrayList<String> arrayList= new ArrayList<>();
        ObjectListing objectListing = s3client.listObjects(bucket);
        for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
            arrayList.add(os.getKey());
        }
        return arrayList;
    }

    @RequestMapping(value = "/download/", method = RequestMethod.POST)
    private MultipartFile download(@PathVariable String object) throws IOException {
        S3Object s3object = s3client.getObject(bucket, object);
        S3ObjectInputStream inputStream = s3object.getObjectContent();
        MultipartFile multipartFile = new MockMultipartFile(object, object, "text/plain", inputStream);
        return multipartFile;
    }
}