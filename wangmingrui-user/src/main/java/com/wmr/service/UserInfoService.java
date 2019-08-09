package com.wmr.service;

import com.github.pagehelper.PageInfo;
import com.wmr.entity.RoleInfo;
import com.wmr.entity.UserInfo;

import java.util.List;

public interface UserInfoService {
    /**
     * 查询  分页  按用户姓名 性别  创建时间区间 查询
     * @param page      //当前页
     * @param pageSize  //每页显示的条数
     * @param userName //用户姓名
     * @param min      //创建时间最小值
     * @param max      //创建时间最大值
     * @param sex      //用户性别
     * @return
     */
    PageInfo<UserInfo> findAllByUserNameBetweenAndCreateTime(int page, int pageSize, String userName, String min, String max,String sex);
    /**
     * 删除user中间表的数据和user表的数据
     */
    int deletaAll(Long id);
    /**
     * 添加用户
     */
    int addUserInfo(UserInfo userInfo);
    /**
     * 修改
     */
    void updateUserInfo(UserInfo userInfo);
    /**
     * 修改中间表
     */
    void updateModdle(Long roleId,Long userId);
    /**
     * 查询角色
     */
    List<RoleInfo> selectRole();
}
