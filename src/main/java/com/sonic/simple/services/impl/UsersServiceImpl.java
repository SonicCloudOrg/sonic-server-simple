package com.sonic.simple.services.impl;

import com.sonic.simple.exception.SonicException;
import com.sonic.simple.mapper.UsersMapper;
import com.sonic.simple.models.domain.Users;
import com.sonic.simple.models.http.ChangePwd;
import com.sonic.simple.models.http.RespEnum;
import com.sonic.simple.models.http.RespModel;
import com.sonic.simple.models.http.UserInfo;
import com.sonic.simple.services.UsersService;
import com.sonic.simple.services.impl.base.SonicServiceImpl;
import com.sonic.simple.tools.JWTTokenTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

/**
 * @author ZhouYiXun
 * @des
 * @date 2021/10/13 11:26
 */
@Service
public class UsersServiceImpl extends SonicServiceImpl<UsersMapper, Users> implements UsersService {
    private final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    private JWTTokenTool jwtTokenTool;

    @Autowired
    private UsersMapper usersMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(Users users) throws SonicException {
        try {
            users.setPassword(DigestUtils.md5DigestAsHex(users.getPassword().getBytes()));
            save(users);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SonicException("注册失败！用户名已存在！");
        }
    }

    @Override
    public String login(UserInfo userInfo) {
        Users users = findByUserName(userInfo.getUserName());
        if (users != null && DigestUtils.md5DigestAsHex(userInfo.getPassword().getBytes()).equals(users.getPassword())) {
            String token = jwtTokenTool.getToken(users.getUserName());
            users.setPassword("");
            logger.info("用户：" + userInfo.getUserName() + "登入! token:" + token);
            return token;
        } else {
            return null;
        }
    }

    @Override
    public Users getUserInfo(String token) {
        String name = jwtTokenTool.getUserName(token);
        if (name != null) {
            Users users = findByUserName(name);
            users.setPassword("");
            return users;
        } else {
            return null;
        }
    }

    @Override
    public RespModel<String> resetPwd(String token, ChangePwd changePwd) {
        String name = jwtTokenTool.getUserName(token);
        if (name != null) {
            Users users = findByUserName(name);
            if (users != null) {
                if (DigestUtils.md5DigestAsHex(changePwd.getOldPwd().getBytes()).equals(users.getPassword())) {
                    users.setPassword(DigestUtils.md5DigestAsHex(changePwd.getNewPwd().getBytes()));
                    save(users);
                    return new RespModel(2000, "修改密码成功！");
                } else {
                    return new RespModel(4001, "旧密码错误！");
                }
            } else {
                return new RespModel(RespEnum.UNAUTHORIZED);
            }
        } else {
            return new RespModel(RespEnum.UNAUTHORIZED);
        }
    }

    @Override
    public Users findByUserName(String userName) {
        Assert.hasText(userName, "userName must not be null");
        return lambdaQuery().eq(Users::getUserName, userName).one();
    }
}
