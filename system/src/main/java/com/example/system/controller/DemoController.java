package com.example.system.controller;

import com.example.common.core.domain.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "示例接口", description = "示例接口相关API")
@RestController
@RequestMapping("/demo")
public class DemoController {

    // @Log(title = "示例模块", action = "获取欢迎信息")  // 暂时注释掉Log注解
    @Operation(summary = "示例方法", description = "这是一个示例方法的详细描述")
    @Parameter(name = "name", description = "名称", required = true)
    @GetMapping("/hello")
    public R<String> hello(@RequestParam String name) {
        return R.ok("Hello " + name);
    }
} 