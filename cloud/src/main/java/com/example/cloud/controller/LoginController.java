package com.example.cloud.controller;

import com.example.cloud.entry.LoginInfo;
import com.example.cloud.entry.MailInfo;
import com.example.cloud.entry.UserInfo;
import com.example.cloud.service.LoginService;
import com.example.cloud.service.MailService;
import com.example.cloud.service.impl.LoginServiceImpl;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Random;

@RestController
@RequestMapping("/cloud")
@ResponseBody
@CrossOrigin

public class LoginController {

    @Resource
    LoginService loginService;

    @Resource
    private MailService mailService;

    //用户注册
    @RequestMapping("/user_add")
    public String UserAdd(@RequestBody UserInfo data) throws Exception{
        String userName = data.getUserName();
        String passWord = data.getPassWord();
        String eMail = data.geteMail();
        if( !( "".equals(userName.trim() ) ) ){
            if( !( "".equals(passWord.trim() ) ) ){
                if(!( "".equals(eMail.trim() ) ) ){
                    if( loginService.existId(userName) == true ){
                        String flag = loginService.existEMail(eMail);
                        if( flag == "true" ){
                            loginService.user_register(userName,passWord,eMail);
                            return "true";
                        }
                        else if( flag == "eMailRepeat" ){
                            return "eMailRepeat";
                        }
                        else{
                            return "eMailStyleError";
                        }
                    }
                    else{
                        return "nameRepeat";
                    }
                }
            }
        }
        return "nameRepeat";
    }

    //用户登陆
    @RequestMapping("/user_login")
    public String UserLogin(@RequestBody LoginInfo data) throws Exception{
        String userName = data.getUserName();
        String passWord = data.getPassWord();
        if( !( "".equals(userName.trim() ) ) ){
            if( loginService.existId(userName) == true) {
                return "nameFalse";
            }

            else if( !( "".equals(passWord.trim() ) ) ){
                if( loginService.existPassWord(userName,passWord) == true )
                {
                    loginService.user_login(userName,passWord);
                    return "true";
                }
                else{
                    return "passWordFalse";
                }
            }
        }
        return "nameFalse";
    }
    //修改密码
    @RequestMapping("/user_change_password")
    public String UserChangePassWord(@RequestBody UserInfo data) throws Exception {
        String userName = data.getUserName();
        String passWord = data.getPassWord();
        String code = data.geteMail();
        if (!("".equals(userName.trim()))) {
            if (!("".equals(passWord.trim()))) {
                String flag = loginService.user_change_password(userName, passWord, code);
                return flag;
            }
        }
        return "false";
    }

    //发送邮件
    @RequestMapping("/send_check_code")
    public String SendMail(@RequestParam ("userName") String userName) {
        if (mailService.SendMail(userName) == "true") {
            return "true";
        }
        return "false";
    }

}