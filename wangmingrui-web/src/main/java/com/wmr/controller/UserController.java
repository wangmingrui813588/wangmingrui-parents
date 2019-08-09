package com.wmr.controller;

import com.alibaba.fastjson.JSON;
import com.wmr.ResponseResult;
import com.wmr.entity.UserInfo;
import com.wmr.jwt.JWTUtils;
import com.wmr.randm.VerifyCodeUtils;
import com.wmr.service.UserService;
import com.wmr.utils.MD5;
import com.wmr.utils.UID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 获取滑动验证码
     */
    @RequestMapping("getCode")
    public ResponseResult getCode(HttpServletRequest request, HttpServletResponse response){
        //生成随机5位字符串
        String code = VerifyCodeUtils.generateVerifyCode(5);
        //调用本类返回验证码
      ResponseResult responseResult =  ResponseResult.getResponseResult();
      responseResult.setResult(code);
      String uidCode = "CODE"+ UID.getUUID16();
        //将随机生成的字符串存入redis
        redisTemplate.opsForValue().set(uidCode,code);
        //设置过期时间
        redisTemplate.expire(uidCode,1, TimeUnit.MINUTES);
        //会写cookie
        Cookie cookie = new Cookie("authcode",uidCode);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        response.addCookie(cookie);
        return responseResult;
    }
    @RequestMapping("test01")
    public String test(){
        return "我爱你";
    }

    @RequestMapping("login")
    public ResponseResult toLogin(@RequestBody Map<String,Object> map) throws LoginException {
        //调用本类实例
        ResponseResult responseResult = ResponseResult.getResponseResult();
        //随机生成验证码
        String code = (String) redisTemplate.opsForValue().get(map.get("codekey").toString());
        //判断cookie回传入的验证码是否是redis中的验证码
        if(code==null || !code.equals(map.get("code").toString())){
            responseResult.setCode(500);
            responseResult.setError("验证码错误,请重新刷新页面登陆");
            return  responseResult;
        }
        //进行用户效验
        if(map!=null&&map.get("loginname")!=null){
            //根据用户名获取用户信息
            UserInfo user = userService.getLogin(map.get("loginname").toString());
            System.out.println(user);
            if(user!=null){
                //获取传入密码，对比密码
                String password = MD5.encryptPassword(map.get("password").toString(), "lcg");
                if(user.getPassword().equals(password)){
                    //将用户信息转存为JSON串
                    String s = JSON.toJSONString(user);
                    //将用户信息使用JWt进行加密，将加密信息作为票据
                    String token = JWTUtils.generateToken(s);
                    //将加密信息存入statuInfo
                    responseResult.setToken(token);
                    //将生成的token存储到redis库
                    redisTemplate.opsForValue().set("USERINFO"+user.getId(),token);
                    //将该用户的数据访问权限信息存入缓存中
                    redisTemplate.opsForHash().putAll("USERDATAAUTH"+user.getId().toString(),user.getAuthmap());
                    //设置过期时间
                    redisTemplate.expire("USERINFO"+user.getId().toString(),600,TimeUnit.SECONDS);
                    responseResult.setResult(user);
                    responseResult.setCode(200);
                    //设置成功信息
                    responseResult.setSuccess("登陆成功！^_^");
                    return responseResult;
                }else {
                    throw new LoginException("用户名或密码错误");
                }
            }else {
                throw new LoginException("用户名或密码错误");
            }
        }else {
            throw new LoginException("用户名或密码错误");
        }
    }
    @RequestMapping("toCheckLogin")
    public ResponseResult loginOut(String loginName){
        //根据用户名获取用户信息
        UserInfo user = userService.getLogin(loginName);
        System.out.println(user);
          if(user!=null){
           redisTemplate.delete("USERDATAAUTH"+user.getId().toString());
           redisTemplate.delete("USERINFO"+user.getId());
        }
        ResponseResult responseResult = ResponseResult.getResponseResult ();
        responseResult.setSuccess ( "ok" );
        return responseResult;
    }


}
