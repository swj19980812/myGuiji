package com.example.cloud.service.impl;

import com.example.cloud.service.FileService;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String fileOrDir(String fileName) {
        File file = new File(fileName);
        if(file.isDirectory() == true){
            return "dir";
        }
        else{
            return "file";
        }
    }

    @Override
    public String newFolder(String folderPath) {
        try  {
            String  filePath  =  folderPath;
            filePath  =  filePath.toString();
            java.io.File  myFilePath  =  new  java.io.File(filePath);
            if  (!myFilePath.exists())  {
                if( myFilePath.mkdir() == true ){
                    return "true";
                }
            }
        }
        catch  (Exception  e)  {
            e.printStackTrace();
            return "false";
        }
        return "false";
    }

    @Override
    public String newFile(String filePathAndName, String fileContent) {
        try  {
            String  filePath  =  filePathAndName;
            filePath  =  filePath.toString();  //取的路径及文件名
            File myFilePath  =  new  File(filePath);
            /**如果文件不存在就建一个新文件*/
            if  (!myFilePath.exists())  {
                if( myFilePath.createNewFile() == true ){
                    return "true";
                }
            }
            FileWriter resultFile  =  new  FileWriter(myFilePath);  //用来写入字符文件的便捷类, 在给出 File 对象的情况下构造一个 FileWriter 对象
            PrintWriter myFile  =  new  PrintWriter(resultFile);  //向文本输出流打印对象的格式化表示形式,使用指定文件创建不具有自动行刷新的新 PrintWriter。
            String  strContent  =  fileContent;
            myFile.println(strContent);
            resultFile.close();

        }
        catch  (Exception  e)  {
            e.printStackTrace();
            return "false";

        }
        return "false";
    }

    @Override
    public String delFile(String filePathAndName) {
        try  {
            String  filePath  =  filePathAndName;
            filePath  =  filePath.toString();
            java.io.File  myDelFile  =  new  java.io.File(filePath);
            if( true == myDelFile.delete() ){
                return "true";
            }

        }
        catch  (Exception  e)  {
            e.printStackTrace();
            return "false";
        }
        return "false";
    }

    @Override
    public String delFolder(String folderPath) {
        try  {
            delAllFile(folderPath);  //删除完里面所有内容
            String  filePath  =  folderPath;
            filePath  =  filePath.toString();
            java.io.File  myFilePath  =  new  java.io.File(filePath);
            if( myFilePath.delete() == true ){ //删除空文件夹
                return "true";
            }

        }
        catch  (Exception  e)  {
            e.printStackTrace();
            return "false";
        }
        return "false";
    }

    @Override
    public void delAllFile(String path) {
        File  file  =  new  File(path);
        if  (!file.exists())  {
            return;
        }
        if  (!file.isDirectory())  {
            return;
        }
        String[]  tempList  =  file.list();
        File  temp  =  null;
        for  (int  i  =  0;  i  <  tempList.length;  i++)  {
            if  (path.endsWith(File.separator))  {
                temp  =  new  File(path  +  tempList[i]);
            }
            else  {
                temp  =  new  File(path  +  File.separator  +  tempList[i]);
            }
            if  (temp.isFile())  {
                temp.delete();
            }
            if  (temp.isDirectory())  {
                delAllFile(path+"/"+  tempList[i]);//先删除文件夹里面的文件
                delFolder(path+"/"+  tempList[i]);//再删除空文件夹
            }
        }

    }

    @Override
    public String copyFile(String oldPath, String newPath){

        try  {
//           int  bytesum  =  0;
            int  byteread  =  0;
            File  oldfile  =  new  File(oldPath);

            if  (oldfile.exists())  {  //文件存在时
                byte[]  buffer  =  new  byte[1444];
                InputStream inStream  =  new FileInputStream(oldPath);  //读入原文件
                FileOutputStream  fs  =  new  FileOutputStream(newPath);
//               int  length;
                while  (  (byteread  =  inStream.read(buffer))  !=  -1)  {
//                   bytesum  +=  byteread;  //字节数  文件大小
//                   System.out.println(bytesum);
                     fs.write(buffer,  0,  byteread);
                }
                inStream.close();
                fs.close();
            }

        }
        catch  (Exception  e)  {
            e.printStackTrace();
            return "false";

        }
        return "true";
    }

    @Override
    public String copyFolder(String oldPath, String newPath) {
        try  {
            (new  File(newPath)).mkdirs();  //如果文件夹不存在  则建立新文件夹
            File  a=new  File(oldPath);
            String[]  file=a.list();
            File  temp=null;
            for  (int  i  =  0;  i  <  file.length;  i++)  {
                if(oldPath.endsWith(File.separator)){
                    temp=new  File(oldPath+file[i]);
                }
                else{
                    temp=new  File(oldPath+File.separator+file[i]);
                }

                if(temp.isFile()){
                    FileInputStream input  =  new  FileInputStream(temp);
                    FileOutputStream output  =  new  FileOutputStream(newPath  +  "/"  +
                            (temp.getName()).toString());
                    byte[]  b  =  new  byte[1024  *  5];
                    int  len;
                    while  (  (len  =  input.read(b))  !=  -1)  {
                        output.write(b,  0,  len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if(temp.isDirectory()){//如果是子文件夹
                    copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);
                }
            }
        }
        catch  (Exception  e)  {
            e.printStackTrace();
            return "false";

        }
        return "true";
    }

    @Override
    public String moveFile(String oldPath, String newPath) {
        if( copyFile(oldPath,newPath) == "true" )
        {
            if( delFile(oldPath) == "true" )
            {
                return "true";
            }
        }
        return "false";

    }

    @Override
    public String moveFolder(String oldPath, String newPath) {
        if( copyFolder(oldPath,newPath) == "true" )
        {
            if( delFolder(oldPath) == "true" )
            {
                return "true";
            }
        }
        return "false";
    }

    @Override
    public void copyFile2(String source, String dest) {
        try {
            File in = new File(source);
            File out = new File(dest);
            FileInputStream inFile = new FileInputStream(in);
            FileOutputStream outFile = new FileOutputStream(out);
            byte[] buffer = new byte[10240];
            int i = 0;
            while ((i = inFile.read(buffer)) != -1) {
                outFile.write(buffer, 0, i);
            }//end while
            inFile.close();
            outFile.close();
        }//end try
        catch (Exception e) {

        }//end catch
    }//end copyFile

    @Override
    public String reName(String oldPath, String newPath) {
        System.out.println(oldPath + "\n" + newPath);
        if( new File(oldPath).renameTo(new File(newPath)) == true ){
            return "true";
        }
        return "false";
    }

    @Override
    public String finalName(String fileName) {
        File tempFile =new File( fileName.trim());
        String file = tempFile.getName();
        System.out.println(file);
        return file;
    }
}
