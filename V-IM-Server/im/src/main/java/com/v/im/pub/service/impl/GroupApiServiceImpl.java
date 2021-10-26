package com.v.im.pub.service.impl;

import com.v.im.pub.service.GroupApiService;
import com.v.im.tio.StartTioRunner;
import com.v.im.user.entity.ImChatGroup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Qualifier("groupApiService")
public class GroupApiServiceImpl implements GroupApiService {

    @Resource
    private StartTioRunner startTioRunner;


    @Override
    public boolean add(ImChatGroup group) {
        return false;
    }
}
