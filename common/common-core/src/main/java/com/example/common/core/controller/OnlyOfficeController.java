package com.example.common.core.controller;

import com.example.common.core.config.OnlyOfficeConfig;
import com.example.common.core.domain.R;
import com.example.common.core.domain.dto.FileModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Slf4j
@Tag(name = "OnlyOffice", description = "OnlyOffice相关接口")
@RestController
@RequestMapping("/onlyoffice")
public class OnlyOfficeController {

    @Autowired
    private OnlyOfficeConfig config;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing OnlyOffice storage directory: {}", config.getFileStoragePath());
            if (config.getFileStoragePath() == null) {
                throw new IllegalStateException("File storage path is not configured");
            }
            
            checkFilePermissions();
        } catch (Exception e) {
            log.error("Failed to initialize OnlyOffice storage directory", e);
            throw e;
        }
    }

    @Operation(summary = "上传文件")
    @PostMapping("/upload")
    public R<FileModel> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String fileExt = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + fileExt;
        
        // 使用File对象来处理路径
        File targetFile = new File(config.getFileStoragePath(), newFileName);
        // 确保目标文件的父目录存在
        targetFile.getParentFile().mkdirs();
        // 保存文件
        file.transferTo(targetFile);

        FileModel fileModel = new FileModel();
        fileModel.setFileName(originalFilename);
        fileModel.setFileType(fileExt.substring(1));
        fileModel.setKey(newFileName);
        
        return R.ok(fileModel);
    }

    @Operation(summary = "获取编辑配置")
    @GetMapping("/config")
    public R<Map<String, Object>> getConfig(
            @RequestParam String fileName,
            @RequestParam String fileType,
            @RequestParam String key,
            HttpServletRequest request) {
        
        String domain = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String callbackUrl = domain + "/onlyoffice/callback";
        String fileUrl = domain + "/onlyoffice/download/" + key;

        Map<String, Object> editorConfig = new HashMap<>();
        Map<String, Object> document = new HashMap<>();
        document.put("fileType", fileType);
        document.put("key", key);
        document.put("title", fileName);
        document.put("url", fileUrl);
        document.put("permissions", new HashMap<String, Object>() {{
            put("edit", true);
            put("download", true);
            put("review", true);
        }});
        editorConfig.put("document", document);
        
        editorConfig.put("documentType", getDocumentType(fileType));
        
        Map<String, Object> editorConfigInner = new HashMap<>();
        editorConfigInner.put("callbackUrl", callbackUrl);
        editorConfigInner.put("mode", "edit");
        editorConfigInner.put("lang", "zh-CN");
        editorConfigInner.put("user", new HashMap<String, Object>() {{
            put("id", "1");
            put("name", "User");
        }});
        editorConfig.put("editorConfig", editorConfigInner);

        // 生成token
        try {
            Map<String, Object> tokenData = new HashMap<>();
            tokenData.put("document", document);
            tokenData.put("documentType", getDocumentType(fileType));
            tokenData.put("editorConfig", editorConfigInner);

            String token = generateJWT(new ObjectMapper().writeValueAsString(tokenData));
            editorConfig.put("token", token);
        } catch (Exception e) {
            log.error("Error generating token", e);
        }

        return R.ok(editorConfig);
    }

    private String generateJWT(String payload) {
        try {
            String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
            
            String headerBase64 = Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(header.getBytes(StandardCharsets.UTF_8));
            String payloadBase64 = Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(payload.getBytes(StandardCharsets.UTF_8));
            
            String dataToSign = headerBase64 + "." + payloadBase64;
            
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(
                    config.getJwtSecret().getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secretKey);
            
            byte[] signatureBytes = sha256_HMAC.doFinal(dataToSign.getBytes(StandardCharsets.UTF_8));
            String signature = Base64.getUrlEncoder().withoutPadding().encodeToString(signatureBytes);
            
            return dataToSign + "." + signature;
        } catch (Exception e) {
            throw new RuntimeException("Error generating JWT", e);
        }
    }

    @Operation(summary = "下载文件")
    @GetMapping("/download/{key}")
    public void downloadFile(
            @PathVariable String key,
            @RequestParam(required = false) String fileName,
            HttpServletResponse response) throws IOException {
        Path filePath = Paths.get(config.getFileStoragePath(), key);
        File file = filePath.toFile();
        
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        String downloadFileName = fileName != null ? fileName : key;
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + 
                URLEncoder.encode(downloadFileName, "UTF-8") + "\"");
        
        FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
    }

    @Operation(summary = "回调接口")
    @PostMapping("/callback")
    public Map<String, Integer> callback(@RequestBody String body) throws IOException {
        log.info("Received callback: {}", body);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> callbackData = mapper.readValue(body, Map.class);
        
        try {
            // 处理文件保存
            if ("2".equals(String.valueOf(callbackData.get("status")))) {
                String downloadUrl = (String) callbackData.get("url");
                if (downloadUrl != null) {
                    // 获取文件key
                    String key = (String) ((Map)callbackData.get("key")).get("key");
                    if (key != null) {
                        // 保存文件
                        File targetFile = new File(config.getFileStoragePath(), key);
                        // TODO: 从downloadUrl下载文件并保存到targetFile
                    }
                }
            }
            
            Map<String, Integer> result = new HashMap<>();
            result.put("error", 0);
            return result;
        } catch (Exception e) {
            log.error("Error processing callback", e);
            Map<String, Integer> result = new HashMap<>();
            result.put("error", 1);
            return result;
        }
    }

    @Operation(summary = "获取文件列表")
    @GetMapping("/list")
    public R<List<FileModel>> getFileList() {
        File dir = new File(config.getFileStoragePath());
        List<FileModel> files = new ArrayList<>();
        
        if (dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    FileModel model = new FileModel();
                    model.setFileName(file.getName());
                    model.setKey(file.getName());
                    String fileType = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                    model.setFileType(fileType);
                    files.add(model);
                }
            }
        }
        
        return R.ok(files);
    }

    @Operation(summary = "删除文件")
    @DeleteMapping("/delete/{key}")
    public R<Boolean> deleteFile(@PathVariable String key) {
        File file = new File(config.getFileStoragePath(), key);
        if (file.exists() && file.isFile()) {
            return R.ok(file.delete());
        }
        return R.fail("文件不存在");
    }

    private String getDocumentType(String fileType) {
        switch (fileType.toLowerCase()) {
            case "doc":
            case "docx":
            case "odt":
            case "rtf":
            case "txt":
                return "text";
            case "xls":
            case "xlsx":
            case "ods":
                return "spreadsheet";
            case "ppt":
            case "pptx":
            case "odp":
                return "presentation";
            default:
                return "text";
        }
    }

    private void checkFilePermissions() {
        File storageDir = new File(config.getFileStoragePath());
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        if (!storageDir.canRead() || !storageDir.canWrite()) {
            log.error("Storage directory {} does not have proper permissions", config.getFileStoragePath());
            throw new RuntimeException("Storage directory does not have proper permissions");
        }
    }
} 