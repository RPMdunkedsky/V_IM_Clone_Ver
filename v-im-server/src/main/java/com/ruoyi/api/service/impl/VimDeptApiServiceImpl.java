package com.ruoyi.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.api.service.VimDeptApiService;
import com.ruoyi.api.vo.Dept;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysDeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author z
 */
@Service
public class VimDeptApiServiceImpl implements VimDeptApiService {

    @Resource
    private ISysDeptService iSysDeptService;

    @Override
    public List<Dept> getDepts() {
        return iSysDeptService.list().stream().map(this::transform).collect(Collectors.toList());
    }

    @Override
    public List<Dept> getDepts(String deptIds) {
        if(StringUtils.isNotBlank(deptIds)){
            QueryWrapper<SysDept> wrapper = new QueryWrapper<>();
            wrapper.in("dept_id",deptIds.split(","));
            List<SysDept> sysDepts = iSysDeptService.list(wrapper);
            return sysDepts.stream().map(this::transform).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public Dept get(String deptId) {
        return transform(iSysDeptService.selectDeptById(Long.parseLong(deptId)));
    }

    private Dept transform(SysDept dept){
        return new Dept(String.valueOf(dept.getDeptId()), String.valueOf(dept.getParentId()), dept.getAncestors(), dept.getDeptName(), dept.getOrderNum());
    }
}
