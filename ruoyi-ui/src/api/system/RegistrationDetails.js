import request from '@/utils/request'

// 查询报名子表列表
export function listRegistrationDetails(query) {
  return request({
    url: '/system/RegistrationDetails/list',
    method: 'get',
    params: query
  })
}

// 查询报名子表详细
export function getRegistrationDetails(id) {
  return request({
    url: '/system/RegistrationDetails/' + id,
    method: 'get'
  })
}

// 新增报名子表
export function addRegistrationDetails(data) {
  return request({
    url: '/system/RegistrationDetails',
    method: 'post',
    data: data
  })
}

// 修改报名子表
export function updateRegistrationDetails(data) {
  return request({
    url: '/system/RegistrationDetails',
    method: 'put',
    data: data
  })
}

// 删除报名子表
export function delRegistrationDetails(id) {
  return request({
    url: '/system/RegistrationDetails/' + id,
    method: 'delete'
  })
}
