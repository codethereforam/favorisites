package team.soth.favorisites.service.impl;

import team.soth.favorisites.common.annotation.BaseService;
import team.soth.favorisites.common.base.BaseServiceImpl;
import team.soth.favorisites.dao.mapper.UserMapper;
import team.soth.favorisites.dao.model.User;
import team.soth.favorisites.dao.model.UserExample;
import team.soth.favorisites.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UserService实现
* Created by thinkam on 17-10-31.
*/
@Service
@Transactional(rollbackFor = Exception.class)
@BaseService
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User, UserExample> implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserMapper userMapper;

}