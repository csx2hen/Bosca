package shared;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public class S3FileDto {

    private String fileID;
    @JsonSerialize
    private MultipartFile file;
    private ObjectMetadata metadata = new ObjectMetadata();

    public MultipartFile getFile() { return file; }

    public void setFile(MultipartFile file) { this.file = file; }

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    public ObjectMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(ObjectMetadata metadata) {
        this.metadata = metadata;
    }

}
