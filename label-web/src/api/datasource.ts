import request from '@/utils/request'

export interface Datasource {
  id?: number
  name: string
  type: number
  host: string
  port: number
  database: string
  username: string
  password: string
  params?: string
  status?: number
  description?: string
  createTime?: string
  updateTime?: string
}

export interface DatasourceQuery {
  pageNum: number
  pageSize: number
}

// 获取数据源列表
export const getDatasourceList = (params: DatasourceQuery) => {
  return request({
    url: '/datasource/list',
    method: 'get',
    params
  })
}

// 获取数据源详情
export const getDatasource = (id: number) => {
  return request({
    url: `/datasource/${id}`,
    method: 'get'
  })
}

// 创建数据源
export const createDatasource = (data: Datasource) => {
  return request({
    url: '/datasource',
    method: 'post',
    data
  })
}

// 更新数据源
export const updateDatasource = (data: Datasource) => {
  return request({
    url: '/datasource',
    method: 'put',
    data
  })
}

// 删除数据源
export const deleteDatasource = (id: number) => {
  return request({
    url: `/datasource/${id}`,
    method: 'delete'
  })
}

// 测试数据源连接
export const testConnection = (data: Datasource) => {
  return request({
    url: '/datasource/test',
    method: 'post',
    data
  })
}

// 启用数据源
export const enableDatasource = (id: number) => {
  return request({
    url: `/datasource/${id}/enable`,
    method: 'post'
  })
}

// 禁用数据源
export const disableDatasource = (id: number) => {
  return request({
    url: `/datasource/${id}/disable`,
    method: 'post'
  })
}

// 获取数据源的表列表
export const getTables = (datasourceId: number) => {
  return request({
    url: `/datasource/${datasourceId}/tables`,
    method: 'get'
  })
}

// 获取表的字段列表
export const getTableColumns = (datasourceId: number, tableName: string) => {
  return request({
    url: `/datasource/${datasourceId}/tables/${tableName}/columns`,
    method: 'get'
  })
}

// 预览表数据
export const previewTableData = (datasourceId: number, tableName: string, limit: number = 100) => {
  return request({
    url: `/datasource/${datasourceId}/tables/${tableName}/preview`,
    method: 'get',
    params: { limit }
  })
}

// 执行SQL查询
export const executeQuery = (datasourceId: number, sql: string) => {
  return request({
    url: `/datasource/${datasourceId}/query`,
    method: 'post',
    data: { sql }
  })
}