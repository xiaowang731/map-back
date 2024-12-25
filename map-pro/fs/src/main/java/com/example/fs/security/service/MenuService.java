package com.example.fs.security.service;

import cn.hutool.core.lang.tree.Tree;
import com.example.fs.security.pojo.UserVO;

import java.util.List;


/**
 * @author Adss
 * @date 2024/6/20 下午3:38
 */
public interface MenuService {

    List<Tree<String>> selectMenu();

    UserVO getRouterPerm(String userId);
}
