package com.ruoyi.api.service;

import com.ruoyi.api.vo.User;
import com.ruoyi.common.core.domain.entity.SysUser;

import java.util.List;

public interface VimUserApiService {

    List<User> getFriends(String userId);

    List<User> getByDept(String deptId);

    List<User> search(String mobile);

    User get(String userId);

    int update(User user);

    boolean addFriends(String friendId,String userId);

    boolean delFriends(String friendId,String userId);
}
