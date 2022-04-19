package com.ruoyi.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.api.exception.VimBaseException;
import com.ruoyi.api.service.VimUserApiService;
import com.ruoyi.api.utils.SysUtils;
import com.ruoyi.api.vo.User;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.vim.domain.ImFriend;
import com.ruoyi.vim.mapper.ImFriendMapper;
import com.ruoyi.vim.service.IImFriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tio.core.Tio;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VimUserApiServiceImpl implements VimUserApiService {

    private static final Logger log = LoggerFactory.getLogger(VimUserApiServiceImpl.class);

    @Resource
    private ISysUserService iSysUserService;

    @Resource
    private ImFriendMapper imFriendMapper;

    @Resource
    private IImFriendService iImFriendService;

    @Override
    public List<User> getFriends(String userId) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        return imFriendMapper.getUserFriends(String.valueOf(user.getUserId()), "0");
    }

    @Override
    public List<User> getByDept(String deptId) {
        SysUser sysUser = new SysUser();
        sysUser.setDeptId(Long.parseLong(deptId));
        return iSysUserService.selectUserList(sysUser).stream().map(this::transform).collect(Collectors.toList());
    }

    @Override
    public List<User> search(String mobile) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        return iSysUserService.list(queryWrapper).stream().map(this::transform).collect(Collectors.toList());
    }

    @Override
    public User get(String userId) {
        SysUser sysUser = iSysUserService.selectUserById(Long.parseLong(userId));
        return new User(String.valueOf(sysUser.getUserId()), sysUser.getNickName(), sysUser.getAvatar(), sysUser.getMobile(), sysUser.getSex(), String.valueOf(sysUser.getDeptId()), sysUser.getEmail());
    }

    @Override
    public int update(User user) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(Long.parseLong(user.getId()));
        sysUser.setAvatar(user.getAvatar());
        sysUser.setNickName(user.getName());
        sysUser.setEmail(user.getEmail());
        sysUser.setSex(user.getSex());
        return iSysUserService.updateUser(sysUser);
    }

    @Override
    public boolean addFriends(String friendId) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        List<String> ids = getFriends(String.valueOf(user.getUserId())).stream().map(n->n.getId()).collect(Collectors.toList());
        if(ids.contains(friendId)){
            throw new VimBaseException("friend.also.add",null);
        }
        ImFriend imFriend = new ImFriend();
        imFriend.setFriendId(Long.parseLong(friendId));
        imFriend.setUserId(user.getUserId());
        imFriend.setState(SysUtils.FRIEND_STATUS_COMMON);
        iImFriendService.save(imFriend);
        return true;
    }

    @Override
    public boolean delFriends(String friendId) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        QueryWrapper<ImFriend> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", user.getUserId());
        wrapper.eq("friend_id", friendId);
        boolean f1 = iImFriendService.remove(wrapper);

        QueryWrapper<ImFriend> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("user_id", friendId);
        wrapper1.eq("friend_id", user.getUserId());
        boolean f2 = iImFriendService.remove(wrapper1);
        return f1 || f2;
    }

    private User transform(SysUser sysUser) {
        return new User(String.valueOf(sysUser.getUserId()), sysUser.getNickName(), sysUser.getAvatar(), sysUser.getMobile(), sysUser.getSex(), String.valueOf(sysUser.getDeptId()), sysUser.getEmail());
    }
}
