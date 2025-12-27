package com.example.avatar;

import org.springframework.stereotype.Component;
import run.halo.app.plugin.BasePlugin;
import run.halo.app.plugin.PluginContext;

@Component
public class AvatarPlugin extends BasePlugin {
    public AvatarPlugin(PluginContext context) {
        super(context);
    }

    @Override
    public void start() {
        System.out.println("Letter Avatar Plugin started!");
    }

    @Override
    public void stop() {
        System.out.println("Letter Avatar Plugin stopped!");
    }
}