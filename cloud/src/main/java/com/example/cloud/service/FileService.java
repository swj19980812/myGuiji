package com.example.cloud.service;


import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {
    public static final String ROOT = "E:\\cloud\\";
    public  String fileOrDir(String fileName);
    public  String  newFolder(String  folderPath); //新建文件夹
    public  String  newFile(String  filePathAndName,  String  fileContent); //新建文件
    public  String  delFile(String  filePathAndName); //删除文件
    public  String  delFolder(String  folderPath);  //删除文件夹
    public  void  delAllFile(String  path);    //删除文件夹下所有文件
    public  String  copyFile(String  oldPath,  String  newPath) throws IOException;  //复制文件
    public  String  copyFolder(String  oldPath,  String  newPath);  //复制文件夹
    public  String  moveFile(String  oldPath,  String  newPath) throws IOException;// 移动文件
    public  String  moveFolder(String  oldPath,  String  newPath);//移动文件夹
    public  void  copyFile2(String source, String dest);//  复制文件2 不用
    public  String  reName(String oldPath, String newPath); //文件重命名
    public  String finalName(String fileName);

}