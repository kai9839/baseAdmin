import request from '@/utils/request'
import type { FileModel } from '@/types/file'

export interface FileQueryParams {
    fileName?: string;
    fileType?: string;
    beginTime?: string;
    endTime?: string;
    pageNum?: number;
    pageSize?: number;
}

export interface PageResult<T> {
    records: T[];
    total: number;
}

export function getFileList(params: FileQueryParams) {
    return request<PageResult<FileModel>>({
        url: '/onlyoffice/page',
        method: 'get',
        params
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