package com.lczyfz.demo.sys.entity;

import com.lczyfz.edp.springboot.core.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.NotBlank;;

/**
 * 考试题目Entity
 *
 * @author 天狗
 * @version 2022-10-18
 */
@ApiModel(value = "ExamUser", description = "ExamUser")
public class ExamUser extends DataEntity<ExamUser> {

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "用户角色")
    @NotBlank(message = "用户角色不能为空")
    private String role;

    @ApiModelProperty(value = "用户邮箱")
    @NotBlank(message = "用户邮箱不能为空")
    private String email;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ExamUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", remarks='" + remarks + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createDate=" + createDate +
                ", updateBy='" + updateBy + '\'' +
                ", updateDate=" + updateDate +
                ", delFlag='" + delFlag + '\'' +
                ", officeId='" + officeId + '\'' +
                ", sqlMap=" + sqlMap +
                ", id='" + id + '\'' +
                ", isNewRecord=" + isNewRecord +
                '}';
    }
}