package com.wmr.entity;

import com.wmr.base.BaseAuditable;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 作者: LCG
 * 日期: 2019/8/4 15:18
 * 描述: 用户信息描述
 */
@Data
@Entity
@Table(name = "base_user")
public class UserInfo extends BaseAuditable implements Serializable {

    @Column(name = "userName")
   private String userName;

    @Column(name = "loginName")
   private String loginName;

    @Column(name = "password")
   private String password;

    @Column(name = "tel")
   private String tel;

    @Column(name = "sex")
   private String sex;

    @Column(name = "parentId")
    private Long parentId;
    @Column(name = "department")
    private String department;
    @Column(name = "imager")
    private String imager;
    @Transient
    String roleName;
    @Transient
    Long bid;
    @Transient
    private List<MenuInfo> listMenuInfo;

    @Transient
    private RoleInfo roleInfo;

    @Transient
    private Map<String,String> authmap;

}
