package com.example.avatar;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;

@RestController
public class AvatarController {

    @GetMapping(value = "/api/plugins/dicebear-avatar/generate")
    public Mono<ResponseEntity<?>> generate(@RequestParam(value = "name", defaultValue = "?") String name) {
        return generateAvatar(name);
    }

    // 兼容 Gravatar 路径格式 /avatar/{hash}
    @GetMapping(value = {"/avatar/{name}", "/api/plugins/dicebear-avatar/avatar/{name}"})
    public Mono<ResponseEntity<?>> generatePath(@PathVariable("name") String name) {
        // 移除可能的后缀 (如 .jpg, .png)
        String cleanName = name;
        if (name.contains(".")) {
            cleanName = name.substring(0, name.lastIndexOf("."));
        }
        return generateAvatar(cleanName);
    }

    private Mono<ResponseEntity<?>> generateAvatar(String name) {
        String text = (name == null || name.trim().isEmpty()) ? "default" : name.trim();

        // 默认风格
        String style = "pixel-art";
        String targetUrl = "https://api.dicebear.com/7.x/" + style + "/svg?seed=" + text;
        return Mono.just(ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(targetUrl))
                .build());
    }
}