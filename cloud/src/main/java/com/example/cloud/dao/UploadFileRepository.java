package com.example.cloud.dao;

import com.example.cloud.entry.UploadFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Mapper
public interface UploadFileRepository {

    HashMap<String,Object> findByFileMd5(String fileMd5);

    String deleteByFileMd5(String fileMd5);
    String save(@Param("fileId") String fileId,@Param("filePath")String filePath,@Param("size")String size,@Param("suffix")String suffix,@Param("name")String name,@Param("md5")String md5,@Param("Creatdate")Date Creatdate,@Param("Updatadate")Date Updatadate,@Param("status") Integer status);
    String updata(@Param("fileMd5")String fileMd5, @Param("upDate") Date upDate, @Param("status")Integer status);
}
