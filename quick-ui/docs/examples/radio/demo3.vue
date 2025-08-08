<template>
  <div>
    <h2>复杂单选功能示例</h2>
    
    <div class="demo-section">
      <h3>多级联动</h3>
      <div class="level-container">
        <div class="level-item">
          <label>省份：</label>
          <c7-radio :data-list="provinceOptions" v-model="selectedProvince" @change="handleProvinceChange" />
        </div>
        <div class="level-item">
          <label>城市：</label>
          <c7-radio :data-list="cityOptions" v-model="selectedCity" @change="handleCityChange" />
        </div>
        <div class="level-item">
          <label>区县：</label>
          <c7-radio :data-list="districtOptions" v-model="selectedDistrict" @change="handleDistrictChange" />
        </div>
      </div>
      <div class="result">选中的地址：{{ selectedAddress }}</div>
    </div>

    <div class="demo-section">
      <h3>数据统计</h3>
      <c7-radio :data-list="statOptions" v-model="statValue" @change="handleStatChange" />
      <div class="stat-container">
        <div class="stat-item">
          <span class="stat-label">总选项数：</span>
          <span class="stat-value">{{ statOptions.length }}</span>
        </div>
        <div class="stat-item">
          <span class="stat-label">已选择：</span>
          <span class="stat-value">{{ statValue || '无' }}</span>
        </div>
        <div class="stat-item">
          <span class="stat-label">选择率：</span>
          <span class="stat-value">{{ selectionRate }}%</span>
        </div>
      </div>
    </div>

    <div class="demo-section">
      <h3>实时搜索</h3>
      <el-input v-model="searchText" placeholder="输入搜索关键词" style="width: 300px; margin-bottom: 10px;" />
      <c7-radio :data-list="searchResults" v-model="searchValue" @change="handleSearchChange" />
      <div class="result">选中的值：{{ searchValue }}</div>
      <div class="search-info">搜索结果：{{ searchResults.length }} 项</div>
    </div>

    <div class="demo-section">
      <h3>数据分组</h3>
      <div class="group-container">
        <div v-for="group in groupedOptions" :key="group.category" class="group-item">
          <h4>{{ group.category }}</h4>
          <c7-radio :data-list="group.options" v-model="groupValue" @change="handleGroupChange" />
        </div>
      </div>
      <div class="result">选中的值：{{ groupValue }}</div>
    </div>

    <div class="demo-section">
      <h3>自定义验证</h3>
      <c7-radio :data-list="validationOptions" v-model="validationValue" @change="handleValidationChange" />
      <div class="validation-result" :class="{ 'valid': isValid, 'invalid': !isValid }">
        {{ validationMessage }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { c7Radio } from 'c7-plus'

// 多级联动
const selectedProvince = ref('')
const selectedCity = ref('')
const selectedDistrict = ref('')

const provinceOptions = ref([
  { label: '北京市', value: 'beijing' },
  { label: '上海市', value: 'shanghai' },
  { label: '广东省', value: 'guangdong' }
])

const cityOptions = ref([])
const districtOptions = ref([])

const cityData = {
  beijing: [
    { label: '东城区', value: 'dongcheng' },
    { label: '西城区', value: 'xicheng' },
    { label: '朝阳区', value: 'chaoyang' }
  ],
  shanghai: [
    { label: '黄浦区', value: 'huangpu' },
    { label: '徐汇区', value: 'xuhui' },
    { label: '长宁区', value: 'changning' }
  ],
  guangdong: [
    { label: '广州市', value: 'guangzhou' },
    { label: '深圳市', value: 'shenzhen' },
    { label: '珠海市', value: 'zhuhai' }
  ]
}

const districtData = {
  dongcheng: [
    { label: '东华门街道', value: 'donghuamen' },
    { label: '景山街道', value: 'jingshan' }
  ],
  xicheng: [
    { label: '西长安街街道', value: 'xichangan' },
    { label: '新街口街道', value: 'xinjiekou' }
  ],
  chaoyang: [
    { label: '建外街道', value: 'jianwai' },
    { label: '朝外街道', value: 'chaowai' }
  ]
}

const selectedAddress = computed(() => {
  const province = provinceOptions.value.find(p => p.value === selectedProvince.value)
  const city = cityOptions.value.find(c => c.value === selectedCity.value)
  const district = districtOptions.value.find(d => d.value === selectedDistrict.value)
  
  return [province?.label, city?.label, district?.label].filter(Boolean).join(' - ')
})

const handleProvinceChange = (value) => {
  selectedCity.value = ''
  selectedDistrict.value = ''
  cityOptions.value = cityData[value] || []
  districtOptions.value = []
  console.log('省份变化：', value)
}

const handleCityChange = (value) => {
  selectedDistrict.value = ''
  districtOptions.value = districtData[value] || []
  console.log('城市变化：', value)
}

const handleDistrictChange = (value) => {
  console.log('区县变化：', value)
}

// 数据统计
const statOptions = ref([
  { label: '选项A', value: 'option_a' },
  { label: '选项B', value: 'option_b' },
  { label: '选项C', value: 'option_c' },
  { label: '选项D', value: 'option_d' },
  { label: '选项E', value: 'option_e' }
])

const statValue = ref('')
const selectionRate = computed(() => {
  return statValue.value ? 100 : 0
})

const handleStatChange = (value) => {
  console.log('统计选项变化：', value)
}

// 实时搜索
const searchText = ref('')
const searchValue = ref('')

const allSearchOptions = ref([
  { label: '苹果手机', value: 'iphone' },
  { label: '苹果电脑', value: 'macbook' },
  { label: '苹果平板', value: 'ipad' },
  { label: '苹果手表', value: 'apple_watch' },
  { label: '苹果耳机', value: 'airpods' },
  { label: '三星手机', value: 'samsung' },
  { label: '华为手机', value: 'huawei' },
  { label: '小米手机', value: 'xiaomi' }
])

const searchResults = computed(() => {
  if (!searchText.value) {
    return allSearchOptions.value
  }
  return allSearchOptions.value.filter(option => 
    option.label.toLowerCase().includes(searchText.value.toLowerCase())
  )
})

const handleSearchChange = (value) => {
  console.log('搜索选项变化：', value)
}

// 数据分组
const groupValue = ref('')

const groupedOptions = ref([
  {
    category: '电子产品',
    options: [
      { label: '手机', value: 'phone' },
      { label: '电脑', value: 'computer' },
      { label: '平板', value: 'tablet' }
    ]
  },
  {
    category: '服装',
    options: [
      { label: '上衣', value: 'shirt' },
      { label: '裤子', value: 'pants' },
      { label: '鞋子', value: 'shoes' }
    ]
  },
  {
    category: '食品',
    options: [
      { label: '水果', value: 'fruit' },
      { label: '蔬菜', value: 'vegetable' },
      { label: '肉类', value: 'meat' }
    ]
  }
])

const handleGroupChange = (value) => {
  console.log('分组选项变化：', value)
}

// 自定义验证
const validationOptions = ref([
  { label: '选项1', value: 'valid1' },
  { label: '选项2', value: 'valid2' },
  { label: '选项3', value: 'valid3' }
])

const validationValue = ref('')
const isValid = computed(() => {
  return validationValue.value === 'valid2'
})

const validationMessage = computed(() => {
  if (!validationValue.value) {
    return '请选择一个选项'
  }
  return isValid.value ? '验证通过！' : '请选择选项2'
})

const handleValidationChange = (value) => {
  console.log('验证选项变化：', value)
}
</script>

<style scoped>
h2 {
  margin-bottom: 20px;
  color: #333;
}

.demo-section {
  margin-bottom: 30px;
  padding: 20px;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
}

.demo-section h3 {
  margin-bottom: 15px;
  color: #666;
  font-size: 16px;
}

.result {
  margin-top: 10px;
  padding: 8px;
  background-color: #f5f7fa;
  border-radius: 4px;
  color: #606266;
  font-size: 14px;
}

.level-container {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.level-item {
  display: flex;
  align-items: center;
  gap: 10px;
}

.level-item label {
  font-weight: bold;
  min-width: 60px;
}

.stat-container {
  margin-top: 15px;
  display: flex;
  gap: 20px;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 5px;
}

.stat-label {
  font-weight: bold;
  color: #666;
}

.stat-value {
  color: #409eff;
  font-weight: bold;
}

.search-info {
  margin-top: 10px;
  color: #909399;
  font-size: 12px;
}

.group-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.group-item {
  padding: 15px;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  background-color: #fafafa;
}

.group-item h4 {
  margin-bottom: 10px;
  color: #333;
  font-size: 14px;
}

.validation-result {
  margin-top: 10px;
  padding: 8px;
  border-radius: 4px;
  font-weight: bold;
}

.validation-result.valid {
  background-color: #f0f9ff;
  color: #67c23a;
}

.validation-result.invalid {
  background-color: #fef0f0;
  color: #f56c6c;
}
</style> 