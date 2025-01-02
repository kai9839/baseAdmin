package com.example.common.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.core.domain.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
} 