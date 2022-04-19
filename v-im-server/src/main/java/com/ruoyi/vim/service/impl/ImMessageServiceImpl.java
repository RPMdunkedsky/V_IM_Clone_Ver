package com.ruoyi.vim.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.vim.mapper.ImMessageMapper;
import com.ruoyi.vim.domain.ImMessage;
import com.ruoyi.vim.service.IImMessageService;

/**
 * 聊天信息Service业务层处理
 * 
 * @author 乐天
 * @since 2022-01-25
 */
@Service
public class ImMessageServiceImpl extends ServiceImpl<ImMessageMapper, ImMessage>  implements IImMessageService 
{

}
