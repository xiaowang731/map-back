package com.example.fs.security.mapper;

import com.example.fs.security.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Adss
 * @date 2024/6/20 下午3:38
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    @Select("SELECT DISTINCT rm.menu_id " +
            "FROM user_role ur " +
            "LEFT JOIN role r ON ur.role_id = r.id " +
            "LEFT JOIN role_menu rm ON ur.role_id = rm.role_id " +
            "WHERE ur.user_id = #{userid} ")
    List<Long> selectByMenuId(@Param("userid") String userId);
}
