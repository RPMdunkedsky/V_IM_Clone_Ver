package com.v.im.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.v.im.user.entity.ImChatGroup;
import com.v.im.user.entity.ImChatGroupUser;
import com.v.im.user.mapper.ImChatGroupMapper;
import com.v.im.user.service.IImChatGroupService;
import com.v.im.user.service.IImChatGroupUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 群 服务实现类
 * </p>
 *
 * @author 乐天
 * @since 2018-10-28
 */
@Service
@Qualifier("imChatGroupService")
public class ImChatGroupServiceImpl extends ServiceImpl<ImChatGroupMapper, ImChatGroup> implements IImChatGroupService {

    @Resource
    @Qualifier("imChatGroupUserService")
    private IImChatGroupUserService imChatGroupUserService;

    /**
     * 把用户添加到群里
     * @param groupId 群id
     * @param userIds 用户Id
     */
    @Override
    public void addUsers(String groupId, String... userIds) {
        for(String userId:userIds){
            ImChatGroupUser imChatGroupUser = new ImChatGroupUser();
            imChatGroupUser.setUserId(userId);
            imChatGroupUser.setChatGroupId(groupId);
            imChatGroupUser.setCreateDate(new Date());
            imChatGroupUserService.save(imChatGroupUser);
        }
    }

    @Override
    public void removeUsers(String groupId, String... userIds) {
        QueryWrapper<ImChatGroupUser> wrapper = new QueryWrapper<>();
        wrapper.eq("group_id",groupId);
        wrapper.in("user_id",userIds);
        imChatGroupUserService.remove(wrapper);
    }
}
