<template>
    <a-layout
        class="layout"
        :class="{
            'layout-side': configStore.layout === 'side',
            'layout-top': configStore.layout === 'top',
            'layout-mix': configStore.layout === 'mix',
            'fixed-header': configStore.fixedHeader,
            'fixed-sidebar': configStore.fixedSidebar,
            'show-tags': configStore.tagsView,
            [`theme-${configStore.theme}`]: true,
        }"
    >
        <!-- 侧边栏 -->
        <a-layout-sider
            v-if="configStore.layout !== 'top'"
            v-model:collapsed="collapsed"
            collapsible
            :class="{ 'fixed-sidebar': configStore.fixedSidebar }"
        >
            <div class="logo" v-if="configStore.showLogo">
                <img src="@/assets/logo.png" alt="logo" />
                <span v-show="!collapsed">OnlyOffice管理</span>
            </div>
            <a-menu
                v-model:selectedKeys="selectedKeys"
                theme="dark"
                mode="inline"
            >
                <a-menu-item key="1">
                    <template #icon>
                        <file-outlined />
                    </template>
                    <router-link to="/">文档管理</router-link>
                </a-menu-item>
                <a-menu-item key="2">
                    <template #icon>
                        <experiment-outlined />
                    </template>
                    <router-link to="/test">测试页面</router-link>
                </a-menu-item>
            </a-menu>
        </a-layout-sider>

        <a-layout>
            <!-- 头部 -->
            <a-layout-header
                class="header"
                :class="{ 'fixed-header': configStore.fixedHeader }"
            >
                <!-- 左侧部分 -->
                <div class="header-left">
                    <menu-fold-outlined
                        v-if="!collapsed && configStore.layout !== 'top'"
                        class="trigger"
                        @click="() => (collapsed = true)"
                    />
                    <menu-unfold-outlined
                        v-else-if="configStore.layout !== 'top'"
                        class="trigger"
                        @click="() => (collapsed = false)"
                    />
                    <!-- 顶部导航 -->
                    <a-menu
                        v-if="configStore.topNav"
                        mode="horizontal"
                        v-model:selectedKeys="selectedKeys"
                    >
                        <a-menu-item key="1">
                            <router-link to="/">文档管理</router-link>
                        </a-menu-item>
                    </a-menu>
                </div>

                <!-- 右侧部分 -->
                <div class="header-right">
                    <a-space>
                        <a-button
                            type="link"
                            @click="showSetting = true"
                        >
                            <template #icon>
                                <setting-outlined />
                            </template>
                        </a-button>
                        <a-dropdown>
                            <a class="ant-dropdown-link">
                                <user-outlined />
                                <span class="username">Admin</span>
                            </a>
                            <template #overlay>
                                <a-menu>
                                    <a-menu-item key="0">
                                        <user-outlined />
                                        个人中心
                                    </a-menu-item>
                                    <a-menu-divider />
                                    <a-menu-item key="3" @click="handleLogout">
                                        <logout-outlined />
                                        退出登录
                                    </a-menu-item>
                                </a-menu>
                            </template>
                        </a-dropdown>
                    </a-space>
                </div>
            </a-layout-header>

            <!-- 标签页 -->
            <div v-if="configStore.tagsView" class="tags-view">
                <!-- 这里可以添加标签页组件 -->
            </div>

            <!-- 内容区 -->
            <a-layout-content
                class="content"
                :class="{ 'content-fluid': configStore.contentWidth === 'fluid' }"
            >
                <div class="content-wrapper">
                    <router-view></router-view>
                </div>
            </a-layout-content>

            <!-- 页脚 -->
            <a-layout-footer v-if="configStore.showFooter" class="footer">
                OnlyOffice Document Management ©2024 Created by Your Company
            </a-layout-footer>
        </a-layout>
    </a-layout>

    <!-- 系统配置抽屉 -->
    <setting-drawer v-model:visible="showSetting" />
</template>

<script setup lang="ts">
import { ref } from 'vue'
import {
    FileOutlined,
    MenuFoldOutlined,
    MenuUnfoldOutlined,
    UserOutlined,
    LogoutOutlined,
    SettingOutlined,
    ExperimentOutlined
} from '@ant-design/icons-vue'
import SettingDrawer from '@/components/SettingDrawer.vue'
import { useConfigStore } from '@/store/modules/config'

const collapsed = ref(false)
const selectedKeys = ref(['1'])
const showSetting = ref(false)
const configStore = useConfigStore()

const handleLogout = () => {
    // 处理退出登录
    console.log('logout')
}
</script>

<style lang="scss" scoped>
.layout {
    .logo {
        display: flex;
        align-items: center;
        justify-content: center;
        height: 64px;
        overflow: hidden;
        img {
            width: 32px;
            height: 32px;
        }
    }
    min-height: 100vh;

    &.layout-top {
        .header {
            padding: 0 24px;
        }
    }

    &.fixed-header {
        .header {
            position: fixed;
            top: 0;
            right: 0;
            z-index: 9;
            width: calc(100% - 200px);
            transition: width 0.2s;
        }

        .content {
            margin-top: 64px;
        }
    }

    &.fixed-sidebar {
        .ant-layout-sider {
            position: fixed;
            left: 0;
            height: 100vh;
            overflow: auto;
            z-index: 10;
        }

        .ant-layout {
            margin-left: 200px;
        }
    }
}

.header {
    background: #fff;
    padding: 0;
    display: flex;
    align-items: center;
    justify-content: space-between;
    box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

    .header-left {
        display: flex;
        align-items: center;
    }

    .header-right {
        padding-right: 24px;
    }
}

.trigger {
    padding: 0 24px;
    font-size: 18px;
    cursor: pointer;
    transition: color 0.3s;

    &:hover {
        color: #1890ff;
    }
}

.tags-view {
    background: #fff;
    border-bottom: 1px solid #f0f0f0;
    padding: 6px 16px;
}

.content {
    margin: 24px;
    
    &.content-fluid {
        max-width: 1200px;
        margin: 24px auto;
    }
}

.content-wrapper {
    background: #fff;
    padding: 24px;
    min-height: 280px;
}

.footer {
    text-align: center;
    padding: 20px;
}
</style> 