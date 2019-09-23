package com.example.cloud.entry;

import org.springframework.web.multipart.MultipartFile;

public class UpLoadInfo {
    private MultipartFile fileName;
    private String toPath;

    public MultipartFile getFileName() {
        return fileName;
    }

    public void setFileName(MultipartFile fileName) {
        this.fileName = fileName;
    }

    public String getToPath() {
        return toPath;
    }

    public void setToPath(String toPath) {
        this.toPath = toPath;
    }

    public UpLoadInfo() {
    }
}
