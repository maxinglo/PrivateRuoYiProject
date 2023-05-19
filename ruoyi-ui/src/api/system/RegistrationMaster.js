import request from '@/utils/request'

// 查询报名主表列表
export function listRegistrationMaster(query) {
  return request({
    url: '/system/RegistrationMaster/list',
    method: 'get',
    params: query
  })
}

// 查询报名主表详细
export function getRegistrationMaster(id) {
  return request({
    url: '/system/RegistrationMaster/' + id,
    method: 'get'
  })
}

// 新增报名主表
export function addRegistrationMaster(data) {
  return request({
    url: '/system/RegistrationMaster',
    method: 'post',
    data: data
  })
}

// 修改报名主表
export function updateRegistrationMaster(data) {
  return request({
    url: '/system/RegistrationMaster',
    method: 'put',
    data: data
  })
}

// 删除报名主表
export function delRegistrationMaster(id) {
  return request({
    url: '/system/RegistrationMaster/' + id,
    method: 'delete'
  })
}
