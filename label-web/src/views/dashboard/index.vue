<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <el-icon class="stat-icon" color="#409eff"><Connection /></el-icon>
            <div class="stat-info">
              <div class="stat-value">{{ stats.datasourceCount }}</div>
              <div class="stat-label">数据源总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <el-icon class="stat-icon" color="#67c23a"><PriceTag /></el-icon>
            <div class="stat-info">
              <div class="stat-value">{{ stats.labelCount }}</div>
              <div class="stat-label">标签总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <el-icon class="stat-icon" color="#e6a23c"><Timer /></el-icon>
            <div class="stat-info">
              <div class="stat-value">{{ stats.taskCount }}</div>
              <div class="stat-label">计算任务</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <el-icon class="stat-icon" color="#f56c6c"><User /></el-icon>
            <div class="stat-info">
              <div class="stat-value">{{ stats.userCount }}</div>
              <div class="stat-label">用户总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card shadow="never">
          <template #header>
            <span>快捷入口</span>
          </template>
          <div class="quick-actions">
            <el-button type="primary" @click="goTo('/datasource/list')">
              <el-icon><Connection /></el-icon>
              管理数据源
            </el-button>
            <el-button type="success" @click="goTo('/label/list')">
              <el-icon><PriceTag /></el-icon>
              创建标签
            </el-button>
            <el-button type="warning" @click="goTo('/compute/task')">
              <el-icon><Timer /></el-icon>
              查看任务
            </el-button>
            <el-button type="danger" @click="goTo('/user/portrait')">
              <el-icon><Search /></el-icon>
              查询画像
            </el-button>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="never">
          <template #header>
            <span>系统信息</span>
          </template>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="系统名称">用户画像标签系统</el-descriptions-item>
            <el-descriptions-item label="版本号">v1.0.0</el-descriptions-item>
            <el-descriptions-item label="技术栈">Vue 3 + Element Plus + Spring Boot</el-descriptions-item>
            <el-descriptions-item label="数据源支持">StarRocks、MySQL、ClickHouse</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>

    <el-row style="margin-top: 20px">
      <el-col :span="24">
        <el-card shadow="never">
          <template #header>
            <span>最近任务</span>
          </template>
          <el-table :data="recentTasks" stripe>
            <el-table-column prop="id" label="任务ID" width="80" />
            <el-table-column prop="labelName" label="标签名称" width="200" />
            <el-table-column prop="taskType" label="任务类型" width="120">
              <template #default="{ row }">
                <el-tag :type="row.taskType === 1 ? 'primary' : 'success'">
                  {{ row.taskType === 1 ? '全量计算' : '增量计算' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="120">
              <template #default="{ row }">
                <el-tag :type="getStatusType(row.status)">
                  {{ getStatusText(row.status) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="resultCount" label="结果数量" width="120" />
            <el-table-column prop="startTime" label="开始时间" width="180" />
            <el-table-column prop="endTime" label="结束时间" width="180" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const stats = ref({
  datasourceCount: 5,
  labelCount: 128,
  taskCount: 45,
  userCount: 100000
})

const recentTasks = ref([
  {
    id: 1,
    labelName: '用户年龄',
    taskType: 1,
    status: 2,
    resultCount: 10000,
    startTime: '2024-01-10 10:00:00',
    endTime: '2024-01-10 10:05:00'
  },
  {
    id: 2,
    labelName: '用户性别',
    taskType: 2,
    status: 2,
    resultCount: 5000,
    startTime: '2024-01-10 11:00:00',
    endTime: '2024-01-10 11:02:00'
  },
  {
    id: 3,
    labelName: '消费能力',
    taskType: 1,
    status: 1,
    resultCount: 0,
    startTime: '2024-01-10 12:00:00',
    endTime: '-'
  }
])

const getStatusType = (status: number) => {
  const statusMap: Record<number, string> = {
    0: 'info',
    1: 'warning',
    2: 'success',
    3: 'danger'
  }
  return statusMap[status] || 'info'
}

const getStatusText = (status: number) => {
  const statusMap: Record<number, string> = {
    0: '待执行',
    1: '执行中',
    2: '执行成功',
    3: '执行失败'
  }
  return statusMap[status] || '未知'
}

const goTo = (path: string) => {
  router.push(path)
}

onMounted(() => {
  // 可以在这里加载真实数据
})
</script>

<style lang="scss" scoped>
.dashboard-container {
  .stat-card {
    .stat-content {
      display: flex;
      align-items: center;

      .stat-icon {
        font-size: 48px;
        margin-right: 20px;
      }

      .stat-info {
        .stat-value {
          font-size: 32px;
          font-weight: bold;
          color: #333;
        }

        .stat-label {
          font-size: 14px;
          color: #999;
          margin-top: 5px;
        }
      }
    }
  }

  .quick-actions {
    display: flex;
    gap: 10px;
    flex-wrap: wrap;

    .el-button {
      flex: 1;
      min-width: 120px;
    }
  }
}
</style>