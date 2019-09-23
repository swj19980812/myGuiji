package com.example.cloud.service.impl;

import com.example.cloud.entry.FileMessage;
import com.example.cloud.entry.HadoopUtil;
import com.example.cloud.service.HadoopService;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.cloud.entry.HadoopUtil.getFileSystem;

@Service
public class HadoopServiceImpl implements HadoopService {


    @Override
    public  String fileOrDir(String fileName) throws Exception {
        if (StringUtils.isEmpty(fileName)) {
            return "false";
        }

        // 文件对象
        FileSystem fs = getFileSystem();
        // 目标路径

        Path newPath = new Path(fileName);
        // 创建空文件夹
        boolean isOk = fs.mkdirs(newPath);


        if(fs.getFileStatus(newPath).isDirectory() == true)
        {
            return "dir";
        }
        else
            return "false";

    }
    @Override
    public  String  newFolder(String  folderPath) throws Exception //新建文件夹
    {
        if (StringUtils.isEmpty(folderPath)) {
            return "false";
        }

        // 文件对象
        FileSystem fs = getFileSystem();
        // 目标路径

        Path newPath = new Path(folderPath);
        // 创建空文件夹
        boolean isOk = fs.mkdirs(newPath);

        fs.close();
        if (isOk) {
            return "true";
        } else {
            return "false";
        }
    }

    @Override
    public  String  delFile(String  filePathAndName) throws Exception //删除文件
    {
        FileSystem fs = getFileSystem();
        Path newPath = new Path(filePathAndName);
        boolean isOk = fs.deleteOnExit(newPath);
        fs.close();
        if (isOk) {
            return "true";
        } else {
            return "false";
        }
    }
    @Override
    public  String  delFolder(String  folderPath) throws Exception  //删除文件夹
    {
        FileSystem fs = getFileSystem();

        Path newPath = new Path(folderPath);

        boolean isOk = fs.delete(newPath,true);
        fs.close();
        if (isOk) {
            return "true";
        } else {
            return "false";
        }
    }
    @Override
    public  String  reName(String oldPath, String newPath) throws Exception {
        if (StringUtils.isEmpty(oldPath) || StringUtils.isEmpty(newPath)) {
            return "false";
        }
        FileSystem fs = getFileSystem();
        // 原文件目标路径
        Path oldPath1 = new Path(oldPath);
        // 重命名目标路径
        Path newPath1 = new Path(newPath);
        boolean isOk = fs.rename(oldPath1, newPath1);
        fs.close();
        return "true";

    }

    public static List<Map<String, Object>> readPath(String pathName) throws Exception {
        if (StringUtils.isEmpty(pathName)) {
            return null;
        }

        FileSystem fs = getFileSystem();
        // 目标路径
        Path newPath = new Path(pathName);
        FileStatus[] statusList = fs.listStatus(newPath);
        List<Map<String, Object>> list = new ArrayList<>();
        if (null != statusList && statusList.length > 0) {
            for (FileStatus fileStatus : statusList) {
                Map<String, Object> map = new HashMap<>();
                map.put("filePath", fileStatus.getPath().toString());
                map.put("fileStatus", fileStatus.isDirectory());

                list.add(map);
            }
            return list;
        } else {
            return null;
        }
    }
    public static FileMessage file_show( String fileName) throws Exception {
                FileMessage filemessage = new FileMessage();
        Path newPath = new Path(fileName);
        FileSystem fs = getFileSystem();
        // 目标路径

        FileStatus[] statusList = fs.listStatus(newPath);

        Map<String, Integer> file_type = new HashMap<>();
        Map<String, String> file_size = new HashMap<>();
        Map<String, String> file_time = new HashMap<>();

        if (statusList!= null) {
            for (int i = 0; i < statusList.length; i++) {
                if (statusList[i].isDirectory() || statusList[i].isFile()) {
                    String filepath1 = statusList[i].getPath().toString();
                    String filename1 = statusList[i].getPath().getName();
                    long mysize = statusList[i].getLen();
                    float mysize1 = 0;
                    String mynewsize = null;
                    long lastModified = statusList[i].getModificationTime();
                    int type = 0;
                    if (statusList[i].isDirectory()) {
                        type = 1;
                    }
                    if((mysize/1024)>1048576)
                    {
                        mysize1 = mysize / 1048576;
                        mysize1 = mysize / 1024;
                        NumberFormat ddf1 = NumberFormat.getNumberInstance();
                        ddf1.setMaximumFractionDigits(1);
                        mynewsize = ddf1.format(mysize1) + "gb";

                    }
                    else if (mysize > 1048576) {
                        mysize1 = mysize / 1048576;
                        NumberFormat ddf1 = NumberFormat.getNumberInstance();
                        ddf1.setMaximumFractionDigits(1);
                        mynewsize = ddf1.format(mysize1) + "mb";
                    }
                    else if (mysize > 1024){
                        mysize1 = mysize / 1024;
                        NumberFormat ddf1 = NumberFormat.getNumberInstance();
                        ddf1.setMaximumFractionDigits(1);
                        mynewsize = ddf1.format(mysize1) + "kb";
                    }
                    else {
                        mynewsize = mysize + "b";
                    }
                    //System.out.print("数字格式="+s);   //四舍五入   23.55
                    SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");//注意大小写是不一样的，一般返回时间和日期都是以毫秒和字节这些基本的单位
// dateFormat.format(lastModified);
                    //System.out.println("大小:" + mysize + " ，" + "修改时间:" + dateFormat.format(lastModified));
                    String dataString = dateFormat.format(lastModified);


                    file_type.put(filename1, type);
                    file_size.put(filename1, mynewsize);
                    file_time.put(filename1, dataString);
                }
            }

        }
        filemessage.setFile_type(file_type);
        filemessage.setFile_size(file_size);
        filemessage.setFile_time(file_time);
        return filemessage;


    }

}
