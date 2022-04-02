package org.cloud.sonic.simple.services;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.cloud.sonic.simple.exception.SonicException;
import org.cloud.sonic.simple.models.domain.Users;
import org.cloud.sonic.simple.models.http.ChangePwd;
import org.cloud.sonic.simple.models.http.RespModel;
import org.cloud.sonic.simple.models.http.UserInfo;

public interface UsersService extends IService<Users> {
    JSONObject getLoginConfig();

    void register(Users users) throws SonicException;

    String login(UserInfo userInfo);

    Users getUserInfo(String token);

    RespModel<String> resetPwd(String token, ChangePwd changePwd);

    Users findByUserName(String userName);
}
