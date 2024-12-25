package com.example.fs.security.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.fs.security.mapper.UserRoleMapper;
import com.example.fs.security.pojo.UserRole;
import com.example.fs.security.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * @author Adss
 * @date 2024/6/20 下午3:38
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
