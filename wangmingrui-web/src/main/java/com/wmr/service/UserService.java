package com.wmr.service;

import com.wmr.dao.MenuDao;
import com.wmr.dao.RoleDao;
import com.wmr.dao.UserDao;
import com.wmr.entity.MenuInfo;
import com.wmr.entity.RoleInfo;
import com.wmr.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private MenuDao menuDao;

    public UserInfo getLogin(String loginName){
        //获取用户信息
        UserInfo user = userDao.findByLoginName(loginName);
        //判断用户是否为空
        if(user != null){
            //根据用户ID获取用户的角色信息
            RoleInfo role = roleDao.forRoleInfoByUserId(user.getId());
            //设置本用户的角色信息
            user.setRoleInfo(role);
            //判断角色，如果不为空，设置本角色的权限信息
            if(role != null){
                //查询用户的权限信息
                List<MenuInfo> menu = menuDao.getFirstMenuInfo(role.getId(),1);
                //递归的查询子菜单权限
                Map<String,String> map = new HashMap<>();
                this.getForMenuInfo(menu,role.getId(),map);
                //设置菜单的子权限
                user.setAuthmap(map);
                user.setListMenuInfo(menu);
            }
        }
        return user;
    }
/**
 * 获取子权限的递归方法
 * @param firstMenuInfo
 * @param roleId
 */
    public void getForMenuInfo(List<MenuInfo> firstMenuInfo,Long roleId,Map<String,String> map ){
        for (MenuInfo m: firstMenuInfo
             ) {
            //定义菜单权限
           int leval = m.getLeval()+1;
            //获取下级的菜单信息
            List<MenuInfo> firstMenuInfos =  menuDao.getFirstMenuInfo(roleId,leval);
            if(firstMenuInfos != null){
                //整理后台的数据访问链接
                if(leval==4){
                    for(MenuInfo menu:firstMenuInfos){
                        map.put(menu.getUrl(),"");
                    }
                }
                //设置查出来的菜单到父级对象中
                m.setMenuInfoList(firstMenuInfos);
                //根据查出来的下级菜单继续查询该菜单包含的子菜单
                getForMenuInfo(firstMenuInfos,roleId,map);
            }else {
                break;
            }
        }
    }
}
