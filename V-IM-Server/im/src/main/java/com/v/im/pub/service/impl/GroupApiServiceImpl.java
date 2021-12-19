package com.v.im.pub.service.impl;

import com.v.im.pub.service.GroupApiService;
import com.v.im.tio.StartTioRunner;
import com.v.im.user.UserUtils;
import com.v.im.user.entity.ImChatGroup;
import com.v.im.user.service.IImChatGroupService;
import com.v.im.user.service.IImChatGroupUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tio.core.Tio;
import org.tio.server.ServerTioConfig;

import javax.annotation.Resource;

@Service
@Qualifier("groupApiService")
public class GroupApiServiceImpl implements GroupApiService {

    @Resource
    private StartTioRunner startTioRunner;

    @Resource
    @Qualifier(value = "imChatGroupService")
    private IImChatGroupService imChatGroupService;

    @Resource
    @Qualifier("imChatGroupUserService")
    private IImChatGroupUserService imChatGroupUserService;

    /**
     * 创建群组API->绑定自己-》绑定到Tio
     *
     * @param group 群组
     * @return 是否创建成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean create(ImChatGroup group) {
        String userId = UserUtils.getUser().getId();
        group.setMaster(userId);
        group.preInsert();
        imChatGroupService.save(group);
        imChatGroupService.addUsers(group.getId(), userId);

        ServerTioConfig serverTioConfig = startTioRunner.getAppStarter().getWsServerStarter().getServerTioConfig();
        Tio.bindGroup(serverTioConfig, userId, group.getId());
        return false;
    }

    @Override
    public void addUsers(String groupId, String... userIds) {
        imChatGroupService.addUsers(groupId, userIds);
        ServerTioConfig serverTioConfig = startTioRunner.getAppStarter().getWsServerStarter().getServerTioConfig();

        for (String userId : userIds) {
            Tio.bindGroup(serverTioConfig, userId, groupId);
        }
    }

    @Override
    public void removeUsers(String groupId, String... userIds) {
        imChatGroupService.removeUsers(groupId, userIds);
        ServerTioConfig serverTioConfig = startTioRunner.getAppStarter().getWsServerStarter().getServerTioConfig();
        for (String userId : userIds) {
            Tio.unbindGroup(serverTioConfig, userId, groupId);
        }
    }
}
