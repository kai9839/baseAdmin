<template>
    <div class="file-list">
        <!-- 查询表单 -->
        <a-form layout="inline" class="search-form">
            <a-form-item label="文件名">
                <a-input
                    v-model:value="queryParams.fileName"
                    placeholder="请输入文件名"
                    allow-clear
                />
            </a-form-item>
            <a-form-item label="文件类型">
                <a-select
                    v-model:value="queryParams.fileType"
                    placeholder="请选择文件类型"
                    style="width: 120px"
                    allow-clear
                >
                    <a-select-option value="doc">Word文档</a-select-option>
                    <a-select-option value="xls">Excel表格</a-select-option>
                    <a-select-option value="ppt">PPT演示</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="创建时间">
                <a-range-picker
                    v-model:value="dateRange"
                    show-time
                    @change="handleDateChange"
                />
            </a-form-item>
            <a-form-item>
                <a-space>
                    <a-button type="primary" @click="handleQuery">查询</a-button>
                    <a-button @click="resetQuery">重置</a-button>
                </a-space>
            </a-form-item>
        </a-form>

        <!-- 操作按钮 -->
        <div class="operation-bar">
            <a-upload
                :customRequest="handleUpload"
                :showUploadList="false"
                :multiple="false"
                accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx,.pdf"
            >
                <a-button type="primary">
                    <UploadOutlined />
                    上传文件
                </a-button>
            </a-upload>
        </div>

        <!-- 文件列表 -->
        <a-table
            :columns="columns"
            :data-source="fileList"
            :loading="loading"
            :pagination="{
                total: total,
                current: queryParams.pageNum,
                pageSize: queryParams.pageSize,
                showSizeChanger: true,
                showTotal: (total) => `共 ${total} 条`,
                onChange: handleTableChange
            }"
        >
            <!-- 表格列配置 -->
            <template #bodyCell="{ column, record }">
                <template v-if="column.key === 'fileSize'">
                    {{ formatFileSize(record.fileSize) }}
                </template>
                <template v-if="column.key === 'createTime'">
                    {{ formatDate(record.createTime) }}
                </template>
                <template v-if="column.key === 'action'">
                    <a-space>
                        <a-button type="link" @click="handleEdit(record)">
                            <template #icon>
                                <EditOutlined />
                            </template>
                            编辑
                        </a-button>
                        <a-button type="link" @click="handleDownload(record)">
                            <template #icon>
                                <DownloadOutlined />
                            </template>
                            下载
                        </a-button>
                        <a-popconfirm
                            title="确定要删除这个文件吗？"
                            @confirm="handleDelete(record)"
                        >
                            <a-button type="link" danger>
                                <template #icon>
                                    <DeleteOutlined />
                                </template>
                                删除
                            </a-button>
                        </a-popconfirm>
                    </a-space>
                </template>
            </template>
        </a-table>
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import type { Dayjs } from 'dayjs'
import { message } from 'ant-design-vue'
import { UploadOutlined, EditOutlined, DeleteOutlined, DownloadOutlined } from '@ant-design/icons-vue'
import { useRouter } from 'vue-router'
import type { FileModel } from '@/types/file'
import type { FileQueryParams } from '@/api/onlyoffice'
import { getFileList, uploadFile, deleteFile } from '@/api/onlyoffice'
import { formatFileSize, formatDate } from '@/utils/format'

const router = useRouter()
const loading = ref(false)
const fileList = ref<FileModel[]>([])
const total = ref(0)
const dateRange = ref<[Dayjs, Dayjs]>()

const queryParams = reactive<FileQueryParams>({
    pageNum: 1,
    pageSize: 10,
    fileName: '',
    fileType: undefined,
    beginTime: '',
    endTime: ''
})

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
        title: '大小',
        dataIndex: 'fileSize',
        key: 'fileSize'
    },
    {
        title: '创建时间',
        dataIndex: 'createTime',
        key: 'createTime'
    },
    {
        title: '操作',
        key: 'action',
        width: 250
    }
]

const loadFiles = async () => {
    loading.value = true
    try {
        const { records, total: totalCount } = await getFileList(queryParams)
        fileList.value = records
        total.value = totalCount
    } catch (error) {
        console.error(error)
    } finally {
        loading.value = false
    }
}

const handleQuery = () => {
    queryParams.pageNum = 1
    loadFiles()
}

const resetQuery = () => {
    queryParams.fileName = ''
    queryParams.fileType = undefined
    queryParams.beginTime = ''
    queryParams.endTime = ''
    dateRange.value = undefined
    handleQuery()
}

const handleDateChange = (dates: [Dayjs, Dayjs]) => {
    if (dates) {
        queryParams.beginTime = dates[0].format('YYYY-MM-DD HH:mm:ss')
        queryParams.endTime = dates[1].format('YYYY-MM-DD HH:mm:ss')
    } else {
        queryParams.beginTime = ''
        queryParams.endTime = ''
    }
}

const handleTableChange = (page: number, pageSize: number) => {
    queryParams.pageNum = page
    queryParams.pageSize = pageSize
    loadFiles()
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
            key: record.fileKey
        }
    })
}

const handleDownload = (record: FileModel) => {
    window.location.href = `/onlyoffice/download/${record.fileKey}?fileName=${record.fileName}`
}

const handleDelete = async (record: FileModel) => {
    try {
        await deleteFile(record.fileKey)
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

<style lang="scss" scoped>
.file-list {
    .search-form {
        margin-bottom: 16px;
        padding: 24px;
        background: #fff;
        border-radius: 2px;
    }

    .operation-bar {
        margin-bottom: 16px;
    }
}
</style> 