package com.v.im.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.v.im.user.entity.ImDept;
import com.v.im.user.mapper.ImDeptMapper;
import com.v.im.user.service.IImDeptService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 乐天
 * @since 2018-10-23
 */
@Service
@Qualifier("imDeptService")
public class ImDeptServiceImpl extends ServiceImpl<ImDeptMapper, ImDept> implements IImDeptService {

    @Override
    public String getDepts(String deptIds) {
        if(StrUtil.isNotBlank(deptIds)){
            QueryWrapper<ImDept> wrapper = new QueryWrapper<>();
            wrapper.in("id",deptIds.split(","));
            List<ImDept> deptList = super.list(wrapper);
            return deptList.stream().map(ImDept::getName).collect(Collectors.joining(","));
        }
        return null;
    }
}
