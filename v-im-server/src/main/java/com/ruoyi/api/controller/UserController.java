package com.ruoyi.api.controller;

import com.ruoyi.api.service.VimUserApiService;
import com.ruoyi.api.vo.User;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/sys/users")
public class UserController {

    @Resource
    private VimUserApiService vimUserApiService;

    @GetMapping("{userId}")
    public AjaxResult get(@PathVariable String userId) {
        return AjaxResult.success(vimUserApiService.get(userId));
    }

    @GetMapping("my")
    public AjaxResult my() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        return AjaxResult.success(vimUserApiService.get(String.valueOf(user.getUserId())));
    }

    @GetMapping("search")
    public AjaxResult search(String mobile) {
        return AjaxResult.success(vimUserApiService.search(mobile));
    }

    @PutMapping("{userId}")
    public AjaxResult update(@PathVariable String userId, @RequestBody User user) {
        user.setId(userId);
        return AjaxResult.success(vimUserApiService.update(user));
    }


}
