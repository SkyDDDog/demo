package com.lczyfz.demo.sys.vo;

import com.lczyfz.edp.springboot.core.entity.BaseVO;
import com.lczyfz.edp.springboot.core.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.util.Date;


/**
 * 考试管理系统 用户VO
 *
 * @author 天狗
 * @version 2022-10-18
 */
@ApiModel(value = "ExamUserVO", description = "ExamUserVO")
public class ExamUserVO extends BaseVO {

    @ApiModelProperty(value = "用户名",example = "edp")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码",example = "admin")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "用户邮箱",example = "362664609@qq.com")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ExamUserVO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}