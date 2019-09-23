package com.example.cloud.service;

import org.hibernate.validator.constraints.EAN;

import java.util.List;
import java.util.Map;

public interface HadoopService {

    public  String fileOrDir(String fileName) throws Exception;//判断类型
    public  String  newFolder(String  folderPath) throws Exception; //新建文件夹
    public  String  delFile(String  filePathAndName) throws Exception; //删除文件
    public  String  delFolder(String  folderPath) throws Exception;  //删除文件夹
    public  String  reName(String oldPath, String newPath) throws Exception; //文件重命名

}
