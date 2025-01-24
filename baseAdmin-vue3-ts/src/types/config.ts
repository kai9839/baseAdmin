export interface ThemeConfig {
    // 主题色
    primaryColor: string;
    // 预设主题
    theme: 'light' | 'dark' | 'realDark';
    // 导航模式
    layout: 'side' | 'top' | 'mix';
    // 内容区域宽度
    contentWidth: 'fluid' | 'fixed';
    // 固定头部
    fixedHeader: boolean;
    // 固定侧边栏
    fixedSidebar: boolean;
    // 显示顶部导航
    topNav: boolean;
    // 显示标签页
    tagsView: boolean;
    // 显示logo
    showLogo: boolean;
    // 显示页脚
    showFooter: boolean;
    // 主题色列表
    colorList: string[];
}

export interface AppConfig extends ThemeConfig {
    // 其他全局配置
    title: string;
    version: string;
} 