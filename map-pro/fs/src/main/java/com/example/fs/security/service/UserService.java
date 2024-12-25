package com.example.fs.security.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.fs.common.PageVO;
import com.example.fs.security.pojo.*;
import jakarta.annotation.Nullable;

/**
 * @author Adss
 * @date 2024/6/20 下午3:38
 */
public interface UserService extends IService<User> {

    UserVO login(LoginDTO loginDto);

    UserVO selectCurrentUser();

    void updatePerson(UpdatePersonDto dto);

    @Nullable
    User currentUser(Boolean isCache);

    Boolean currentUserIsAdmin();

    void loginOut();

    void add(AddUserDTO dto);

    void delete(DeleteUserDTO dto);

    void update(UpdateUserDTO dto);

    PageVO<UserVO> select(UserPageDTO dto);
}
