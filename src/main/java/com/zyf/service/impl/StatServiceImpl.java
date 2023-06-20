package com.zyf.service.impl;

import com.zyf.entity.GlobalStat;
import com.zyf.mapper.BookMapper;
import com.zyf.mapper.UserMapper;
import com.zyf.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatServiceImpl implements StatService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    BookMapper bookMapper;

    @Override
    public GlobalStat getGlobalStat() {
        return new GlobalStat(
                userMapper.getStudentCount(),
                bookMapper.getBookCount(),
                bookMapper.getBorrowCount());
    }
}
