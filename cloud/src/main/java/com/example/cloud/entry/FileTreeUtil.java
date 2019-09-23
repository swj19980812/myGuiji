package com.example.cloud.entry;





import com.example.cloud.service.HadoopService;
import com.example.cloud.service.impl.HadoopServiceImpl;
import org.apache.hadoop.fs.Path;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileTreeUtil {
    @Resource
    HadoopService hadoopService;


    public static FileTree getFileToTree(FileTree tree, String filepath) throws Exception {
       if(filepath == null)
           return null;
        //System.out.println("begin");
        List<FileTree> childrenList = new ArrayList<FileTree>();


            //String[] list2 = file.list();//获取目录下的所有文件
            List<Map<String,Object>> list2 = HadoopServiceImpl.readPath(filepath);
            for (int i = 0; i < list2.size(); i++) {
                //System.out.println("begin2");
                Map<String,Object> pathstatus=null;
                pathstatus=list2.get(i);
                Object fileName1 = pathstatus.get("filePath");
                Path path=new Path((String)fileName1);
                String fileName=path.getName();


                Object file2 = pathstatus.get("fileStatus");
                //System.out.println(filePath);

                if ((boolean)file2==true) {
                    //System.out.println(filepath);
                    FileTree tree1 = new FileTree(fileName, fileName1.toString(), childrenList);
                    //getFileToTree(tree1, fileName1.toString());//递归
                    childrenList.add(getFileToTree(tree1, fileName1.toString()));
                }

            tree.setFilepath(filepath);
            tree.setChild(childrenList);
        }
        return tree;

    }
//    public FileTree getFileToTree1(FileTree tree, String filepath)
////    {
////        return getFileToTree(tree,filepath);
////    }
    public static void treeout(FileTree tree) {
        if (tree == null)
            return;

        List<FileTree> a = tree.getChild();
        System.out.println(a.size());
        for (int i = 0; i < a.size(); i++) {
            System.out.println(a.get(i).getFilepath());
            System.out.println(a.get(i).getFileName());
            treeout(a.get(i));
        }
    }

   public static void main(String[] args)throws FileNotFoundException {

//
    }

}
