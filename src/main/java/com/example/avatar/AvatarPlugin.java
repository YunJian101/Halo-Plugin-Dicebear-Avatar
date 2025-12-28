package com.example.avatar;

import org.springframework.stereotype.Component;
import run.halo.app.plugin.BasePlugin;
import run.halo.app.plugin.PluginContext;

/**
 * 插件主类，继承自 BasePlugin。
 * Halo 会在加载插件时初始化此类。
 */
@Component
public class AvatarPlugin extends BasePlugin {
    // 构造函数，注入插件上下文环境
    public AvatarPlugin(PluginContext context) {
        super(context);
    }

    @Override
    public void start() {
        // 插件启动时执行的逻辑
        System.out.println("Dicebear Avatar Plugin started!");
    }

    @Override
    public void stop() {
        // 插件停止时执行的逻辑（如释放资源）
        System.out.println("Dicebear Avatar Plugin stopped!");
    }
}