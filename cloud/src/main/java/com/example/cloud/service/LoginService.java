package com.example.cloud.service;

public interface LoginService {
    //判断用户是否存在
    public boolean existId(String userName);
    //用户注册
    public void user_register(String userName,String passWord,String eMail);
    //用户登陆
    public void user_login(String userName,String passWord);
    //更改密码
    public String user_change_password(String userName,String passWord,String eMail);
    //判断密码是否正确
    public boolean existPassWord(String userName,String passWord);
    //判断邮箱是否正确
    public String existEMail(String eMail);

}
