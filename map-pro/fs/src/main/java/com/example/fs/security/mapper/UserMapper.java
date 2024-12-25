package com.example.fs.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.fs.security.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Adss
 * @date 2024/6/20 下午3:38
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


    @Select("SELECT DISTINCT m.perms " +
            "FROM user_role ur " +
            "LEFT JOIN role r ON ur.role_id = r.id " +
            "LEFT JOIN role_menu rm ON ur.role_id = rm.role_id " +
            "LEFT JOIN menu m ON m.id = rm.menu_id " +
            "WHERE ur.user_id = #{userid} " +
            "AND m.type = 3")
    List<String> selectPermsByUserId(@Param("userid") String userId);

    @Update("update user set login_time = #{date} where id = #{id}")
    void updateLoginTime(@Param("id") String s, @Param("date") LocalDateTime date);
}




