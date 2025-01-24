<template>
  <div class="file-list">
    <!-- 搜索表单 -->
    <el-form :inline="true" class="search-form">
      <el-form-item label="文件名">
        <el-input v-model="queryParams.fileName" placeholder="请输入文件名" clearable />
      </el-form-item>
      <el-form-item label="文件类型">
        <el-select v-model="queryParams.fileType" placeholder="请选择文件类型" clearable style="width: 120px">
          <el-option label="Word文档" value="doc" />
          <el-option label="Excel表格" value="xls" />
          <el-option label="PPT演示" value="ppt" />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker v-model="dateRange" type="datetimerange" range-separator="至" start-placeholder="开始日期"
          end-placeholder="结束日期" @change="handleDateChange" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">查询</el-button>
        <el-button @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <div class="operation-bar">
      <el-upload :action="`${baseURL}/onlyoffice/upload`" :before-upload="beforeUpload"
        :on-success="handleUploadSuccess" :on-error="handleUploadError" :show-file-list="false"
        accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx,.pdf">
        <el-button type="primary">
          <i class="el-icon-upload"></i> 上传文件
        </el-button>
      </el-upload>
    </div>
    <!-- 文件列表 -->
    <el-table v-loading="loading" :data="fileList" border style="width: 100%">
      <el-table-column prop="fileName" label="文件名" min-width="200" />
      <el-table-column prop="fileType" label="类型" width="100" />
      <el-table-column prop="fileSize" label="大小" width="100" :formatter="(row) => formatFileSize(row.fileSize)" />
      <el-table-column prop="createTime" label="创建时间" width="180" :formatter="(row) => parseTime(row.createTime)" />
      <el-table-column label="操作" width="200" fixed="right">
        <template slot-scope="scope">
          <el-button size="mini" type="text" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="text" @click="handleDownload(scope.row)">下载</el-button>
          <el-button size="mini" type="text" class="delete-btn" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination :current-page="queryParams.pageNum" :page-size="queryParams.pageSize" :total="total"
      layout="total, prev, pager, next" @current-change="handleCurrentChange" />
    <!-- <el-dialog title="提示" fullscreen :append-to-body="true" :visible.sync="dialogVisible":before-close="handleClose">
      <EditorOffice :options="options" />
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog> -->
    
  </div>
</template>

<script>
import { getFileList, deleteFile } from '@/api/onlyoffice'
import { formatFileSize, parseTime } from '@/utils/format'

export default {
  name: 'FileList',
  components: {
    EditorOffice: () => import('@/views/EditorOffice.vue')
  },
  data() {
    return {
      loading: false,
      baseURL: process.env.VUE_APP_BASE_API,
      queryParams: {
        fileName: '',
        fileType: '',
        beginTime: '',
        endTime: '',
        pageNum: 1,
        pageSize: 10
      },
      dateRange: [],
      fileList: [],
      total: 0,
      options: {},
      dialogVisible: false
    }
  },
  created() {
    this.loadFiles()
  },
  methods: {
    handleClose() {
      this.dialogVisible = false
    },
    // 加载文件列表
    async loadFiles() {
      try {
        this.loading = true
        const res = await getFileList(this.queryParams)
        const { records, total } = res.data
        this.fileList = records
        this.total = total
      } catch (error) {
        console.error(error)
      } finally {
        this.loading = false
      }
    },
    // 查询
    handleQuery() {
      this.queryParams.pageNum = 1
      this.loadFiles()
    },
    // 重置
    resetQuery() {
      this.dateRange = []
      this.queryParams = {
        fileName: '',
        fileType: '',
        beginTime: '',
        endTime: '',
        pageNum: 1,
        pageSize: 10
      }
      this.handleQuery()
    },
    // 日期范围变化
    handleDateChange(dates) {
      if (dates) {
        this.queryParams.beginTime = parseTime(dates[0])
        this.queryParams.endTime = parseTime(dates[1])
      } else {
        this.queryParams.beginTime = ''
        this.queryParams.endTime = ''
      }
    },
    // 页码变化
    handleCurrentChange(val) {
      this.queryParams.pageNum = val
      this.loadFiles()
    },
    // 上传前校验
    beforeUpload(file) {
      const isLt100M = file.size / 1024 / 1024 < 100
      if (!isLt100M) {
        this.$message.error('文件大小不能超过 100MB!')
        return false
      }
      return true
    },
    // 上传成功
    handleUploadSuccess(res) {
      if (res.code === 200) {
        this.$message.success('上传成功')
        this.loadFiles()
      } else {
        this.$message.error(res.msg || '上传失败')
      }
    },
    // 上传失败
    handleUploadError() {
      this.$message.error('上传失败')
    },
    // 编辑文件
    handleEdit(row) {
      
      // this.options = {
      //   fileName: row.fileName,
      //   fileType: row.fileType,
      //   key: row.fileKey
      // }
      // this.dialogVisible = true
      this.$router.push({
        name: 'Editor',
        query: {
          fileName: row.fileName,
          fileType: row.fileType,
          key: row.fileKey
        }
      })
    },
    // 下载文件
    handleDownload(row) {
      window.location.href = `${this.baseURL}/onlyoffice/download/${row.fileKey}?fileName=${row.fileName}`
    },
    // 删除文件
    async handleDelete(row) {
      try {
        await this.$confirm('确认要删除该文件吗?', '提示', {
          type: 'warning'
        })
        await deleteFile(row.fileKey)
        this.$message.success('删除成功')
        this.loadFiles()
      } catch (error) {
        console.error(error)
      }
    },
    formatFileSize,
    parseTime
  }
}
</script>

<style lang="scss" scoped>
.file-list {
  .search-form {
    margin-bottom: 16px;
    padding: 16px;
    background: #fff;
    border-radius: 4px;
  }

  .operation-bar {
    margin-bottom: 16px;
  }

  .delete-btn {
    color: #f56c6c;
  }

  .el-pagination {
    margin-top: 20px;
    text-align: right;
  }
}
</style>