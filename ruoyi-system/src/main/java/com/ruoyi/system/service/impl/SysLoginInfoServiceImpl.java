package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.SysLoginInfo;
import com.ruoyi.system.mapper.SysLoginInfoMapper;
import com.ruoyi.system.service.ISysLoginInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统访问日志情况信息 服务层处理
 * 
 * @author ruoyi
 */
@Service
public class SysLoginInfoServiceImpl extends ServiceImpl<SysLoginInfoMapper, SysLoginInfo>  implements ISysLoginInfoService
{

    /**
     * 新增系统登录日志
     * 
     * @param loginInfo 访问日志对象
     */
    @Override
    public void insertLoginInfo(SysLoginInfo loginInfo)
    {
        baseMapper.insertLoginInfo(loginInfo);
    }

    /**
     * 查询系统登录日志集合
     * 
     * @param loginInfo 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<SysLoginInfo> selectLoginInfoList(SysLoginInfo loginInfo)
    {
        return baseMapper.selectLoginInfoList(loginInfo);
    }

    /**
     * 批量删除系统登录日志
     * 
     * @param infoIds 需要删除的登录日志ID
     * @return
     */
    @Override
    public int deleteLoginInfoByIds(Long[] infoIds)
    {
        return baseMapper.deleteLoginInfoByIds(infoIds);
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLoginInfo()
    {
        baseMapper.cleanLoginInfo();
    }
}
