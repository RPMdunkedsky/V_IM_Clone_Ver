package com.v.im.pub.controller;

import com.v.im.pub.service.GroupApiService;
import com.v.im.user.entity.ImChatGroup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 群功能处理
 * @author lele
 */
@RestController
@RequestMapping("/api/group")
public class GroupApiController {

    @Resource
    @Qualifier("groupApiService")
    private GroupApiService groupApiService;

    /**
     * 创建群
     * @param group 群信息
     * @return 结果
     */
    @PostMapping("create")
    public boolean create(ImChatGroup group) {
        return groupApiService.create(group);
    }

    /**
     * 添加群成员
     * @param groupId 群id
     * @param userIds 成员id
     */
    @PostMapping("addUsers")
    public void addUsers(String groupId, String[] userIds) {
        groupApiService.addUsers(groupId, userIds);
    }

    /**
     * 移除群成员
     * @param groupId 群id
     * @param userIds 成员id
     */
    @PostMapping("removeUsers")
    public void removeUsers(String groupId, String[] userIds) {
        groupApiService.removeUsers(groupId, userIds);
    }
}
