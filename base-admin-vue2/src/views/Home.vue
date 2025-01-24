<template>
  <div class="home-container">
    <el-card>
      <div slot="header">
        <span>文档管理</span>
      </div>
      <file-list></file-list>
    </el-card>
  </div>
</template>

<script>
import FileList from '@/components/FileList.vue'

export default {
  name: 'Home',
  components: {
    FileList
  }
}
</script>

<style lang="scss" scoped>
.home-container {
  min-height: 100%;
}
</style> 