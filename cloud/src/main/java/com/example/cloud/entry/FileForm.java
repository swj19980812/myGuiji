package com.example.cloud.entry;

import lombok.Data;

/**
 * Create by tianci
 * 2019/1/10 16:33
 */
@Data
public class FileForm {

    private String md5;

    private String uuid;

    private String date;

    private String name;

    private String size;

    private String total;

    private String index;

    private String action;

    private String partMd5;

    public String getMd5() {
        return md5;
    }

    public String getUuid() {
        return uuid;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getTotal() {
        return total;
    }

    public String getIndex() {
        return index;
    }

    public String getAction() {
        return action;
    }

    public String getPartMd5() {
        return partMd5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setPartMd5(String partMd5) {
        this.partMd5 = partMd5;
    }
}
