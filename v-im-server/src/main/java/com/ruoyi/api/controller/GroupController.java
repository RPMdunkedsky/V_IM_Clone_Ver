package com.ruoyi.api.controller;

import com.ruoyi.api.service.VimGroupApiService;
import com.ruoyi.api.vo.Group;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/sys/groups")
public class GroupController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Resource
    private VimGroupApiService vimGroupApiService;

    /**
     * 新建群
     *
     * @param group 群
     * @return boolean
     */
    @PostMapping("")
    public AjaxResult save(@RequestBody Group group) {
        return AjaxResult.success(vimGroupApiService.save(group));
    }

    /**
     * 更新群
     *
     * @param group 群
     * @return boolean
     */
    @PatchMapping(value = "{id}")
    public AjaxResult update(@PathVariable String id, @RequestBody Group group) {
        group.setId(id);
        return toAjax(vimGroupApiService.update(group));
    }

    /**
     * 获取群
     *
     * @param id 群id
     * @return boolean
     */
    @GetMapping(value = "{id}")
    public AjaxResult get(@PathVariable("id") String id) {
        return AjaxResult.success(vimGroupApiService.get(id));
    }

    /**
     * 查询当前用户所在的群
     *
     * @return List
     */
    @GetMapping
    public AjaxResult list() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        return AjaxResult.success(vimGroupApiService.getGroups(String.valueOf(user.getUserId())));
    }


    /**
     * 查询当前群的所有的用户
     *
     * @return List
     */
    @GetMapping("{id}/users")
    public AjaxResult users(@PathVariable("id") String id) {
        return AjaxResult.success(vimGroupApiService.getUsers(id));
    }

    /**
     * 添加群成员
     *
     * @return List
     */
    @PostMapping("{id}/users")
    public AjaxResult addUsers(@PathVariable("id") String id,@RequestBody String[] userId) {
        try {
            vimGroupApiService.addUsers(id, userId);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * 添加群成员
     *
     * @return List
     */
    @DeleteMapping("{id}/users/{userId}")
    public AjaxResult deleteUser(@PathVariable("id") String id, @PathVariable("userId") String userId) {
        String[] ids = {userId};
        return toAjax(vimGroupApiService.delUsers(id, ids));
    }


    /**
     * 删一个群,只有群主能删除
     *
     * @param id 群id
     * @return boolean
     */
    @DeleteMapping(value = "{id}")
    public AjaxResult delete(@PathVariable("id") String id) {
        return  toAjax(vimGroupApiService.del(id));
    }
}
