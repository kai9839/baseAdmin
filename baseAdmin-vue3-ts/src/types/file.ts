export interface FileModel {
    fileId?: number;
    fileName: string;
    fileKey: string;
    fileType: string;
    fileSize?: number;
    createBy?: string;
    createTime?: string;
    updateBy?: string;
    updateTime?: string;
    delFlag?: string;
    remark?: string;
}

export interface OnlyOfficeConfig {
    document: {
        fileType: string
        key: string
        title: string
        url: string
        permissions: {
            edit: boolean
            download: boolean
            review: boolean
        }
    }
    documentType: string
    editorConfig: {
        callbackUrl: string
        mode: string
        lang: string
        user: {
            id: string
            name: string
        }
    }
    token: string
} 