package com.sonic.simple.services;

import com.sonic.simple.exception.SonicException;
import com.sonic.simple.models.http.RespModel;
import com.sonic.simple.models.Users;
import com.sonic.simple.models.http.ChangePwd;
import com.sonic.simple.models.http.UserInfo;

public interface UsersService {
    void register(Users users) throws SonicException;

    String login(UserInfo userInfo);

    Users getUserInfo(String token);

    RespModel resetPwd(String token, ChangePwd changePwd);
}
