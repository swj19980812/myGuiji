package com.example.cloud.entry;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Create by tianci
 * 2019/1/10 14:38
 */

@Entity
@Data
public class UploadFile {

    /* uuid */
    @Id
    private String fileId;

    /* 文件路径 */
    private String filePath;

    /* 文件大小 */
    private String fileSize;

    /* 文件后缀 */
    private String fileSuffix;

    /* 文件名字 */
    private String fileName;

    /* 文件md5 */
    private String fileMd5;

    /* 文件上传状态 */
    private Integer fileStatus;

    private Date createTime;

    private Date updateTime;

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public void setFileStatus(Integer fileStatus) {
        this.fileStatus = fileStatus;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFileId() {
        return fileId;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public Integer getFileStatus() {
        return fileStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }
}
