package com.v.im.pub.service;

import com.v.im.user.entity.ImChatGroup;

/**
 * @author lele
 */
public interface GroupApiService {

    /**
     * 创建一个群组
     *
     * @param group 群组
     * @return 是否创建成功
     */
    boolean create(ImChatGroup group);


    /**
     * 群里添加用户
     *
     * @param groupId 群组
     * @param userIds 用户
     * @return 是否创建成功
     */
    void addUsers(String groupId, String... userIds);

    /**
     * 群里移除用户
     *
     * @param groupId 群组
     * @param userIds 用户
     * @return 是否创建成功
     */
    void removeUsers(String groupId, String... userIds);
}
