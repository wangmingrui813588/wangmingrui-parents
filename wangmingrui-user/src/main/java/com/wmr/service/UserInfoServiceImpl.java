package com.wmr.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wmr.dao.UserMapper;
import com.wmr.entity.RoleInfo;
import com.wmr.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService{
    @Resource
    UserMapper userMapper;

    /**
     * 查询  分页  按用户姓名 性别  创建时间区间 查询
     * @param page      //当前页
     * @param pageSize  //每页显示的条数
     * @param userName //用户姓名
     * @param min      //创建时间最小值
     * @param max      //创建时间最大值
     * @param      //用户性别
     * @return
     */
    @Override
    public PageInfo<UserInfo> findAllByUserNameBetweenAndCreateTime(int page, int pageSize, String userName, String min, String max,String sex) {
        //调用PageHelper插件
        PageHelper.startPage(page,pageSize);
        //调用dao层查询  分页  按用户姓名 性别  创建时间区间查询 方法
        List<UserInfo> allByUserNameBetweenAndCreateTime = userMapper.findAllByUserNameBetweenAndCreateTime(userName, min, max,sex);
        return new PageInfo<>(allByUserNameBetweenAndCreateTime);
    }
    /**
     * 删除user中间表的数据和user表的数据
     */
    @Override
    public int deletaAll(Long id) {
        int i = userMapper.deleteUserMiddle(id);
        int i1 = userMapper.deleteUserInfo(id);
        if(i>0 || i1>0){
            return 1;
        }
        return 0;
    }
    /**
     * 添加用户
     */
    @Override
    public int addUserInfo(UserInfo userInfo) {
        System.out.println(userInfo+"======我是userInfo=========");
        userMapper.addUserInfo(userInfo);
        UserInfo info = userMapper.selectUserInfoByName(userInfo.getUserName());

        userMapper.addModdle(info.getId());
        return 1;
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        userMapper.updateUserInfo(userInfo);
    }

    @Override
    public void updateModdle(Long roleId, Long userId) {
        userMapper.updateModdle(roleId,userId);
    }

    @Override
    public List<RoleInfo> selectRole() {
        return userMapper.selectRole();
    }


}
