package com.ruoyi.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.api.service.VimGroupApiService;
import com.ruoyi.api.utils.SysUtils;
import com.ruoyi.api.vo.Group;
import com.ruoyi.api.vo.User;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.tio.StartTioRunner;
import com.ruoyi.vim.domain.ImGroup;
import com.ruoyi.vim.domain.ImGroupUser;
import com.ruoyi.vim.mapper.ImGroupMapper;
import com.ruoyi.vim.mapper.ImGroupUserMapper;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tio.core.Tio;
import org.tio.server.ServerTioConfig;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author z
 */
@Service
public class VimGroupApiServiceImpl implements VimGroupApiService {

    private static final Logger log = LoggerFactory.getLogger(VimGroupApiServiceImpl.class);

    @Resource
    private ImGroupMapper imGroupMapper;

    @Resource
    private ImGroupUserMapper imGroupUserMapper;

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public List<Group> getGroups(String userId) {
        return imGroupMapper.getUserGroups(userId).stream().map(this::transform).collect(Collectors.toList());
    }

    @Override
    public List<User> getUsers(String groupId) {
        return imGroupMapper.getGroupUsers(groupId).stream().map(this::transform).collect(Collectors.toList());
    }

    @Override
    public Group get(String groupId) {
        return transform(imGroupMapper.selectById(groupId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUsers(String groupId, String[] userIds) {
        try {
            StartTioRunner startTioRunner = applicationContext.getBean(StartTioRunner.class);
            ServerTioConfig serverTioConfig = startTioRunner.getAppStarter().getWsServerStarter().getServerTioConfig();
            for (String userId : userIds) {
                ImGroupUser imGroupUser = new ImGroupUser();
                imGroupUser.setUserId(userId);
                imGroupUser.setGroupId(groupId);
                imGroupUser.setState("0");
                imGroupUserMapper.insert(imGroupUser);
                Tio.bindGroup(serverTioConfig,userId,groupId);
            }
        } catch (Exception e) {
            log.error("添加群用户失败：", e);
            return false;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delUsers(String groupId, String[] userIds) {
        try {
            StartTioRunner startTioRunner = applicationContext.getBean(StartTioRunner.class);
            ServerTioConfig serverTioConfig = startTioRunner.getAppStarter().getWsServerStarter().getServerTioConfig();
            for (String userId : userIds) {
                QueryWrapper<ImGroupUser> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("user_id",userId);
                queryWrapper.eq("group_id",groupId);
                imGroupUserMapper.delete(queryWrapper);
                Tio.unbindGroup(serverTioConfig,userId,groupId);
            }
        } catch (Exception e) {
            log.error("删除群用户失败：", e);
            return false;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int del(String groupId) {
        this.delUsers(groupId, getUsers(groupId).stream().map(User::getId).toArray(String[]::new));
        return imGroupMapper.deleteById(groupId);
    }

    private Group transform(ImGroup imGroup) {
        return new Group(imGroup.getId(), imGroup.getName(), imGroup.getAvatar(), String.valueOf(imGroup.getMaster()),imGroup.getNeedCheck());
    }

    private User transform(SysUser sysUser) {
        return new User(String.valueOf(sysUser.getUserId()), sysUser.getNickName(), sysUser.getAvatar(), sysUser.getMobile(), sysUser.getSex(),String.valueOf(sysUser.getDeptId()),sysUser.getEmail());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Group save(Group group) {
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        ImGroup imGroup = new ImGroup();
        imGroup.setMaster(sysUser.getUserId());
        imGroup.setName(group.getName());
        imGroup.setAvatar(group.getAvatar());
        imGroup.setDelFlag(SysUtils.DEL_NO);
        imGroup.setNeedCheck(group.getNeedCheck());
        imGroup.preInsert();
        imGroupMapper.insert(imGroup);
        String[] userIds = {String.valueOf(sysUser.getUserId())};
        this.addUsers(imGroup.getId(),userIds);
        group.setId(imGroup.getId());
        return transform(imGroup);
    }

    @Override
    @Transactional
    public int update(Group group) {
        ImGroup imGroup = imGroupMapper.selectById(group.getId());
        imGroup.setName(group.getName());
        imGroup.setAvatar(group.getAvatar());
        imGroup.setNeedCheck(group.getNeedCheck());
        imGroup.setDelFlag(SysUtils.DEL_NO);
        imGroup.preUpdate();
        return imGroupMapper.updateById(imGroup);
    }
}
