package com.userlabel.api.controller;

import com.userlabel.core.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统首页接口
 */
@RestController
@RequestMapping("/api")
public class IndexController {

    @GetMapping("/")
    public Result<String> index() {
        return Result.success("用户画像标签系统 API 服务运行正常！");
    }

    @GetMapping("/health")
    public Result<Map<String, Object>> health() {
        Map<String, Object> data = new HashMap<>();
        data.put("status", "UP");
        data.put("service", "user-labels-system");
        data.put("version", "1.0.0");
        return Result.success(data);
    }
}
