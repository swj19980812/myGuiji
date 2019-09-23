package com.example.cloud.service.impl;

import com.example.cloud.dao.LoginDao;
import com.example.cloud.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    LoginDao loginDao;

    @Override
    public boolean existId(String userName) {
        String result = loginDao.existId(userName);
        if( result == null ){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean existPassWord(String userName, String passWord) {
        if( passWord.equals(loginDao.getPassWord(userName)) ){
            return true;
        }
        return false;
    }

    @Override
    public void user_login(String userName, String passWord) {
    }
    @Override
    public void user_register(String userName, String passWord, String eMail) {
        loginDao.user_register(userName,passWord,eMail);
        loginDao.insert_code_request(userName,eMail,"0");
    }

    @Override
    public String user_change_password(String userName, String passWord, String code) {

        if( loginDao.existId(userName) == null){
            return "nameError";
        }
        else{
            String eCode = loginDao.getCode(userName);
            if( eCode.equals(code) == true ){
                Double time = loginDao.isTimeOut(userName);
                if( time / 60 >= 5.0 ){
                    loginDao.user_change_password(userName,passWord);
                    return "true";
                }
                else{
                    return "timeout";
                }
            }
            else{
                return "codeError";
            }
        }
    }

    @Override
    public String existEMail(String eMail) {
        boolean flag;
        if( null == loginDao.searchEMail(eMail)){
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(eMail);
            flag = matcher.matches();
            if( flag == true){
                return "true";
            }
            else{
                return "eMailStyleError";
            }

        }
        else{
            return "eMailRepeat";
        }
    }

}
