package com.example.cloud.entry;

import java.util.Map;

public class FileMessage {
    Map<String,Integer> file_type;
    Map<String,String>file_size;
    Map<String,String>file_time;

    public Map<String, Integer> getFile_type() {
        return file_type;
    }

    public void setFile_type(Map<String, Integer> file_type) {
        this.file_type = file_type;
    }

    public Map<String, String> getFile_size() {
        return file_size;
    }

    public void setFile_size(Map<String, String> file_size) {
        this.file_size = file_size;
    }

    public Map<String, String> getFile_time() {
        return file_time;
    }

    public void setFile_time(Map<String, String> file_time) {
        this.file_time = file_time;
    }

    public FileMessage() {

    }
}
