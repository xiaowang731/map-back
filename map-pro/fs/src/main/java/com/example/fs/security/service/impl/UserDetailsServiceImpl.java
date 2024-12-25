package com.example.fs.security.service.impl;

import com.example.fs.common.exception.ParameterException;
import com.example.fs.security.mapper.UserMapper;
import com.example.fs.security.pojo.LoginUser;
import com.example.fs.security.pojo.User;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Adss
 * @date 2024/6/20 下午3:38
 */
@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectOne(
                Wrappers.lambdaQuery(User.class)
                        .select(User::getId,
                                User::getUsername,
                                User::getPassword,
                                User::getStatus,
                                User::getNickname,
                                User::getLoginTime,
                                User::getAvatar
                        )
                        .eq(User::getRemoved, false)
                        .eq(User::getUsername, username)
        );
        if (user == null) {
            throw new ParameterException(String.format("用户名不存在：%s", username));
        }
        List<String> perms = userMapper.selectPermsByUserId(user.getId());

        return new LoginUser(user, perms);
    }
}
