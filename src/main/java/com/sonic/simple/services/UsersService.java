package com.sonic.simple.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sonic.simple.exception.SonicException;
import com.sonic.simple.models.domain.Users;
import com.sonic.simple.models.http.ChangePwd;
import com.sonic.simple.models.http.RespModel;
import com.sonic.simple.models.http.UserInfo;

public interface UsersService extends IService<Users> {
    void register(Users users) throws SonicException;

    String login(UserInfo userInfo);

    Users getUserInfo(String token);

    RespModel<String> resetPwd(String token, ChangePwd changePwd);

    Users findByUserName(String userName);
}
