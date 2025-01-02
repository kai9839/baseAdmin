export interface FileModel {
    fileName: string
    fileType: string
    key: string
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