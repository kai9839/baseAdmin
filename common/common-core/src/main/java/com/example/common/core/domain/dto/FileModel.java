package com.example.common.core.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "文件信息")
public class FileModel {
    
    @Schema(description = "文件名")
    private String fileName;
    
    @Schema(description = "文件类型")
    private String fileType;
    
    @Schema(description = "文件标识")
    private String key;
} 