package com.ruoyi.api.controller;

import com.ruoyi.api.exception.VimBaseException;
import com.ruoyi.api.service.VimUserApiService;
import com.ruoyi.api.vo.Group;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.vim.service.IImFriendService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 乐天
 */
@RestController
@RequestMapping("/api/sys/friends")
public class FriendController {

    @Resource
    private VimUserApiService vimUserApiService;


    @GetMapping
    public AjaxResult list(){
        SysUser user = SecurityUtils.getLoginUser().getUser();
        return  AjaxResult.success(vimUserApiService.getFriends(String.valueOf(user.getUserId())));
    }

    @PostMapping(value = "add")
    public AjaxResult add(@RequestBody String friendId){
        try {
            SysUser sysUser = SecurityUtils.getLoginUser().getUser();
            return  AjaxResult.success(vimUserApiService.addFriends(friendId,String.valueOf(sysUser.getUserId())));
        } catch (VimBaseException e) {
            throw new VimBaseException("friend.also.add",null);
        }catch (Exception e) {
            throw new VimBaseException("friend.also.error",null);
        }
    }


    @DeleteMapping(value = "delete")
    public AjaxResult delete(@RequestBody String friendId){
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        return  AjaxResult.success(vimUserApiService.delFriends(friendId,String.valueOf(sysUser.getUserId())));
    }
}
