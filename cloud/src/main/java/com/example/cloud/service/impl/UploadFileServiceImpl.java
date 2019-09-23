package com.example.cloud.service.impl;

import com.example.cloud.dao.UploadFileRepository;
import com.example.cloud.entry.*;
import com.example.cloud.service.UploadFileService;
import io.netty.util.Constant;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    @Resource
    UploadFileRepository uploadFileRepository;

    @Override
    public Map<String, Object> findByFileMd5(String md5) {
       HashMap<String,Object> map1 = uploadFileRepository.findByFileMd5(md5);
        UploadFile uploadFile = new UploadFile();
       if( map1 != null) {

           uploadFile.setFileStatus((Integer) map1.get("fileStatus"));
           uploadFile.setFilePath((String) map1.get("fileP ath"));
           uploadFile.setFileId((String) map1.get("fileId"));
       }
        Map<String, Object> map = null;
        if (map1 == null) {
            //没有上传过文件
            map = new HashMap<>();
            map.put("flag", 0);
            map.put("fileId", KeyUtil.genUniqueKey());
            map.put("date", simpleDateFormat.format(new Date()));
        } else {
            //上传过文件，判断文件现在还存在不存在
            File file = new File(uploadFile.getFilePath());

            if (file.exists()) {
                if (uploadFile.getFileStatus() == 1) {
                    //文件只上传了一部分
                    map = new HashMap<>();
                    map.put("flag", 1);
                    map.put("fileId", uploadFile.getFileId());
                    map.put("date", simpleDateFormat.format(new Date()));
                } else if (uploadFile.getFileStatus() == 2) {
                    //文件早已上传完整
                    map = new HashMap<>();
                    map.put("flag" , 2);
                }
            } else {

                map = new HashMap<>();
                map.put("flag", 0);
                map.put("fileId", uploadFile.getFileId());
                map.put("date", simpleDateFormat.format(new Date()));
            }
        }


//        Map<String, Object> map = null;
//        map = new HashMap<>();
//        map.put("flag", 0);
//        map.put("fileId", KeyUtil.genUniqueKey());
//        map.put("date", simpleDateFormat.format(new Date()));
        return map;


    }


    @Override
    public Map<String, Object> realUpload(FileForm form, MultipartFile multipartFile) throws Exception {
        String action = form.getAction();
        String fileId = form.getUuid();
        Integer index = Integer.valueOf(form.getIndex());
        String partMd5 = form.getPartMd5();
        String md5 = form.getMd5();
        Integer total = Integer.valueOf(form.getTotal());
        String fileName = form.getName();
        String size = form.getSize();
        String suffix = NameUtil.getExtensionName(fileName);
        Date data1=null;
        Date data2=null;
        //System.out.println(index+"+"+total);
        String saveDirectory = "E:\\test\\" + File.separator + fileId;
        String filePath = saveDirectory + File.separator + fileId + "." + suffix;
        //验证路径是否存在，不存在则创建目录
        File path = new File(saveDirectory);
        if (!path.exists()) {
            path.mkdirs();
        }
        //文件分片位置
        File file = new File(saveDirectory, fileId + "_" + index);

        //根据action不同执行不同操作. check:校验分片是否上传过; upload:直接上传分片
        Map<String, Object> map = null;
        if ("check".equals(action)) {
            String md5Str = FileMd5Util.getFileMD5(file);
            if (md5Str != null && md5Str.length() == 31) {
                System.out.println("check length =" + partMd5.length() + " md5Str length" + md5Str.length() + "   " + partMd5 + " " + md5Str);
                md5Str = "0" + md5Str;
                System.out.println("check");
            }
            if (md5Str != null && md5Str.equals(partMd5)) {
                //分片已上传过
                System.out.println("duandian");
                map = new HashMap<>();
                map.put("flag", "1");
                map.put("fileId", fileId);
                if(index != total)
                    return map;
            } else {
                //分片未上传
                map = new HashMap<>();
                map.put("flag", "0");
                map.put("fileId", fileId);
                return map;
            }
        } else if("upload".equals(action)) {
            //分片上传过程中出错,有残余时需删除分块后,重新上传
            if (file.exists()) {
                file.delete();
            }

            //System.out.println("error");
            multipartFile.transferTo(new File(saveDirectory, fileId + "_" + index));
            map = new HashMap<>();
            map.put("flag", "1");
            map.put("fileId", fileId);
            if(index.equals(total)==false && index.equals(1)==false)
                return map;
        }

        if (path.isDirectory()) {
            File[] fileArray = path.listFiles();
            if (fileArray != null) {
                if (fileArray.length == total) {
                    //分块全部上传完毕,合并
                    System.out.println("hebin");
                    File newFile = new File(saveDirectory, fileId + "." + suffix);
                    FileOutputStream outputStream = new FileOutputStream(newFile, true);//文件追加写入
                    byte[] byt = new byte[10 * 1024 * 1024];
                    int len;
                    FileInputStream temp = null;//分片文件
                    for (int i = 0; i < total; i++) {
                        int j = i + 1;
                        temp = new FileInputStream(new File(saveDirectory, fileId + "_" + j));
                        while ((len = temp.read(byt)) != -1) {
                            outputStream.write(byt, 0, len);
                        }
                    }
                    //关闭流
                    temp.close();
                    outputStream.close();
                    //修改FileRes记录为上传成功
                    UploadFile uploadFile = new UploadFile();
                    uploadFile.setFileId(fileId);
                    uploadFile.setFileStatus(2);
                    uploadFile.setFileName(fileName);
                    uploadFile.setFileMd5(md5);
                    uploadFile.setFileSuffix(suffix);
                    uploadFile.setFilePath(filePath);
                    uploadFile.setFileSize(size);
                    data2=new Date();

                    uploadFileRepository.updata(md5,data1,2);

                    map=new HashMap<>();
                    map.put("fileId", fileId);
                    map.put("flag", "2");

                    return map;
                }
                else if(index == 1) {
                    System.out.println("diyici");
                    //文件第一个分传时记录到数据库片上
                    UploadFile uploadFile = new UploadFile();
                    uploadFile.setFileMd5(md5);
                    String name = NameUtil.getFileNameNoEx(fileName);
                    if (name.length() > 32) {
                        name = name.substring(0, 32);
                    }
                    uploadFile.setFileName(name);
                    uploadFile.setFileSuffix(suffix);
                    uploadFile.setFileId(fileId);
                    uploadFile.setFilePath(filePath);
                    uploadFile.setFileSize(size);
                    uploadFile.setFileStatus(1);
                    data1=new Date();
                    System.out.println(data1);
                    uploadFileRepository.save(fileId,filePath,size,suffix,name,md5,data2,data1,1);
                }
            }
        }
        return map;
    }
}
