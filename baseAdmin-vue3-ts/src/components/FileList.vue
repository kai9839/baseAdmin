<template>
    <div class="file-list">
        <a-upload
            :customRequest="handleUpload"
            :showUploadList="false"
            :multiple="false"
            accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx"
        >
            <a-button type="primary">
                <upload-outlined /> 上传文件
            </a-button>
        </a-upload>

        <a-table
            :columns="columns"
            :data-source="fileList"
            :loading="loading"
            :pagination="false"
            class="mt-4"
        >
            <template #bodyCell="{ column, record }">
                <template v-if="column.key === 'action'">
                    <a-space>
                        <a @click="handleEdit(record)">编辑</a>
                        <a @click="handleDownload(record)">下载</a>
                        <a-popconfirm
                            title="确定要删除这个文件吗？"
                            @confirm="handleDelete(record)"
                        >
                            <a class="text-red-500">删除</a>
                        </a-popconfirm>
                    </a-space>
                </template>
            </template>
        </a-table>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { UploadOutlined } from '@ant-design/icons-vue'
import { message } from 'ant-design-vue'
import type { FileModel } from '@/types/file'
import { getFileList, uploadFile, deleteFile } from '@/api/onlyoffice'
import { useRouter } from 'vue-router'

const router = useRouter()
const loading = ref(false)
const fileList = ref<FileModel[]>([])

const columns = [
    {
        title: '文件名',
        dataIndex: 'fileName',
        key: 'fileName'
    },
    {
        title: '类型',
        dataIndex: 'fileType',
        key: 'fileType'
    },
    {
        title: '操作',
        key: 'action',
        width: 200
    }
]

const loadFiles = async () => {
    loading.value = true
    try {
        fileList.value = await getFileList()
    } catch (error) {
        console.error(error)
    } finally {
        loading.value = false
    }
}

const handleUpload = async ({ file }: { file: File }) => {
    try {
        await uploadFile(file)
        message.success('上传成功')
        loadFiles()
    } catch (error) {
        console.error(error)
    }
}

const handleEdit = (record: FileModel) => {
    router.push({
        name: 'Editor',
        query: {
            fileName: record.fileName,
            fileType: record.fileType,
            key: record.key
        }
    })
}

const handleDownload = (record: FileModel) => {
    window.location.href = `/onlyoffice/download/${record.key}?fileName=${record.fileName}`
}

const handleDelete = async (record: FileModel) => {
    try {
        await deleteFile(record.key)
        message.success('删除成功')
        loadFiles()
    } catch (error) {
        console.error(error)
    }
}

onMounted(() => {
    loadFiles()
})
</script> 