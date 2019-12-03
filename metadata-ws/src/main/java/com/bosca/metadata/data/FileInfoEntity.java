package com.bosca.metadata.data;


import org.springframework.integration.annotation.Default;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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

    @Column(nullable = true)
    private long size;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedTime;

    @Column(nullable = false)
    private boolean isFolder = false;

    @Column(nullable = false)
    private String parentDir;


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

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public boolean getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(boolean folder) {
        isFolder = folder;
    }

    public String getParentDir() {
        return parentDir;
    }

    public void setParentDir(String parentDir) {
        this.parentDir = parentDir;
    }
}
