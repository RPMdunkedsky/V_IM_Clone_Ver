package com.v.im.pub.controller;

import com.v.im.tio.StartTioRunner;
import com.v.im.user.UserUtils;
import com.v.im.user.entity.ImChatGroup;
import com.v.im.user.service.IImChatGroupService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tio.core.Tio;
import org.tio.server.ServerTioConfig;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/group")
public class GroupApiController {

    @Resource
    private StartTioRunner startTioRunner;

    @Resource
    private IImChatGroupService iImChatGroupService;

    @PostMapping("add")
    public void add(ImChatGroup group) throws Exception {
        ServerTioConfig serverTioConfig = startTioRunner.getAppStarter().getWsServerStarter().getServerTioConfig();
        group.preInsert();
        iImChatGroupService.save(group);
        Tio.unbindGroup(serverTioConfig, UserUtils.getUser().getId(), group.getId());
    }
}
