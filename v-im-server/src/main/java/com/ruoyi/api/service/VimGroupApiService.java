package com.ruoyi.api.service;

import com.ruoyi.api.vo.Group;
import com.ruoyi.api.vo.User;

import java.util.List;

public interface VimGroupApiService {

    List<Group> getGroups(String userId);

    List<User> getUsers(String groupId);

    Group get(String groupId);

    boolean addUsers(String groupId,String[] userIds);

    boolean delUsers(String groupId,String[] userIds);

    int del(String groupId);

    Group save(Group group);

    int update(Group group);
}
