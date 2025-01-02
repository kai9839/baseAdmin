import request from '@/utils/request'
import type { FileModel, OnlyOfficeConfig } from '@/types/file'

export function getFileList() {
    return request<FileModel[]>({
        url: '/onlyoffice/list',
        method: 'get'
    })
}

export function uploadFile(file: File) {
    const formData = new FormData()
    formData.append('file', file)
    return request<FileModel>({
        url: '/onlyoffice/upload',
        method: 'post',
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}

export function getEditorConfig(params: {
    fileName: string
    fileType: string
    key: string
}) {
    return request<OnlyOfficeConfig>({
        url: '/onlyoffice/config',
        method: 'get',
        params
    })
}

export function deleteFile(key: string) {
    return request<boolean>({
        url: `/onlyoffice/delete/${key}`,
        method: 'delete'
    })
} 