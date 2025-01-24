<template>
    <div class="home-container">
        <a-card title="文档管理">
            <FileList />
        </a-card>
    </div>
</template>

<script setup lang="ts">
import FileList from '@/components/FileList.vue'
</script>

<style scoped>
.home-container {
    min-height: 100%;
}
</style> 