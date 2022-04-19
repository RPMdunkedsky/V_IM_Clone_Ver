package com.ruoyi.api.service;

import com.ruoyi.api.vo.Dept;

import java.util.List;

public interface VimDeptApiService {

    List<Dept> getDepts();

    List<Dept> getDepts(String parentIds);

    Dept get(String deptId);
}
