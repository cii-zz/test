<template>
  <div class="label-list-container">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>标签列表</span>
          <el-button type="primary">
            <el-icon><Plus /></el-icon>
            新建标签
          </el-button>
        </div>
      </template>
      <el-empty description="功能开发中..." />
    </el-card>
  </div>
</template>

<script setup lang="ts">
</script>

<style lang="scss" scoped>
.label-list-container {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
</style>