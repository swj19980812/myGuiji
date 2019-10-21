package com.jit.sports.controller;

import com.jit.sports.Dao.RedisDao;
import com.jit.sports.Dao.impl.RedisDaoImpl;
import com.jit.sports.entry.SportInfo;
import com.jit.sports.service.UserService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RequestMapping("/user")
@RestController
@Mapper
public class userController {

    @Resource
    UserService userService;


    //用户登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "userName") String userName,
                       @RequestParam(value = "password") String password) {
        Map<String,String> a = new HashMap<>();
        a.put("sss","qwe");
        RedisDao redisDao = new RedisDaoImpl();
        redisDao.putHashTable("swj",a);

        if(userService.login(userName, password) == null) {
            return "false";
        }
        return "true";


    }

    //用户注册
    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public String reg(@RequestParam(value = "userName") String userName,
                       @RequestParam(value = "password") String password) {
        //检查是否被注册
        if(userService.existUserName(userName) != null) {
            return "false";
        }
        userService.reg(userName, password);
        return "true";
    }

    @RequestMapping("/notOverSport")
    public SportInfo[] notOverSport(@RequestParam(value = "userName") String userName){
        return  userService.selectNotOverSport(userName);
    }

    @RequestMapping("/hello")
    public String test() {
        System.out.println("hello");
        return "hello";
    }
}
