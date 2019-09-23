package com.example.cloud.controller;


import com.example.cloud.entry.HadoopUtil;

import org.eclipse.jetty.util.StringUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cloud")
@ResponseBody
@CrossOrigin
public class HadoopController {

////    @RequestMapping("/mkdir")
//    public String mkdir(@RequestParam("path") String path) throws Exception {
//        if (StringUtil.isEmpty(path)) {
//            return "请求参数为空";
//        }
//        // 文件对象
//        FileSystem fs = HadoopUtil.getFileSystem();
//        // 目标路径
//        Path newPath = new Path(path);
//        // 创建空文件夹
//        boolean isOk = fs.mkdirs(newPath);
//        fs.close();
//        if (isOk) {
//            return "sucessful";
//        } else {
//            return "fail";
//        }
//    }



}
