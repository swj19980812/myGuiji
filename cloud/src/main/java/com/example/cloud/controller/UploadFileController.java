package com.example.cloud.controller;

import com.example.cloud.entry.FileForm;
import com.example.cloud.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

/**
 * Create by tianci
 * 2019/1/10 15:41
 */
@RestController
@RequestMapping("/file")
@ResponseBody
@CrossOrigin
public class UploadFileController {

    @Resource
    UploadFileService uploadFileService;


    @RequestMapping("/open")
    public String open() {

        return "true";
    }

    @RequestMapping("/isUpload")
    public Map<String, Object> isUpload(@Valid FileForm form) {

        return uploadFileService. findByFileMd5(form.getMd5());

    }

    @RequestMapping("/upload")
    public Map<String, Object> upload(@Valid FileForm form,
                                      @RequestParam(value = "data", required = false)MultipartFile multipartFile) {
        Map<String, Object> map = null;

        try {
            map = uploadFileService.realUpload(form, multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
