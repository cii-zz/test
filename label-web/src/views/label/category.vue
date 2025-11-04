<template>
  <div class="label-category-container">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>标签分类</span>
          <el-button type="primary">
            <el-icon><Plus /></el-icon>
            新建分类
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
.label-category-container {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
</style>