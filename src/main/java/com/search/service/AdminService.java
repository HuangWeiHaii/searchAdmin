package com.search.service;

import com.search.bean.Admin;
import com.search.dao.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminService {
    
    @Autowired
    AdminMapper adminMapper;

    public boolean login(String username, String password) {
        Admin admin = adminMapper.selectByPrimaryKey(username);
        System.out.println(username+","+password);
        if (admin != null) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password))
                return true;
        }
        return false;

    }
}