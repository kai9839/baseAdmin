import { defineStore } from 'pinia'
import type { ThemeConfig, AppConfig } from '@/types/config'

const defaultConfig: AppConfig = {
    title: 'OnlyOffice管理系统',
    version: '1.0.0',
    primaryColor: '#1890ff',
    theme: 'light',
    layout: 'side',
    contentWidth: 'fluid',
    fixedHeader: true,
    fixedSidebar: true,
    topNav: false,
    tagsView: true,
    showLogo: true,
    showFooter: true,
    colorList: [
        '#1890ff',
        '#f5222d',
        '#fa541c',
        '#faad14',
        '#13c2c2',
        '#52c41a',
        '#2f54eb',
        '#722ed1',
    ],
}

export const useConfigStore = defineStore('config', {
    state: (): AppConfig => ({
        ...defaultConfig,
    }),
    actions: {
        setConfig(config: Partial<AppConfig>) {
            Object.assign(this, config)
        },
        resetConfig() {
            Object.assign(this, defaultConfig)
        },
    },
    persist: {
        enabled: true,
        strategies: [
            {
                key: 'app-config',
                storage: localStorage,
            },
        ],
    },
}) 