import request from '@/utils/request'

export function getFileList(params) {
  return request({
    url: '/onlyoffice/page',
    method: 'get',
    params
  })
}

export function uploadFile(file) {
  const formData = new FormData()
  formData.append('file', file)
  return request({
    url: '/onlyoffice/upload',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

export function getEditorConfig(params) {
  return request({
    url: '/onlyoffice/config',
    method: 'get',
    params
  })
}

export function deleteFile(key) {
  return request({
    url: `/onlyoffice/delete/${key}`,
    method: 'delete'
  })
} 