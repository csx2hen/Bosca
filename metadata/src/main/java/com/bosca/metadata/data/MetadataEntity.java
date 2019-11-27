package com.bosca.metadata.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "metadata")
public class MetadataEntity implements Serializable {
    private static final long serialVersionUID = -6865808063016155013L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String fileId;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, unique = false)   //同一用户验证唯一文件名
    private String filename;

    @Column(nullable = true, unique = false)
    private String parentPath;

    @Column(nullable = false, unique = false)
    private String createTime;

    @Column(nullable = false, unique = false)
    private String modifyTime;

    @Column(nullable = false, unique = false)
    private String isFolder;

    @Column(nullable = false, unique = false)
    private String isExist;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileID(String fileId) {
        this.fileId = fileId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(String isFolder) {
        this.isFolder = isFolder;
    }

    public String getIsExist() {
        return isExist;
    }

    public void setIsExist(String isExist) {
        this.isExist = isExist;
    }
}
