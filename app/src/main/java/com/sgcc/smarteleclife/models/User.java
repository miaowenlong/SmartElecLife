package com.sgcc.smarteleclife.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by miao_wenlong on 2017/8/15.
 */
@Entity
public class User {
    @Id
    private Long id;
    private String phoneNum;
    private String pwdSecret;
    private String name;
    private String sex;
    private int areaCode;
    @Generated(hash = 1377107528)
    public User(Long id, String phoneNum, String pwdSecret, String name, String sex,
            int areaCode) {
        this.id = id;
        this.phoneNum = phoneNum;
        this.pwdSecret = pwdSecret;
        this.name = name;
        this.sex = sex;
        this.areaCode = areaCode;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getPhoneNum() {
        return this.phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public String getPwdSecret() {
        return this.pwdSecret;
    }
    public void setPwdSecret(String pwdSecret) {
        this.pwdSecret = pwdSecret;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public int getAreaCode() {
        return this.areaCode;
    }
    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }
    public void setId(Long id) {
        this.id = id;
    }

  
}
