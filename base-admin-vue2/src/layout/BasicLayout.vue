<template>
  <el-container class="app-wrapper">
    <!-- 侧边栏 -->
    <el-aside width="200px" class="sidebar-container">
      <div class="logo">
        <img src="@/assets/logo.png" alt="logo">
        <span v-if="!collapsed">OnlyOffice管理</span>
      </div>
      <el-menu
        :default-active="activeMenu"
        :collapse="collapsed"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
      >
        <el-menu-item index="1" @click="$router.push('/')">
          <i class="el-icon-document"></i>
          <span slot="title">文档管理</span>
        </el-menu-item>
        <el-menu-item index="2" @click="$router.push('/test')">
          <i class="el-icon-s-tools"></i>
          <span slot="title">测试页面</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <!-- 头部 -->
      <el-header height="50px" class="app-header">
        <div class="left">
          <i 
            :class="collapsed ? 'el-icon-s-unfold' : 'el-icon-s-fold'"
            @click="toggleSidebar"
          ></i>
        </div>
        <div class="right">
          <el-dropdown trigger="click">
            <span class="el-dropdown-link">
              admin<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>个人中心</el-dropdown-item>
              <el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>

      <!-- 主要内容区 -->
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'BasicLayout',
  data() {
    return {
      collapsed: false,
      activeMenu: '1'
    }
  },
  methods: {
    toggleSidebar() {
      this.collapsed = !this.collapsed
    },
    logout() {
      // 实现登出逻辑
      this.$router.push('/login')
    }
  }
}
</script>

<style lang="scss" scoped>
.app-wrapper {
  height: 100vh;
}

.sidebar-container {
  background-color: #304156;
  transition: width 0.28s;

  .logo {
    height: 50px;
    padding: 10px;
    display: flex;
    align-items: center;
    background: #2b2f3a;
    
    img {
      width: 32px;
      height: 32px;
    }
    
    span {
      margin-left: 10px;
      color: #fff;
      font-size: 16px;
    }
  }
}

.app-header {
  background-color: #fff;
  border-bottom: 1px solid #dcdfe6;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 15px;

  .left {
    i {
      font-size: 20px;
      cursor: pointer;
    }
  }

  .right {
    .el-dropdown-link {
      cursor: pointer;
      color: #303133;
    }
  }
}

.el-main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style> 