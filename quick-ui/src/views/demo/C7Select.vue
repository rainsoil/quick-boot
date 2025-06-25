<template>
  <el-select
      v-bind="$attrs"
      v-model="selected"
      :loading="loading"
      filterable
      @focus="handleLoad"
      @clear="clearOptions"
      @visible-change="visibleChange"
      @change="onChange"
  >
    <el-option
        v-for="item in options"
        :key="item[valueKey]"
        :label="item[labelKey]"
        :value="item[valueKey]"
    />
  </el-select>
</template>

<script>
export default {
  name: 'ElSelectAsync',
  inheritAttrs: false,
  props: {
    modelValue: {
      type: [String, Number, Array],
      default: ''
    },
    fetchData: {
      type: Function, // 修改为 Function
      required: true
    },
    // 新增：数据处理回调函数
    dataFormatter: {
      type: Function,
      default: null
    },
    labelKey: {
      type: String,
      default: 'label'
    },
    valueKey: {
      type: String,
      default: 'value'
    },
    autoLoad: {
      type: Boolean,
      default: true
    },
    fetchParams: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      options: [],
      loading: false
    };
  },
  computed: {
    selected: {
      get() {
        return this.modelValue;
      },
      set(val) {
        this.$emit('update:modelValue', val);
      }
    }
  },
  watch: {
    fetchParams: {
      handler() {
        this.handleLoad();
      },
      deep: true
    }
  },
  mounted() {
    if (this.autoLoad) {
      this.handleLoad();
    }
  },
  methods: {
    async handleLoad(query = '') {
      console.log(this.fetchData)
      this.loading = true;
      try {
        const params = {...this.fetchParams, query};
        this.fetchData(params).then(res => {
          let list = res.data;
          // 如果传入了 dataFormatter，则对数据进行处理
          if (this.dataFormatter && typeof this.dataFormatter === 'function') {
            list = this.dataFormatter(list);
          }
          this.options = Array.isArray(list) ? list : [];
        });
      } catch (err) {
        console.error('ElSelectAsync load error:', err);
      } finally {
        this.loading = false;
      }
    },
    clearOptions() {
      this.options = [];
    },
    visibleChange(open) {
      if (open && !this.options.length) {
        this.handleLoad();
      }
    },
    onChange(val) {
      this.$emit('change', val);
    }
  }
};
</script>
