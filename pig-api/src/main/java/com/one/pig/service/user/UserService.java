package com.one.pig.service.user;

import com.one.pig.system.dao.UserMapper;
import com.one.pig.system.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserMapper userMapper;

    public void test() {
        List<User> userList=userMapper.selectAll();
        System.out.println("aaa");
    }
}
