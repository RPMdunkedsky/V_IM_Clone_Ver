package com.ruoyi.api.controller;

import com.ruoyi.api.service.VimMessageService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.api.entity.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author z
 */
@RestController
@RequestMapping("/api/sys/messages")
public class MessageController {

    @Resource
    private VimMessageService vimMessageService;

    @Resource
    private ISysUserService iSysUserService;

    @GetMapping
    public AjaxResult list(String chatId, String fromId, String type, Long pageNum, Long pageSize) {
        pageNum = pageNum == null?1:pageNum;
        List<Message> messageVoList = vimMessageService.list(chatId,fromId,type,pageNum,pageSize);
        for (Message message : messageVoList) {
            message.setMine(fromId.equals(String.valueOf(message.getFromId())));
            SysUser sysUser = iSysUserService.selectUserById(Long.parseLong(message.getFromId()));
            message.setAvatar(sysUser.getAvatar());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("messageList", messageVoList);
        map.put("pageNo", pageNum);
        map.put("count", vimMessageService.count(chatId,fromId,type));
        map.put("pageSize", pageSize);
        return AjaxResult.success(map);

    }
}
