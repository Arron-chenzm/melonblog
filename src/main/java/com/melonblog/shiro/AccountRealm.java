package com.melonblog.shiro;

import com.melonblog.entity.User;
import com.melonblog.service.UserService;
import com.melonblog.util.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountRealm extends AuthorizingRealm {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    @Override
    //判断token是不是JwtToken
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
        //instanceof 测试对象是不是类的实例
    }

    @Override
    //拿到用户之后获取权限信息
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    //拿到token后进行校验的逻辑
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;
        String userId = jwtUtils.getClaimByToken((String) jwtToken.getCredentials()).getSubject();//获取用户名
        User user = userService.getById(Long.valueOf(userId));//查到对应的user类
        if (user==null){
            throw new UnknownAccountException("账户不存在");
        }
        if (user.getStatus()==-1){
            throw new LockedAccountException("账户已被锁定");
        }
        AccountProfile profile = new AccountProfile();
        BeanUtils.copyProperties(user, profile);
        return new SimpleAuthenticationInfo(profile,jwtToken.getCredentials(),getName());//把用户信息返回shiro

    }
}
