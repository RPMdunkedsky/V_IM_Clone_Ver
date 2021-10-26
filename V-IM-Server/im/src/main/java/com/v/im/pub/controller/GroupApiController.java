package com.v.im.pub.controller;

import com.v.im.pub.service.GroupApiService;
import com.v.im.user.entity.ImChatGroup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/group")
public class GroupApiController {

    @Resource
    @Qualifier("groupApiService")
    private GroupApiService groupApiService;

    @PostMapping("add")
    public boolean add(ImChatGroup group) {
        return groupApiService.add(group);
    }
}
