# Halo Plugin Dicebear Avatar

> 替换 Halo 博客评论组件中的头像，为匿名用户和未上传头像用户提供一个评论区头像（需配合官方的评论组件）。

## 简介

这是一个 [Halo](https://halo.run) 2.x 插件，利用 [DiceBear](https://www.dicebear.com/) API 生成有趣的像素艺术 (Pixel Art) 风格头像，用于替换评论区默认的 Gravatar 头像或空白头像。

## 特性

*   自动为无头像用户生成唯一的像素风格头像。
*   兼容 Gravatar 协议，通过配置镜像地址即可无缝集成。
*   轻量级，开箱即用。

## 安装与使用

1.  **下载插件**：在 GitHub [Releases](https://github.com/YunJian101/Halo-Plugin-Dicebear-Avatar/releases) 页面下载最新版本的 `.jar` 文件。
2.  **安装插件**：进入 Halo 后台 -> 插件 -> 安装插件，上传 JAR 包并启用。

## 配置说明 (重要)

为了让评论组件正确调用本插件生成的头像，**必须**进行以下设置：

1.  进入 Halo 后台 -> **系统** -> **插件** -> **评论组件** -> **头像设置**。
2.  勾选 **启用第三方头像**。
3.  在 **Gravatar 头像服务镜像地址** 中填入：**您的博客域名地址**。
    *   例如: `https://shifeiyu.cn`
    *   注意：如果您的博客使用非标准端口（如 8090），请务必加上端口号，例如 `http://220.198.12.138:8090`。

## 贡献与支持

*   **作者**: 是飞鱼
*   **仓库**: Halo-Plugin-Dicebear-Avatar
*   **问题反馈**: 如果遇到问题，请提交 Issue。

## 许可证

本项目遵循 GPL-3.0 许可证。