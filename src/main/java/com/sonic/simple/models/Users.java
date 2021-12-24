package com.sonic.simple.models;

import com.sonic.simple.models.interfaces.UserLoginType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@ApiModel("用户模型")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"userName"}))
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id", example = "1")
    int id;
    @NotNull
    @ApiModelProperty(value = "用户名称", required = true, example = "ZhouYiXun")
    String userName;
    @NotNull
    @ApiModelProperty(value = "用户密码", required = true, example = "123456")
    String password;
    @Positive
    @ApiModelProperty(value = "角色", required = true, example = "1")
    int role;
    @NotNull
    @ApiModelProperty(value = "用户来源", required = true, example = "local")
    String source = UserLoginType.LOCAL;

    public Users() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", source='" + source + '\'' +
                '}';
    }
}
