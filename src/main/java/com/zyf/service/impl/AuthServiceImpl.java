package com.zyf.service.impl;

import com.zyf.entity.AuthUser;
import com.zyf.mapper.UserMapper;
import com.zyf.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    UserMapper mapper;

    @Transactional
    @Override
    public void register(String name, String sex, String grade, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        AuthUser user = new AuthUser(0, name, encoder.encode(password), "user");
        if (mapper.registerUser(user) <= 0) {
            throw new RuntimeException("用户信息添加失败");
        }
        if (mapper.addStudentInfo(user.getId(), name, sex, grade) <= 0) {
            throw new RuntimeException("学生信息添加失败");
        }
    }

    @Override
    public AuthUser findUser(HttpSession session) {
        AuthUser user = (AuthUser) session.getAttribute("user");
        if(user==null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            user=mapper.getPasswordByUsername(authentication.getName());
            session.setAttribute("user",user);
        }
        return user;
    }


}
