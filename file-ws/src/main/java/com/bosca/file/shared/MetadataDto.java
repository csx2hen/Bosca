package com.bosca.file.shared;

import java.util.Date;

public class MetadataDto {

    private long size;
    private Date createdTime;
    private Date lastModifiedTime;

    public MetadataDto(long size, Date createdTime, Date lastModifiedTime) {
        this.size = size;
        this.createdTime = createdTime;
        this.lastModifiedTime = lastModifiedTime;
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
}
