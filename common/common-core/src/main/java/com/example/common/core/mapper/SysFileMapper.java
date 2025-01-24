package com.example.common.core.mapper;

import com.example.common.core.domain.entity.SysFile;
import com.example.common.core.domain.dto.FileQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysFileMapper {
    /**
     * 插入文件信息
     */
    int insert(SysFile file);
    
    /**
     * 查询文件列表
     */
    List<SysFile> selectFileList(@Param("query") FileQueryDTO query);
    
    /**
     * 查询文件总数
     */
    int selectFileCount(@Param("query") FileQueryDTO query);
    
    /**
     * 根据fileKey查询文件
     */
    SysFile selectByFileKey(@Param("fileKey") String fileKey);
    
    /**
     * 根据fileKey删除文件
     */
    int deleteByFileKey(@Param("fileKey") String fileKey);
} 