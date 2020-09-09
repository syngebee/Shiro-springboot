package com.cyc.shirospringboot.controller;

import com.cyc.shirospringboot.pojo.User;
import com.cyc.shirospringboot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MyController {

    @GetMapping({"/","/index.html","/index"})
    public String index(Model model){
        model.addAttribute("msg","Hello,Shiro");
        return "index";
    }

    @GetMapping("/user/add")
    public String add(){
        return "/user/add";
    }

    @GetMapping("/user/update")
    public String update(){
        return "/user/update";
    }

    @GetMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(String username,String password,Model model){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try {
            subject.login(token);                 //执行登录方法,如果没有异常就说明Ok了
            return "redirect:/index";
        } catch (UnknownAccountException e) {     //用户名不存在
            model.addAttribute("logMsg","用户名不存在");
            return "login";
        }catch (IncorrectCredentialsException e) {     //密码错误
            model.addAttribute("logMsg","密码错误");
            return "login";
        }catch (LockedAccountException e) {     //用户锁定
            model.addAttribute("logMsg","用户已锁定");
            return "login";
        }
    }

    @Autowired
    private UserService userService;

    @GetMapping("/getlist")
    public @ResponseBody List<User> getList(){
        return userService.getList();
    }

    @RequestMapping("/noauth")
    public @ResponseBody String unAuth(){
        return "未经授权不得访问";
    }

/*    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }*/


}
