<template>
  <div class="datasource-container">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>数据源列表</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon>
            新建数据源
          </el-button>
        </div>
      </template>

      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="数据源名称" width="200" />
        <el-table-column prop="type" label="类型" width="120">
          <template #default="{ row }">
            <el-tag :type="getTypeTagType(row.type)">
              {{ getTypeName(row.type) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="host" label="主机地址" width="150" />
        <el-table-column prop="port" label="端口" width="80" />
        <el-table-column prop="database" label="数据库" width="150" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'">
              {{ row.status === 1 ? '已启用' : '未启用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" show-overflow-tooltip />
        <el-table-column label="操作" width="300" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" size="small" @click="handleTest(row)">
              测试连接
            </el-button>
            <el-button
              link
              :type="row.status === 1 ? 'warning' : 'success'"
              size="small"
              @click="handleToggleStatus(row)"
            >
              {{ row.status === 1 ? '禁用' : '启用' }}
            </el-button>
            <el-button link type="primary" size="small" @click="handleEdit(row)">
              编辑
            </el-button>
            <el-button link type="danger" size="small" @click="handleDelete(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="queryParams.pageNum"
        v-model:page-size="queryParams.pageSize"
        :total="total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="fetchData"
        @current-change="fetchData"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </el-card>

    <!-- 新建/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
      @close="handleDialogClose"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
      >
        <el-form-item label="数据源名称" prop="name">
          <el-input v-model="formData.name" placeholder="请输入数据源名称" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="formData.type" placeholder="请选择数据源类型">
            <el-option label="StarRocks" :value="1" />
            <el-option label="MySQL" :value="2" />
            <el-option label="ClickHouse" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="主机地址" prop="host">
          <el-input v-model="formData.host" placeholder="请输入主机地址" />
        </el-form-item>
        <el-form-item label="端口" prop="port">
          <el-input-number v-model="formData.port" :min="1" :max="65535" />
        </el-form-item>
        <el-form-item label="数据库" prop="database">
          <el-input v-model="formData.database" placeholder="请输入数据库名称" />
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="formData.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="formData.password"
            type="password"
            placeholder="请输入密码"
            show-password
          />
        </el-form-item>
        <el-form-item label="连接参数">
          <el-input
            v-model="formData.params"
            placeholder="例如：useSSL=false&serverTimezone=UTC"
          />
        </el-form-item>
        <el-form-item label="描述">
          <el-input
            v-model="formData.description"
            type="textarea"
            :rows="3"
            placeholder="请输入描述信息"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleTestConnection">测试连接</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import {
  getDatasourceList,
  createDatasource,
  updateDatasource,
  deleteDatasource,
  testConnection,
  enableDatasource,
  disableDatasource,
  type Datasource
} from '@/api/datasource'

const tableData = ref<Datasource[]>([])
const total = ref(0)
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10
})

const dialogVisible = ref(false)
const dialogTitle = ref('新建数据源')
const formRef = ref<FormInstance>()
const formData = ref<Datasource>({
  name: '',
  type: 1,
  host: '',
  port: 9030,
  database: '',
  username: '',
  password: '',
  params: 'useSSL=false&serverTimezone=UTC',
  description: ''
})

const formRules: FormRules = {
  name: [{ required: true, message: '请输入数据源名称', trigger: 'blur' }],
  type: [{ required: true, message: '请选择数据源类型', trigger: 'change' }],
  host: [{ required: true, message: '请输入主机地址', trigger: 'blur' }],
  port: [{ required: true, message: '请输入端口', trigger: 'blur' }],
  database: [{ required: true, message: '请输入数据库名称', trigger: 'blur' }],
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

// 获取数据源类型名称
const getTypeName = (type: number) => {
  const typeMap: Record<number, string> = {
    1: 'StarRocks',
    2: 'MySQL',
    3: 'ClickHouse'
  }
  return typeMap[type] || '未知'
}

// 获取类型标签类型
const getTypeTagType = (type: number) => {
  const typeMap: Record<number, any> = {
    1: 'success',
    2: 'primary',
    3: 'warning'
  }
  return typeMap[type] || 'info'
}

// 获取数据
const fetchData = async () => {
  try {
    const res = await getDatasourceList(queryParams)
    tableData.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error('获取数据源列表失败:', error)
  }
}

// 新建数据源
const handleAdd = () => {
  dialogTitle.value = '新建数据源'
  dialogVisible.value = true
}

// 编辑数据源
const handleEdit = (row: Datasource) => {
  dialogTitle.value = '编辑数据源'
  formData.value = { ...row }
  dialogVisible.value = true
}

// 测试连接
const handleTest = async (row: Datasource) => {
  try {
    const res = await testConnection(row)
    if (res.data) {
      ElMessage.success('连接测试成功')
    } else {
      ElMessage.error('连接测试失败')
    }
  } catch (error) {
    console.error('测试连接失败:', error)
  }
}

// 测试连接（表单中）
const handleTestConnection = async () => {
  try {
    await formRef.value?.validate()
    const res = await testConnection(formData.value)
    if (res.data) {
      ElMessage.success('连接测试成功')
    } else {
      ElMessage.error('连接测试失败')
    }
  } catch (error) {
    console.error('测试连接失败:', error)
  }
}

// 切换状态
const handleToggleStatus = async (row: Datasource) => {
  try {
    if (row.status === 1) {
      await disableDatasource(row.id!)
      ElMessage.success('禁用成功')
    } else {
      await enableDatasource(row.id!)
      ElMessage.success('启用成功')
    }
    fetchData()
  } catch (error) {
    console.error('切换状态失败:', error)
  }
}

// 删除数据源
const handleDelete = (row: Datasource) => {
  ElMessageBox.confirm('确定要删除该数据源吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(async () => {
      try {
        await deleteDatasource(row.id!)
        ElMessage.success('删除成功')
        fetchData()
      } catch (error) {
        console.error('删除失败:', error)
      }
    })
    .catch(() => {})
}

// 提交表单
const handleSubmit = async () => {
  try {
    await formRef.value?.validate()
    if (formData.value.id) {
      await updateDatasource(formData.value)
      ElMessage.success('更新成功')
    } else {
      await createDatasource(formData.value)
      ElMessage.success('创建成功')
    }
    dialogVisible.value = false
    fetchData()
  } catch (error) {
    console.error('提交失败:', error)
  }
}

// 关闭对话框
const handleDialogClose = () => {
  formRef.value?.resetFields()
  formData.value = {
    name: '',
    type: 1,
    host: '',
    port: 9030,
    database: '',
    username: '',
    password: '',
    params: 'useSSL=false&serverTimezone=UTC',
    description: ''
  }
}

onMounted(() => {
  fetchData()
})
</script>

<style lang="scss" scoped>
.datasource-container {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
</style>