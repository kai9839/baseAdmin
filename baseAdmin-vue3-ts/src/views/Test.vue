<template>
    <div class="test-container">
        <a-card title="应用导航" :bordered="false">
            <!-- 搜索框 -->
            <div class="search-box">
                <a-input-search
                    v-model:value="searchText"
                    placeholder="输入搜索内容"
                    style="width: 500px"
                    size="large"
                >
                    <template #prefix>
                        <search-outlined />
                    </template>
                </a-input-search>
            </div>

            <!-- 应用网格 -->
            <div class="app-grid">
                <!-- 倒计时卡片 -->
                <a-card class="app-card countdown-card">
                    <div class="countdown-content">
                        <div class="countdown-title">倒计时</div>
                        <div class="countdown-number">-581</div>
                        <div class="countdown-desc">天</div>
                        <div class="countdown-date">2023年6月1日</div>
                    </div>
                </a-card>

                <!-- AI助手卡片 -->
                <a-card class="app-card ai-card">
                    <template #cover>
                        <div class="ai-logo">
                            <img :src="getAssetUrl('wetab-ai.svg')" alt="WetabAI" />
                        </div>
                    </template>
                    <div class="ai-title">WeTab AI</div>
                    <div class="ai-desc">高效开发助手</div>
                    <a-button type="primary" block>Start</a-button>
                </a-card>

                <!-- NBA比赛卡片 -->
                <a-card class="app-card nba-card">
                    <div class="nba-header">常规赛</div>
                    <div class="nba-matches">
                        <div class="match-item" v-for="(match, index) in nbaMatches" :key="index">
                            <div class="team-vs">
                                <span>{{ match.team1 }}</span>
                                <span class="vs">VS</span>
                                <span>{{ match.team2 }}</span>
                            </div>
                            <div class="score">
                                <span>{{ match.score1 }}</span>
                                <span>{{ match.score2 }}</span>
                            </div>
                            <div class="status">{{ match.status }}</div>
                        </div>
                    </div>
                </a-card>

                <!-- 其他应用图标 -->
                <template v-for="app in apps" :key="app.id">
                    <a-card class="app-card" hoverable>
                        <template #cover>
                            <img :src="app.icon" :alt="app.name" />
                        </template>
                        <div class="app-name">{{ app.name }}</div>
                    </a-card>
                </template>
            </div>
        </a-card>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { SearchOutlined } from '@ant-design/icons-vue'
import { getAssetUrl } from '@/utils/assets'

const searchText = ref('')

// NBA比赛数据
const nbaMatches = [
    {
        team1: '勇士',
        team2: '火箭',
        score1: '99',
        score2: '110',
        status: '已结束'
    },
    {
        team1: '湖人',
        team2: '篮网',
        score1: '120',
        score2: '139',
        status: '已结束'
    },
    {
        team1: '76人',
        team2: '魔术',
        score1: '107',
        score2: '113',
        status: '已结束'
    }
]

// 应用列表
const apps = [
    { id: 1, name: '哔哩哔哩', icon: getAssetUrl('bilibili.svg') },
    { id: 2, name: '奶牛快传', icon: getAssetUrl('cow.svg') },
    { id: 3, name: '百度安全论坛', icon: getAssetUrl('oschina.svg') },
    { id: 4, name: '开源中国', icon: getAssetUrl('oschina.svg') },
    { id: 5, name: '扩展迷', icon: getAssetUrl('extension.svg') }
]
</script>

<style scoped lang="scss">
.test-container {
    padding: 24px;
    background: #f0f2f5;
    min-height: 100vh;
}

.search-box {
    display: flex;
    justify-content: center;
    margin-bottom: 24px;
}

.app-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 24px;
    padding: 24px;
}

.app-card {
    height: 200px;
    text-align: center;
    transition: all 0.3s;

    &:hover {
        transform: translateY(-5px);
    }
}

// 倒计时卡片样式
.countdown-card {
    background: #2c3e50;
    color: white;

    .countdown-content {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        height: 100%;
    }

    .countdown-number {
        font-size: 48px;
        font-weight: bold;
    }

    .countdown-desc {
        font-size: 16px;
        margin: 8px 0;
    }

    .countdown-date {
        font-size: 14px;
        opacity: 0.8;
    }
}

// AI助手卡片样式
.ai-card {
    background: #1a237e;
    color: white;

    .ai-logo {
        height: 80px;
        display: flex;
        align-items: center;
        justify-content: center;

        img {
            height: 60px;
        }
    }

    .ai-title {
        font-size: 18px;
        font-weight: bold;
        margin: 12px 0;
    }

    .ai-desc {
        font-size: 14px;
        opacity: 0.8;
        margin-bottom: 12px;
    }
}

// NBA比赛卡片样式
.nba-card {
    grid-column: span 2;
    height: auto;

    .nba-header {
        font-size: 16px;
        font-weight: bold;
        margin-bottom: 16px;
    }

    .match-item {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 8px 0;
        border-bottom: 1px solid #f0f0f0;

        &:last-child {
            border-bottom: none;
        }
    }

    .team-vs {
        display: flex;
        gap: 8px;
    }

    .vs {
        color: #999;
    }

    .score {
        display: flex;
        gap: 16px;
    }

    .status {
        color: #999;
    }
}

.app-name {
    margin-top: 8px;
    font-size: 14px;
}
</style> 