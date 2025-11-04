<template>
  <div class="layout-container">
    <el-container>
      <el-aside :width="isCollapse ? '64px' : '200px'" class="sidebar-container">
        <div class="logo">
          <img src="@/assets/logo.png" alt="Logo" v-if="!isCollapse">
          <span v-if="!isCollapse">用户画像标签系统</span>
        </div>
        <el-menu
          :default-active="activeMenu"
          :collapse="isCollapse"
          :unique-opened="true"
          router
          class="el-menu-vertical"
        >
          <template v-for="route in routes" :key="route.path">
            <el-sub-menu v-if="route.children && route.children.length > 1" :index="route.path">
              <template #title>
                <el-icon><component :is="route.meta?.icon" /></el-icon>
                <span>{{ route.meta?.title }}</span>
              </template>
              <el-menu-item
                v-for="child in route.children"
                :key="child.path"
                :index="route.path + '/' + child.path"
              >
                <el-icon><component :is="child.meta?.icon" /></el-icon>
                <span>{{ child.meta?.title }}</span>
              </el-menu-item>
            </el-sub-menu>
            <el-menu-item
              v-else-if="route.children && route.children.length === 1"
              :index="route.path + '/' + route.children[0].path"
            >
              <el-icon><component :is="route.children[0].meta?.icon" /></el-icon>
              <span>{{ route.children[0].meta?.title }}</span>
            </el-menu-item>
          </template>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header class="header-container">
          <div class="header-left">
            <el-icon class="collapse-icon" @click="toggleCollapse">
              <Expand v-if="isCollapse" />
              <Fold v-else />
            </el-icon>
          </div>
          <div class="header-right">
            <el-dropdown>
              <span class="user-info">
                <el-icon><User /></el-icon>
                <span>管理员</span>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>个人信息</el-dropdown-item>
                  <el-dropdown-item divided>退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-header>

        <el-main class="main-container">
          <router-view v-slot="{ Component }">
            <transition name="fade-transform" mode="out-in">
              <component :is="Component" />
            </transition>
          </router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const isCollapse = ref(false)

const activeMenu = computed(() => {
  return route.path
})

const routes = computed(() => {
  return router.options.routes.filter(r => r.meta?.title)
})

const toggleCollapse = () => {
  isCollapse.value = !isCollapse.value
}
</script>

<style lang="scss" scoped>
.layout-container {
  width: 100%;
  height: 100vh;
  
  .el-container {
    height: 100%;
  }

  .sidebar-container {
    background-color: #304156;
    transition: width 0.3s;
    
    .logo {
      height: 60px;
      display: flex;
      align-items: center;
      justify-content: center;
      color: #fff;
      font-size: 18px;
      font-weight: bold;
      border-bottom: 1px solid rgba(255, 255, 255, 0.1);
      
      img {
        width: 32px;
        height: 32px;
        margin-right: 10px;
      }
    }

    .el-menu-vertical {
      border-right: none;
      background-color: #304156;
    }

    :deep(.el-menu) {
      background-color: #304156;
      
      .el-menu-item,
      .el-sub-menu__title {
        color: #bfcbd9;
        
        &:hover {
          background-color: rgba(0, 0, 0, 0.1);
          color: #fff;
        }
      }
      
      .el-menu-item.is-active {
        background-color: #409eff !important;
        color: #fff !important;
      }
    }
  }

  .header-container {
    background-color: #fff;
    box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 20px;

    .header-left {
      .collapse-icon {
        font-size: 20px;
        cursor: pointer;
        
        &:hover {
          color: #409eff;
        }
      }
    }

    .header-right {
      .user-info {
        display: flex;
        align-items: center;
        cursor: pointer;
        
        .el-icon {
          margin-right: 5px;
        }
      }
    }
  }

  .main-container {
    background-color: #f0f2f5;
    padding: 20px;
  }
}

.fade-transform-leave-active,
.fade-transform-enter-active {
  transition: all 0.3s;
}

.fade-transform-enter-from {
  opacity: 0;
  transform: translateX(-30px);
}

.fade-transform-leave-to {
  opacity: 0;
  transform: translateX(30px);
}
</style>