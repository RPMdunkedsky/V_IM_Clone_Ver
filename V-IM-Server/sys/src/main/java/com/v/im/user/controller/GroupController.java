package com.v.im.user.controller;

import com.v.im.user.entity.ImChatGroup;
import com.v.im.user.service.IImChatGroupService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/group")
public class GroupController {

    @Resource
    @Qualifier(value = "imChatGroupService")
    private IImChatGroupService imChatGroupService;


    @PostMapping(value = "/get")
    public ImChatGroup get(String id){
        return imChatGroupService.getById(id);
    }
}
