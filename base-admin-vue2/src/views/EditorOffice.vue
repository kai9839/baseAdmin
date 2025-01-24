<template>
  <div class="editor-container">
    <div id="placeholder" class="editor-content"></div>
  </div>
</template>

<script>
import { getEditorConfig } from '@/api/onlyoffice'

export default {
  name: 'Editor',
  props: {
    options: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      docEditor: null
    }
  },
  async mounted() {
    await this.loadDocsApi()
    await this.initEditor()
  },
  beforeDestroy() {
    if (this.docEditor) {
      this.docEditor.destroyEditor()
    }
    // 移除 script 标签
    const script = document.querySelector('script[src*="api.js"]')
    if (script) {
      script.remove()
    }
  },
  methods: {
    loadDocsApi() {
      return new Promise((resolve, reject) => {
        const script = document.createElement('script')
        script.src = `${process.env.VUE_APP_ONLYOFFICE_URL}/web-apps/apps/api/documents/api.js`
        script.onload = resolve
        script.onerror = reject
        document.head.appendChild(script)
      })
    },
    async initEditor() {
      // const { fileName, fileType, key } = this.$route.query
      // if (!fileName || !fileType || !key) return
      const { fileName, fileType, key } = this.options
      if (!fileName || !fileType || !key) return

      try {
        const config = await getEditorConfig({
          fileName,
          fileType,
          key
        })
        
        if (this.docEditor) {
          this.docEditor.destroyEditor()
        }
        
        this.docEditor = new window.DocsAPI.DocEditor('placeholder', config)
      } catch (error) {
        console.error('初始化编辑器失败:', error)
        this.$message.error('初始化编辑器失败')
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.editor-container {
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.editor-content {
  flex: 1;
}
</style> 