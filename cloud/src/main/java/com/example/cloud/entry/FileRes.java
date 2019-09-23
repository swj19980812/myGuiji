package com.example.cloud.entry;


//import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_file_res")
public class FileRes {
    @Id
    private Integer id;

    /**
     * 文件存储路径
     */
    private String path;

    /**
     * 文件大小
     */
    private Integer size;

    /**
     * 文件后缀
     */
    private String suffix;

    /**
     * 上传时文件名称
     */
    private String name;

    /**
     * 实际存储的名称
     */
    private String uuid;

    /**
     * 创建时间
     */
    //@JSONField(format="yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_Time")
    private Date createTime;

    /**
     * 文件md5值
     */
    private String md5;

    /**
     * 10表示可以正常播放；
     * 20表示转码失败；
     * 21表示审核失败；
     * 30表示正在处理过程中；
     * 31表示正在审核过程中；
     */
    private Integer status;

    private Long zhixueid;

    private String viewurl;

    /**
     * 视频在七牛云生成的智学云id
     */
    private Long qizhixueid;

    /**
     * 视频在阿里云生成的id
     */
    private String alivideoid;

    @Transient
    private String fileId;

    @Transient
    private String fileName;

    @Transient
    private boolean downLoad;

    /**
     * 资源下载地址
     */
    @Transient
    private String downUrl;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取文件存储路径
     *
     * @return path - 文件存储路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置文件存储路径
     *
     * @param path 文件存储路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取文件大小
     *
     * @return size - 文件大小
     */
    public Integer getSize() {
        return size;
    }

    /**
     * 设置文件大小
     *
     * @param size 文件大小
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * 获取文件后缀
     *
     * @return suffix - 文件后缀
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * 设置文件后缀
     *
     * @param suffix 文件后缀
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * 获取上传时文件名称
     *
     * @return name - 上传时文件名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置上传时文件名称
     *
     * @param name 上传时文件名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取实际存储的名称
     *
     * @return uuid - 实际存储的名称
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 设置实际存储的名称
     *
     * @param uuid 实际存储的名称
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isDownLoad() {
        return downLoad;
    }

    public void setDownLoad(boolean downLoad) {
        this.downLoad = downLoad;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getZhixueid() {
        return zhixueid;
    }

    public void setZhixueid(Long zhixueid) {
        this.zhixueid = zhixueid;
    }

    public String getViewurl() {
        return viewurl;
    }

    public void setViewurl(String viewurl) {
        this.viewurl = viewurl;
    }

    public Long getQizhixueid() {
        return qizhixueid;
    }

    public void setQizhixueid(Long qizhixueid) {
        this.qizhixueid = qizhixueid;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAlivideoid() {
        return alivideoid;
    }

    public void setAlivideoid(String alivideoid) {
        this.alivideoid = alivideoid;
    }
}