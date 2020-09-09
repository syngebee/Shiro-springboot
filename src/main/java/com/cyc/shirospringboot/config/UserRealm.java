package com.cyc.shirospringboot.config;

import com.cyc.shirospringboot.pojo.User;
import com.cyc.shirospringboot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //授权,授予权限
        System.out.println("授权执行");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        info.addStringPermission("user:add");

        //拿到当前用户,这个principal是认证时传递进来的，可以拿到
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();

        //perms属性暂时不考虑多对多关系，直接把权限设置上
        String perms = user.getPerms();
        info.addStringPermission(perms);
        return info;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //认证
        System.out.println("认证执行");

        //转换token为UsernamePasswordToken
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;

        //从数据库中取用户名密码
        //用户名认证
        User user = userService.getUserByUserName(usernamePasswordToken.getUsername());
        if (user==null){
            return null;
        }

        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        session.setAttribute("loginUser",user);

        //密码认证Shiro做 , 把user对象传递过来，让授权可以用
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
