<template>
  <div class="test-container">
    <el-card title="应用导航">
      <!-- 搜索框 -->
      <div class="search-box">
        <el-input
          v-model="searchText"
          placeholder="输入搜索内容"
          style="width: 500px"
          size="large"
        >
          <template slot="prefix">
            <i class="el-icon-search"></i>
          </template>
        </el-input>
      </div>

      <!-- 应用网格 -->
      <div class="app-grid">
        <!-- 倒计时卡片 -->
        <el-card class="app-card countdown-card">
          <div class="countdown-content">
            <div class="countdown-title">倒计时</div>
            <div class="countdown-number">-581</div>
            <div class="countdown-desc">天</div>
            <div class="countdown-date">2023年6月1日</div>
          </div>
        </el-card>

        <!-- AI助手卡片 -->
        <el-card class="app-card ai-card">
          <div class="ai-logo">
            <!-- <img :src="getAssetUrl('wetab-ai.svg')" alt="WetabAI" /> -->
          </div>
          <div class="ai-title">WeTab AI</div>
          <div class="ai-desc">高效开发助手</div>
          <el-button type="primary" style="width: 100%">Start</el-button>
        </el-card>

        <!-- NBA比赛卡片 -->
        <el-card class="app-card nba-card">
          <div class="nba-header">常规赛</div>
          <div class="nba-matches">
            <div class="match-item" v-for="(match, index) in nbaMatches" :key="index">
              <div class="team-vs">
                <span>{{ match.team1 }}</span>
                <span class="vs">VS</span>
                <span>{{ match.team2 }}</span>
              </div>
              <div class="score">{{ match.score }}</div>
              <div class="status">{{ match.status }}</div>
            </div>
          </div>
        </el-card>

        <!-- 其他应用图标 -->
        <el-card 
          v-for="app in apps" 
          :key="app.id" 
          class="app-card"
          :body-style="{ padding: '10px' }"
        >
          <img :src="app.icon" class="app-icon">
          <div class="app-name">{{ app.name }}</div>
        </el-card>
      </div>
    </el-card>
  </div>
</template>

<script>
import { getAssetUrl } from '@/utils/assets'

export default {
  name: 'Test',
  data() {
    return {
      searchText: '',
      nbaMatches: [
        { team1: '勇士', team2: '湖人', score: '108-123', status: '已结束' },
        { team1: '快船', team2: '太阳', score: '未开始', status: '今日 09:00' },
        { team1: '76人', team2: '雄鹿', score: '进行中', status: '第3节 07:30' }
      ],
      apps: [
        { id: 1, name: '哔哩哔哩', icon: getAssetUrl('bilibili.svg') },
        { id: 2, name: '奶牛快传', icon: getAssetUrl('cow.svg') },
        { id: 3, name: '百度安全论坛', icon: getAssetUrl('oschina.svg') },
        { id: 4, name: '开源中国', icon: getAssetUrl('oschina.svg') },
        { id: 5, name: '扩展迷', icon: getAssetUrl('extension.svg') }
      ]
    }
  }
}
</script>

<style lang="scss" scoped>
.test-container {
  padding: 20px;
}

.search-box {
  margin-bottom: 20px;
  text-align: center;
}

.app-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 20px;
  margin-top: 20px;
}

.app-card {
  height: 200px;
  text-align: center;
  
  .app-icon {
    width: 64px;
    height: 64px;
    margin: 20px 0;
  }
}

// 倒计时卡片样式
.countdown-card {
  .countdown-content {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100%;
  }

  .countdown-title {
    font-size: 18px;
    margin-bottom: 10px;
  }

  .countdown-number {
    font-size: 36px;
    font-weight: bold;
    color: #409EFF;
  }

  .countdown-desc {
    font-size: 14px;
    color: #909399;
  }

  .countdown-date {
    margin-top: 10px;
    font-size: 14px;
    color: #606266;
  }
}

// AI助手卡片样式
.ai-card {
  .ai-logo {
    text-align: center;
    margin: 20px 0;
  }

  .ai-title {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 10px;
  }

  .ai-desc {
    color: #909399;
    margin-bottom: 20px;
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
    color: #909399;
  }

  .score {
    display: flex;
    gap: 16px;
  }

  .status {
    color: #909399;
  }
}

.app-name {
  margin-top: 8px;
  font-size: 14px;
}
</style> 