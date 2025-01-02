<template>
    <div class="home-container">
        <a-layout>
            <a-layout-header class="header">
                <div class="logo">OnlyOffice文档管理</div>
            </a-layout-header>
            <a-layout-content class="content">
                <FileList />
            </a-layout-content>
        </a-layout>
    </div>
</template>

<script setup lang="ts">
import FileList from '@/components/FileList.vue'
</script>

<style scoped>
.home-container {
    min-height: 100vh;
}

.header {
    background: #fff;
    padding: 0 24px;
    display: flex;
    align-items: center;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

.logo {
    font-size: 18px;
    font-weight: bold;
}

.content {
    padding: 24px;
    background: #f0f2f5;
}
</style> 