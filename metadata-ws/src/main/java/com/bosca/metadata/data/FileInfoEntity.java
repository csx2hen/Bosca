package com.bosca.metadata.data;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "files")
public class FileInfoEntity implements Serializable {
    private static final long serialVersionUID = 9101571567826004558L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String fileId;

    @Column(nullable = false, length = 120)
    private String filename;

    @Column(nullable = false)
    private String owner;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
