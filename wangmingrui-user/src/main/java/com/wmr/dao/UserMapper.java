package com.wmr.dao;

import com.wmr.entity.RoleInfo;
import com.wmr.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 查询  分页  按用户姓名 性别  创建时间区间 查询
     * @param userName //用户姓名
     * @param min      //创建时间最小值
     * @param max      //创建时间最大值
     * @param sex      //用户性别
     * @return
     */
    List<UserInfo> findAllByUserNameBetweenAndCreateTime(
            @Param("userName") String userName,@Param("min") String min,@Param("max") String max,@Param("sex") String sex);
    /**
     * 按中间表的userId删除中间表数据
     */
    int deleteUserMiddle(Long userId);
    /**
     * 按照id删除user表的数据
     */
     int deleteUserInfo(Long id);
    /**
     * 添加用户
     */
    void addUserInfo(UserInfo userInfo);
    /**
     * 添加中间表
     */
    void addModdle(Long userId);
    UserInfo selectUserInfoByName(String userName);
    /**
     * 修改
     */
    void updateUserInfo(UserInfo userInfo);
    /**
     * 修改中间表
     */
    void updateModdle(@Param("roleId")Long roleId,@Param("userId")Long userId);
    /**
     * 查询角色
     */
    List<RoleInfo> selectRole();
}
