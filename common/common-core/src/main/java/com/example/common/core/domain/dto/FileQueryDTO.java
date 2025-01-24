package com.example.common.core.domain.dto;

import lombok.Data;

@Data
public class FileQueryDTO {
    // 文件名
    private String fileName;
    
    // 文件类型
    private String fileType;
    
    // 开始时间
    private String beginTime;
    
    // 结束时间
    private String endTime;
    
    // 页码
    private Integer pageNum = 1;
    
    // 每页数量
    private Integer pageSize = 10;
} 