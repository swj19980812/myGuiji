package com.example.cloud.entry;

import java.util.List;

public class FileTree {

    private String FileName;
    private String Filepath;

    private List<FileTree> child;


    public String getFileName() {
        return FileName;
    }

    public FileTree() {
    }

    public String getFilepath() {
        return Filepath;
    }

    public List<FileTree> getChild() {
        return child;
    }

    public FileTree(String fileName, String filepath, List<FileTree> child) {
        FileName = fileName;
        Filepath = filepath;
        this.child = child;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public void setFilepath(String filepath) {
        Filepath = filepath;
    }

    public void setChild(List<FileTree> child) {
        this.child = child;
    }
}
