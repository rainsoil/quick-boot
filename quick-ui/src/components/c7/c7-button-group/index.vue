
<template>
  <div class="c7-button-group" :class="groupClasses" :style="groupStyle">
    <!-- 显示可见的按钮 -->
    <template v-for="(button, index) in visibleButtons" :key="`visible-${index}`">
      <component :is="button" :size="size" />
    </template>
    
    <!-- 更多按钮下拉菜单 -->
    <el-dropdown 
      v-if="hiddenButtons.length > 0" 
      :trigger="triggerType"
      @command="handleDropdownCommand"
      placement="bottom-end"
    >
      <c7-button 
        type="info" 
        :size="size"
        :plain="true"
        icon="MoreFilled"
        class="c7-button-group__more"
      >
        更多
      </c7-button>
      <template #dropdown>
        <el-dropdown-menu>
          <template v-for="(button, index) in hiddenButtons" :key="`hidden-${index}`">
            <el-dropdown-item 
              :command="`button-${index}`"
              @click="triggerButtonAction(button)"
            >
              <component :is="button" :size="size" />
            </el-dropdown-item>
          </template>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<script setup>
import { computed, defineOptions, useSlots, nextTick } from 'vue'
import C7Button from '../c7-button/index.vue'
import { ElDropdown, ElDropdownMenu, ElDropdownItem } from 'element-plus'

defineOptions({
  name: 'C7ButtonGroup'
})

const props = defineProps({
  // 布局模式
  mode: {
    type: String,
    default: 'auto', // auto, inline, dropdown
    validator: (value) => ['auto', 'inline', 'dropdown'].includes(value)
  },
  
  // 最大显示按钮数量（auto 模式下）
  maxVisible: {
    type: Number,
    default: 2
  },
  
  // 按钮间距
  spacing: {
    type: [String, Number],
    default: 'loose', // 'tight'(0px), 'normal'(1px), 'loose'(2px) 或具体数值(px)
    validator: (value) => {
      if (typeof value === 'number') return value >= 0
      return ['tight', 'normal', 'loose'].includes(value)
    }
  },
  
  // 按钮大小
  size: {
    type: String,
    default: 'small',
    validator: (value) => ['large', 'default', 'small'].includes(value)
  },
  
  // 是否响应式
  responsive: {
    type: Boolean,
    default: true
  },
  
  // 触发方式：click 或 hover
  trigger: {
    type: String,
    default: 'click',
    validator: (value) => ['click', 'hover'].includes(value)
  },
  
  // 断点配置
  breakpoints: {
    type: Object,
    default: () => ({
      xs: 1,
      sm: 2,
      md: 3,
      lg: 4
    })
  }
})

const slots = useSlots()

// 获取所有按钮组件
const allButtons = computed(() => {
  if (!slots.default) return []
  return slots.default().filter(vnode => 
    vnode.type && 
    (vnode.type.__name === 'C7Button' || vnode.type.name === 'C7Button')
  )
})

// 可见按钮
const visibleButtons = computed(() => {
  if (props.mode === 'inline' || allButtons.value.length <= props.maxVisible) {
    return allButtons.value
  }
  return allButtons.value.slice(0, props.maxVisible)
})

// 隐藏按钮
const hiddenButtons = computed(() => {
  if (props.mode === 'inline' || allButtons.value.length <= props.maxVisible) {
    return []
  }
  return allButtons.value.slice(props.maxVisible)
})

// 触发类型
const triggerType = computed(() => {
  return props.trigger
})

// 计算实际间距值
const actualSpacing = computed(() => {
  if (typeof props.spacing === 'number') {
    return `${props.spacing}px`
  }
  
  // 预设间距值
  const spacingMap = {
    tight: '0px',
    normal: '1px', 
    loose: '2px'
  }
  
  return spacingMap[props.spacing] || '0px'
})

// 处理下拉菜单命令
const handleDropdownCommand = (command) => {
  // 这个函数主要用于处理下拉菜单的命令，实际按钮点击通过 triggerButtonAction 处理
  console.log('Dropdown command:', command)
}

// 触发按钮动作
const triggerButtonAction = (buttonVnode) => {
  // 手动触发按钮的点击事件
  if (buttonVnode && buttonVnode.props && buttonVnode.props.onClick) {
    buttonVnode.props.onClick()
  }
}

const groupClasses = computed(() => {
  return [
    `c7-button-group--${props.mode}`,
    `c7-button-group--size-${props.size}`,
    {
      'c7-button-group--responsive': props.responsive
    }
  ]
})

// 动态样式
const groupStyle = computed(() => {
  return {
    gap: actualSpacing.value
  }
})
</script>

<style scoped>
.c7-button-group {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  /* gap 通过 :style 动态设置 */
}

/* 大小配置 */
.c7-button-group--size-small {
  --button-size: small;
}

.c7-button-group--size-default {
  --button-size: default;
}

.c7-button-group--size-large {
  --button-size: large;
}

/* 更多按钮样式 */
.c7-button-group__more {
  position: relative;
}

.c7-button-group__more:hover {
  background-color: var(--el-color-info-light-9);
  border-color: var(--el-color-info-light-7);
}

/* 下拉菜单项样式 */
:deep(.el-dropdown-menu__item) {
  padding: 8px 16px;
  display: flex;
  align-items: center;
  justify-content: center;
}

:deep(.el-dropdown-menu__item:hover) {
  background-color: var(--el-color-primary-light-9);
}

/* 响应式配置 */
@media (max-width: 768px) {
  .c7-button-group--responsive {
    flex-direction: column;
    align-items: stretch;
  }
  
  .c7-button-group--responsive .c7-button {
    width: 100%;
  }
  
  .c7-button-group--responsive .c7-button-group__more {
    width: 100%;
  }
}

/* 内联模式 */
.c7-button-group--inline {
  display: flex;
  align-items: center;
  /* gap 通过 :style 动态设置 */
}

/* 下拉模式 */
.c7-button-group--dropdown {
  position: relative;
}

/* 自动模式 */
.c7-button-group--auto {
  display: flex;
  align-items: center;
  /* gap 通过 :style 动态设置 */
}

/* 按钮组内按钮样式统一 */
.c7-button-group :deep(.c7-button) {
  margin: 0 !important;
}

.c7-button-group :deep(.el-button) {
  margin: 0 !important;
  margin-left: 0 !important;
  margin-right: 0 !important;
}

.c7-button-group :deep(.el-button + .el-button) {
  margin-left: 0 !important;
}

.c7-button-group :deep(.c7-button + .c7-button) {
  margin-left: 0 !important;
}

/* 下拉菜单样式优化 */
:deep(.el-dropdown-menu) {
  min-width: 120px;
  max-width: 200px;
}

:deep(.el-dropdown-menu__item) {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
