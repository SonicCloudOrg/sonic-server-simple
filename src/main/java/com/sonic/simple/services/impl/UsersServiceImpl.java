package com.sonic.simple.services.impl;

import com.sonic.simple.exception.SonicException;
import com.sonic.simple.models.http.RespEnum;
import com.sonic.simple.models.http.RespModel;
import com.sonic.simple.models.interfaces.UserLoginType;
import com.sonic.simple.tools.JWTTokenTool;
import com.sonic.simple.dao.UsersRepository;
import com.sonic.simple.models.Users;
import com.sonic.simple.models.http.ChangePwd;
import com.sonic.simple.models.http.UserInfo;
import com.sonic.simple.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

/**
 * @author ZhouYiXun
 * @des
 * @date 2021/10/13 11:26
 */
@Service
public class UsersServiceImpl implements UsersService {
    private final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private JWTTokenTool jwtTokenTool;

    @Value("${sonic.ldap.enable}")
    private boolean ldapEnable;

    @Value("${sonic.ldap.userId}")
    private String userId;

    @Value("${sonic.ldap.userBaseDN}")
    private String userBaseDN;

    @Autowired
    private LdapTemplate ldapTemplate;

    @Override
    @Transactional(rollbackFor = SonicException.class)
    public void register(Users users) throws SonicException {
        try {
            users.setPassword(DigestUtils.md5DigestAsHex(users.getPassword().getBytes()));
            usersRepository.save(users);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SonicException("注册失败！用户名已存在！");
        }
    }

    @Override
    public String login(UserInfo userInfo) {
        Users users = usersRepository.findByUserName(userInfo.getUserName());
        String token = null;
        if (users == null) {
            if (checkLdapAuthenticate(userInfo, true)) {
                token = jwtTokenTool.getToken(userInfo.getUserName());
            }
        }else if (UserLoginType.LOCAL.equals(users.getSource()) && DigestUtils.md5DigestAsHex(userInfo.getPassword().getBytes()).equals(users.getPassword())) {
            token = jwtTokenTool.getToken(users.getUserName());
            users.setPassword("");
            logger.info("用户：" + userInfo.getUserName() + "登入! token:" + token);
        } else {
            if (checkLdapAuthenticate(userInfo, false)) {
                token = jwtTokenTool.getToken(users.getUserName());
                logger.info("ldap 用户：" + userInfo.getUserName() + "登入! token:" + token);
            }
        }
        return token;
    }

    private boolean checkLdapAuthenticate(UserInfo userInfo, boolean create) {
        if (!ldapEnable) return false;
        String username = userInfo.getUserName();
        String password = userInfo.getPassword();
        logger.info("login check content username {}", username);
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "person")).and(new EqualsFilter(userId, username));
        try {
            boolean authResult = ldapTemplate.authenticate(userBaseDN, filter.toString(), password);
            if (create) {
                usersRepository.save(buildUser(userInfo));
            }
            return authResult;
        } catch (Exception e) {
            logger.error("ldap 登录异常，{}", e);
            return false;
        }
    }

    private Users buildUser(UserInfo userInfo) {
        Users users = new Users();
        users.setUserName(userInfo.getUserName());
        users.setPassword("");
        users.setRole(1);
        users.setSource(UserLoginType.LDAP);
        return users;
    }

    @Override
    public Users getUserInfo(String token) {
        String name = jwtTokenTool.getUserName(token);
        if (name != null) {
            Users users = usersRepository.findByUserName(name);
            users.setPassword("");
            return users;
        } else {
            return null;
        }
    }

    @Override
    public RespModel resetPwd(String token, ChangePwd changePwd) {
        String name = jwtTokenTool.getUserName(token);
        if (name != null) {
            Users users = usersRepository.findByUserName(name);
            if (users != null) {
                if (DigestUtils.md5DigestAsHex(changePwd.getOldPwd().getBytes()).equals(users.getPassword())) {
                    users.setPassword(DigestUtils.md5DigestAsHex(changePwd.getNewPwd().getBytes()));
                    usersRepository.save(users);
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
}
