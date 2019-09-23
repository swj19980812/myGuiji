package com.example.cloud.controller;

import com.example.cloud.entry.*;
import com.example.cloud.service.FileService;
import com.example.cloud.service.HadoopService;
import com.example.cloud.service.LoginService;
import com.example.cloud.service.impl.HadoopServiceImpl;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static com.example.cloud.entry.HadoopUtil.getFileSystem;

@RestController
@RequestMapping("/cloud")
@ResponseBody
@CrossOrigin
public class FileController {
    @Resource
    FileService fileService;

    //文件删除（文件or文件夹）
    @RequestMapping("/file_delete")
    public String FileDelete(@RequestBody  FileInfo data){
        String fileName = fileService.ROOT + data.getFileName();
        File file = new File(fileName);
        if( fileService.fileOrDir( file.toString() ) == "dir"){
            if( fileService.delFolder(file.toString()) == "true" ){
                return "true";
            }
        }
        else{
            if( fileService.delFile(file.toString()) == "true" ){
                return "true";
            }
        }
        return "false";
    }

    //文件重命名
    @RequestMapping("/file_rename")
    public String FileRename(@RequestBody FileInfo data){
        String fileName = fileService.ROOT + data.getFileName();
        String newFileName = fileService.ROOT + data.getToPath();
        if( fileService.reName(fileName,newFileName) == "true" ){
            return "true";
        }
        return "false";
    }
    //返回文件夹
    @RequestMapping("/dir_show")
    public FileTree dirshow( FileInfo data) throws Exception {
        String fileName = fileService.ROOT + data.getFileName();
        String newFileName = fileService.ROOT + data.getToPath();

        System.out.println("true");
        FileTree fileTree1=new FileTree();
        FileTree fileTree=FileTreeUtil.getFileToTree(fileTree1,"fileName");
        return fileTree;

    }
    //文件复制
    @RequestMapping("/file_copy")
    public String FileCopy(@RequestBody FileInfo data) throws IOException {
        String fileName = fileService.ROOT + data.getFileName();
        String newFileName = fileService.ROOT + data.getToPath();
        String name = fileService.finalName(fileName);
        if( fileService.fileOrDir(fileName.toString()) == "dir"){
            if( fileService.copyFolder(fileName,newFileName + "\\" + name) == "true"){
                return "true";
            }
        }
        else{
            if( fileService.copyFile(fileName,newFileName + "\\" + name) == "true"){
                return "true";
            }
        }
        return "false";
    }

    //文件移动
    @RequestMapping("/file_remove")
    public String FileRemove(@RequestBody FileInfo data) throws IOException {
        String fileName = fileService.ROOT + data.getFileName();
        String newFileName = fileService.ROOT + data.getToPath();
        String name = fileService.finalName(fileName);
        if( fileService.fileOrDir(fileName.toString()) == "dir" ){
            if( fileService.moveFolder(fileName,fileService.ROOT+data.getToPath() + "\\" + name) == "true" ){
                return "true";
            }
        }
        else{
            if( fileService.moveFile(fileName,fileService.ROOT+data.getToPath() + "\\" + name) == "true" ){
                return "true";
            }
        }
        return "false";
    }
    //文件新建
    @RequestMapping("/file_new")
    public String FileNew(@RequestBody FileInfo data){
        String fileName = fileService.ROOT + data.getFileName();
        if( fileService.newFile(fileName,"") == "true" ){
            return "true";
        }
        return "false";
    }

    //文件夹新建
    @RequestMapping("/dir_new")
    public String DirNew(@RequestBody FileInfo data) {
        String fileName = fileService.ROOT + data.getFileName();
        if (fileService.newFolder(fileName) == "true") {
            return "true";
        }
        return "false";
    }

    //小文件上传
    @RequestMapping("/file_small_upload")
    public String FileSmallUpload( @RequestParam("file") MultipartFile file,@RequestParam("path") String path){
        System.out.println(path);
        if( file.isEmpty() ){
            return "false";
        }
        String fileName = file.getOriginalFilename();
        String filePath = FileService.ROOT + path;
        File dest = new File(filePath + "\\" + fileName );
        try{
            file.transferTo(dest);
            return "true";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "false";
    }

    //文件展示
    @RequestMapping("/file_show")
    public FileMessage fileShow(@RequestBody FileInfo fileName) {

       String filepath = fileService.ROOT +fileName.getFileName();
        System.out.println(filepath);
        return HadoopServiceImpl.file_show(filepath);
//
//        FileMessage filemessage = new FileMessage();
//        File dir = new File(filepath);
//        File[] files = dir.listFiles();
//        Map<String, Integer> file_type = new HashMap<>();
//        Map<String, String> file_size = new HashMap<>();
//        Map<String, String> file_time = new HashMap<>();
//
//        if (files != null) {
//            for (int i = 0; i < files.length; i++) {
//                if (files[i].exists()) {
//                    String filepath1 = files[i].getAbsolutePath();
//                    String filename1 = files[i].getName();
//                    long mysize = files[i].length();
//                    float mysize1 = 0;
//                    String mynewsize = null;
//                    long lastModified = files[i].lastModified();
//                    int type = 0;
//                    if (fileService.fileOrDir(filepath1) == "dir") {
//                        type = 1;
//                    }
//                    if((mysize/1024)>1048576)
//                    {
//                        mysize1 = mysize / 1048576;
//                        mysize1 = mysize / 1024;
//                        NumberFormat ddf1 = NumberFormat.getNumberInstance();
//                        ddf1.setMaximumFractionDigits(1);
//                        mynewsize = ddf1.format(mysize1) + "gb";
//
//                    }
//                    else if (mysize > 1048576) {
//                        mysize1 = mysize / 1048576;
//                        NumberFormat ddf1 = NumberFormat.getNumberInstance();
//                        ddf1.setMaximumFractionDigits(1);
//                        mynewsize = ddf1.format(mysize1) + "mb";
//                    }
//                    else if (mysize > 1024){
//                        mysize1 = mysize / 1024;
//                        NumberFormat ddf1 = NumberFormat.getNumberInstance();
//                        ddf1.setMaximumFractionDigits(1);
//                        mynewsize = ddf1.format(mysize1) + "kb";
//                    }
//                    else {
//                        mynewsize = mysize + "b";
//                    }
//                    //System.out.print("数字格式="+s);   //四舍五入   23.55
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");//注意大小写是不一样的，一般返回时间和日期都是以毫秒和字节这些基本的单位
//// dateFormat.format(lastModified);
//                    //System.out.println("大小:" + mysize + " ，" + "修改时间:" + dateFormat.format(lastModified));
//                    String dataString = dateFormat.format(lastModified);
//
//
//                    file_type.put(filename1, type);
//                    file_size.put(filename1, mynewsize);
//                    file_time.put(filename1, dataString);
//                }
//            }
//
//        }
//        filemessage.setFile_type(file_type);
//        filemessage.setFile_size(file_size);
//        filemessage.setFile_time(file_time);
//        return filemessage;

    }

    //文件下载
    @RequestMapping("/file_download")
    public void fileDownload(HttpServletRequest request, HttpServletResponse res) throws Exception {
        //String fileName=fileinfo.getFileName();
        //String toPath=fileinfo.getToPath();
        String fileName=request.getParameter("fileName");
        String filepath=FileService.ROOT+fileName;
        FileSystem fs = getFileSystem();
        Path path=new Path(filepath);
        InputStream inputStream=fs.open(path);

        System.out.println(fileName);
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(inputStream);
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        System.out.println("success");




    }


}
