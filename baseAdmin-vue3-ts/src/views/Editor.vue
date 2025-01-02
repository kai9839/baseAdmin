<template>
    <div class="editor-container">
        <div id="placeholder" class="editor-content"></div>
    </div>
</template>

<script setup lang="ts">
import { onMounted, onBeforeUnmount } from 'vue'
import { useRoute } from 'vue-router'
import { getEditorConfig } from '@/api/onlyoffice'

declare global {
    interface Window {
        DocsAPI: any
    }
}

const route = useRoute()
let docEditor: any = null

const loadDocsApi = () => {
    return new Promise((resolve, reject) => {
        const script = document.createElement('script')
        script.src = `${import.meta.env.VITE_ONLYOFFICE_URL}/web-apps/apps/api/documents/api.js`
        script.onload = resolve
        script.onerror = reject
        document.head.appendChild(script)
    })
}

const initEditor = async () => {
    const { fileName, fileType, key } = route.query
    if (!fileName || !fileType || !key) return

    try {
        // 先加载 DocsAPI
        await loadDocsApi()

        const config = await getEditorConfig({
            fileName: fileName as string,
            fileType: fileType as string,
            key: key as string
        })
        
        if (docEditor) {
            docEditor.destroyEditor()
        }
        
        docEditor = new window.DocsAPI.DocEditor('placeholder', config)
    } catch (error) {
        console.error('初始化编辑器失败:', error)
    }
}

onMounted(() => {
    initEditor()
})

onBeforeUnmount(() => {
    if (docEditor) {
        docEditor.destroyEditor()
    }
    // 移除 script 标签
    const script = document.querySelector('script[src*="api.js"]')
    if (script) {
        script.remove()
    }
})
</script>

<style scoped>
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