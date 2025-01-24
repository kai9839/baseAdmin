import dayjs from 'dayjs'

export function formatFileSize(size: number): string {
    if (!size) return '0 B'
    
    const units = ['B', 'KB', 'MB', 'GB', 'TB']
    let index = 0
    while (size >= 1024 && index < units.length - 1) {
        size /= 1024
        index++
    }
    return `${size.toFixed(2)} ${units[index]}`
}

export function formatDate(date: string | null): string {
    if (!date) return '-'
    return dayjs(date).format('YYYY-MM-DD HH:mm:ss')
} 