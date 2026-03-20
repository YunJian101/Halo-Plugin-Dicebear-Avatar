package com.example.avatar;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import run.halo.app.plugin.SettingFetcher;

import java.net.URI;

/**
 * 头像控制器
 * 负责处理头像生成请求，并管理配置文件的同步。
 */
@RestController
public class AvatarController {

    // Halo 提供的配置获取工具，用于从数据库读取插件设置
    private final SettingFetcher settingFetcher;

    // 内存缓存，默认值为 pixel-art，避免每次都读写文件
    private volatile String cachedStyle = "pixel-art";

    public AvatarController(SettingFetcher settingFetcher) {
        this.settingFetcher = settingFetcher;
    }

    /**
     * 标准头像生成接口
     * @param name 用户名或种子字符串
     */
    @GetMapping(value = "/api/plugins/dicebear-avatar/generate")
    public Mono<ResponseEntity<?>> generate(@RequestParam(value = "name", defaultValue = "?") String name) {
        return generateAvatar(name);
    }

    /**
     * 兼容 Gravatar 路径格式的接口 /avatar/{name}
     * 方便直接替换原有 Gravatar 服务
     */
    @GetMapping(value = {"/avatar/{name}", "/api/plugins/dicebear-avatar/avatar/{name}"})
    public Mono<ResponseEntity<?>> generatePath(@PathVariable("name") String name) {
        // 移除可能的后缀 (如 .jpg, .png)
        String cleanName = name;
        if (name.contains(".")) {
            cleanName = name.substring(0, name.lastIndexOf("."));
        }
        return generateAvatar(cleanName);
    }

    /**
     * 核心逻辑：生成头像重定向响应
     * 1. 获取最新配置 (从数据库获取)
     * 2. 更新内存缓存
     * 3. 返回 DiceBear API 的重定向链接
     */
    private Mono<ResponseEntity<?>> generateAvatar(String name) {
        // 使用 fromCallable 将阻塞的配置读取操作放入弹性线程池执行，防止阻塞 Netty 线程导致异常
        return Mono.fromCallable(this::fetchStyleFromSettings)
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(latestStyle -> {
                    // 检查配置是否变更：只有变更时才更新缓存
                    if (!latestStyle.equals(cachedStyle)) {
                        cachedStyle = latestStyle;
                    }

                    String seed = (name == null || name.trim().isEmpty()) ? "default" : name.trim();
                    
                    // 拼接 DiceBear API 地址
                    String targetUrl = "https://api.dicebear.com/7.x/" + cachedStyle + "/svg?seed=" + seed;
                    
                    // 返回 302 Found 状态码，重定向浏览器到目标图片地址
                    return Mono.just(ResponseEntity.status(HttpStatus.FOUND)
                            .location(URI.create(targetUrl))
                            .header("Cache-Control", "no-cache")
                            .build());
                });
    }

    /**
     * 从 Halo 设置中获取风格
     * 如果获取失败则返回默认值
     */
    private String fetchStyleFromSettings() {
        try {
            var val = settingFetcher.getSettingValue("profile");
            if (val != null && !val.isNull() && val.has("style")) {
                String style = val.get("style").asString();
                if (style != null && !style.isEmpty()) {
                    return style;
                }
            }
        } catch (Exception e) {
            // 获取设置异常，忽略
        }
        return "pixel-art"; // 默认风格
    }
}