package com.wmr.controller;

import com.github.pagehelper.PageInfo;
import com.wmr.ResponseResult;
import com.wmr.entity.RoleInfo;
import com.wmr.entity.UserInfo;
import com.wmr.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;

    String addr1="";
    /**
     * 上传
     */
    @RequestMapping("upload")
    public void upload(@RequestParam("file")MultipartFile file) throws IOException {
        //图片地址
        String addr = "http://localhost:8089/";
        String filename = file.getOriginalFilename();
        file.transferTo(new File("D:/img/"+filename));
        addr1=addr+filename;
    }
    /**
     * 查询  分页  按用户姓名 性别  创建时间区间 查询
     */
    @RequestMapping("findAllUserInfo")
    PageInfo<UserInfo> findAllUserInfo(@RequestBody Map<String, Object> map ){
        Integer page = Integer.valueOf(map.get("page").toString());
        Integer pageSize = Integer.valueOf(map.get("pageSize").toString());
        String userName = (String) map.get("userName");
        String min = (String) map.get("min");
        String max = (String) map.get("max");
        String sex = (String) map.get("sex");
        //调用userInfoService层的方法
        PageInfo<UserInfo> allByUserNameBetweenAndCreateTime = userInfoService.findAllByUserNameBetweenAndCreateTime(page, pageSize, userName, min, max, sex);
        return allByUserNameBetweenAndCreateTime;
    }
    /**
     * 删除user中间表的数据和user表的数据
     */
    @RequestMapping("deleteUserAll")
    public int deleteUserAll(@RequestBody Map<String,String> map){
      return   userInfoService.deletaAll(Long.valueOf(map.get("id")));
    }
    /**
     * 添加用户
     */
    @RequestMapping("addUserInfo")
    public ResponseResult addUserInfo(@RequestBody UserInfo userInfo){
  ResponseResult responseResult = ResponseResult.getResponseResult();
        userInfo.setImager(addr1);
        userInfoService.addUserInfo(userInfo);
        responseResult.setCode(1);
        return responseResult;
    }
    /**\
     * 修改用户
     */
    @RequestMapping("updateUserInfo")
    public ResponseResult updateUserInfo(@RequestBody UserInfo userInfo){
        userInfoService.updateUserInfo(userInfo);
        ResponseResult responseResult = ResponseResult.getResponseResult();
        responseResult.setCode(1);
        return responseResult;
    }
    /**
     * 修改中间表
     */
    @RequestMapping("updateModdle")
    public ResponseResult updateModdle(@RequestBody Map<String, Object> map){
        Long roleId = Long.valueOf(map.get("roleId").toString());
        Long userId = Long.valueOf(map.get("userId").toString());
        userInfoService.updateModdle(roleId,userId);
        ResponseResult responseResult = ResponseResult.getResponseResult();
        responseResult.setCode(1);
        return responseResult;
    }
    /**
     * 查询角色
     */
    @RequestMapping("selectRole")
    public List<RoleInfo> selectRole(){
        return userInfoService.selectRole();
    }

}
