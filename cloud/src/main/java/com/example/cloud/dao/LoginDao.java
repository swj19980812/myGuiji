package com.example.cloud.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginDao {
    public String existId(String userName);
    public String user_register(String userName,String passWord,String eMail);
    public String searchEMail(String eMail);
    public String getEMail(String userName);
    public String user_change_password(String userName,String passWord);
    public String existPassWord(String userName);
    public String getPassWord(String userName);
    public String insert_code_request(String userName,String eMail,String code);
    public String update_code_request(String userName,String code);
    public String update_time(String userName);
    public String getCode(String userName);
    public Double isTimeOut(String userName);
}
