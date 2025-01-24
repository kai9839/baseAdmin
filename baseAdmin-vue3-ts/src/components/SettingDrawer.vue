<template>
    <a-drawer
        title="系统配置"
        placement="right"
        :visible="visible"
        @close="onClose"
        width="300"
    >
        <div class="setting-drawer-content">
            <div class="setting-drawer-block">
                <h3>主题配置</h3>
                <a-divider />
                <div class="theme-color">
                    <span>主题色</span>
                    <div class="color-list">
                        <a-tooltip
                            v-for="color in configStore.colorList"
                            :key="color"
                            :title="color"
                        >
                            <div
                                class="color-block"
                                :style="{ backgroundColor: color }"
                                :class="{ active: configStore.primaryColor === color }"
                                @click="handleColorChange(color)"
                            ></div>
                        </a-tooltip>
                    </div>
                </div>
                <div class="theme-item">
                    <span>主题模式</span>
                    <a-select
                        v-model:value="configStore.theme"
                        style="width: 120px"
                    >
                        <a-select-option value="light">亮色主题</a-select-option>
                        <a-select-option value="dark">暗色主题</a-select-option>
                        <a-select-option value="realDark">深色主题</a-select-option>
                    </a-select>
                </div>
            </div>

            <div class="setting-drawer-block">
                <h3>导航设置</h3>
                <a-divider />
                <div class="setting-item">
                    <span>导航模式</span>
                    <a-select
                        v-model:value="configStore.layout"
                        style="width: 120px"
                    >
                        <a-select-option value="side">侧边菜单</a-select-option>
                        <a-select-option value="top">顶部菜单</a-select-option>
                        <a-select-option value="mix">混合菜单</a-select-option>
                    </a-select>
                </div>
                <div class="setting-item">
                    <span>固定头部</span>
                    <a-switch v-model:checked="configStore.fixedHeader" />
                </div>
                <div class="setting-item">
                    <span>固定侧边栏</span>
                    <a-switch v-model:checked="configStore.fixedSidebar" />
                </div>
                <div class="setting-item">
                    <span>显示顶部导航</span>
                    <a-switch v-model:checked="configStore.topNav" />
                </div>
                <div class="setting-item">
                    <span>显示标签页</span>
                    <a-switch v-model:checked="configStore.tagsView" />
                </div>
                <div class="setting-item">
                    <span>显示Logo</span>
                    <a-switch v-model:checked="configStore.showLogo" />
                </div>
                <div class="setting-item">
                    <span>显示页脚</span>
                    <a-switch v-model:checked="configStore.showFooter" />
                </div>
            </div>

            <a-button type="primary" block @click="handleReset">
                重置配置
            </a-button>
        </div>
    </a-drawer>
</template>

<script setup lang="ts">
import { useConfigStore } from '@/store/modules/config'

const props = defineProps<{
    visible: boolean
}>()

const emit = defineEmits<{
    (e: 'update:visible', visible: boolean): void
}>()

const configStore = useConfigStore()

const onClose = () => {
    emit('update:visible', false)
}

const handleColorChange = (color: string) => {
    configStore.setConfig({ primaryColor: color })
    // 这里可以添加动态更改主题色的逻辑
}

const handleReset = () => {
    configStore.resetConfig()
}
</script>

<style scoped>
.setting-drawer-content {
    display: flex;
    flex-direction: column;
    gap: 24px;
}

.setting-drawer-block {
    h3 {
        margin-bottom: 12px;
        font-size: 14px;
        line-height: 22px;
    }
}

.theme-color {
    margin-bottom: 24px;

    .color-list {
        display: flex;
        gap: 8px;
        margin-top: 8px;
        flex-wrap: wrap;
    }

    .color-block {
        width: 20px;
        height: 20px;
        border-radius: 2px;
        cursor: pointer;
        border: 1px solid #f0f0f0;
        transition: all 0.3s;

        &:hover {
            transform: scale(1.1);
        }

        &.active {
            border: 1px solid #1890ff;
            box-shadow: 0 0 0 2px rgba(24, 144, 255, 0.2);
        }
    }
}

.setting-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 24px;
}
</style> 