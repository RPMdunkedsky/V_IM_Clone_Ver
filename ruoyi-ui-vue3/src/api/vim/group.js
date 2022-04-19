import request from '@/utils/request'

// 查询群列表
export function listGroup(query) {
  return request({
    url: '/vim/group/list',
    method: 'get',
    params: query
  })
}

// 查询群详细
export function getGroup(id) {
  return request({
    url: '/vim/group/' + id,
    method: 'get'
  })
}

// 新增群
export function addGroup(data) {
  return request({
    url: '/vim/group',
    method: 'post',
    data: data
  })
}

// 修改群
export function updateGroup(data) {
  return request({
    url: '/vim/group',
    method: 'put',
    data: data
  })
}

// 删除群
export function delGroup(id) {
  return request({
    url: '/vim/group/' + id,
    method: 'delete'
  })
}
