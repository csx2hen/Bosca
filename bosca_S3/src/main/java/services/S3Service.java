package services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import shared.S3FileDto;

import java.io.IOException;


import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public class S3Service {

    @Value("${aws.accessKey}")
    private String accessKeyID="AKIA2BZ3MX6ECQOVXOUN";
    @Value("${aws.secretKey}")
    private String secretKey="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";
    @Value("${aws.Bucket}")
    private String bucket="bosca-temp";

    private AmazonS3 s3client;

    public S3Service(){
        AWSCredentials credentials = new BasicAWSCredentials(accessKeyID, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setSignerOverride("S3SignerType");
        clientConfig.setProtocol(Protocol.HTTP);
        s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_WEST_1)
                .build();
    }

    public String upload(S3FileDto file){
        String message = " ";
        try {

            if (file == null){
                System.out.println("no file!");
                return message;
            }

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getFile().getSize());
            //file.setMetadata(metadata);
            PutObjectResult result = s3client.putObject(new PutObjectRequest(bucket, file.getFileID(), file.getFile().getInputStream(), file.getMetadata()));
            System.out.println("fileID:"+file.getFileID());
            return file.getFileID();
        } catch (AmazonServiceException | IOException e) {
            e.printStackTrace();
            return message;
        } catch (SdkClientException e) {
            e.printStackTrace();
            return message;
        }
    }

    public ArrayList<String> listObjects(){
        ArrayList<String> arrayList= new ArrayList<>();
        ObjectListing objectListing = s3client.listObjects(bucket);
        for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
            arrayList.add(os.getKey());
        }
        return arrayList;
    }

    public MultipartFile downloadmultipartFile(String fileID) throws IOException {
        String object = fileID;
        S3Object s3object = s3client.getObject(bucket, object);
        S3ObjectInputStream inputStream = s3object.getObjectContent();
        MultipartFile multipartFile = new MockMultipartFile(object, object, "text/plain", inputStream);
        return multipartFile;
    }
/*
    public String downloadFile(S3FileDto file) throws IOException {
        String message="done";
        S3Object s3object = s3client.getObject(bucket, file.getFileID());
        if(s3object == null)message="no such file";
        else{
            S3ObjectInputStream inputStream = s3object.getObjectContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter pw = new PrintWriter(file.getDownloadPath());
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                pw.println(line);
            }
            reader.close();
            pw.close();
        }
        return message;
    }
*/
    public String deleteFile(S3FileDto file){
        String message="1";
        S3Object s3object = s3client.getObject(bucket, file.getFileID());
        if(s3object == null)message="0";
        else {
            s3client.deleteObject(bucket, file.getFileID());
        }
        return message;
    }

    public byte[] amazonS3Downloading(S3FileDto file) throws IOException {
        S3Object object = s3client.getObject(new GetObjectRequest(bucket,file.getFileID()));
        InputStream input = null;
        if(object!=null){
            input=object.getObjectContent();
            return IOUtils.toByteArray(input);
        }
        return null;
    }


    @Test
    public void test() throws IOException {
        S3FileDto f = new S3FileDto();

    }

}
