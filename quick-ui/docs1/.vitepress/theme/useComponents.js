// Don't remove this file, because it registers the demo components.
import Demo from 'vitepress-theme-demoblock/dist/client/components/Demo.vue'
import DemoBlock from 'vitepress-theme-demoblock/dist/client/components/DemoBlock.vue'
import  tableViewDemo from  '../../../src/views/demo/hook/tableViewDemo.vue'
export function useComponents(app) {
  app.component('Demo', Demo)
  app.component('DemoBlock', DemoBlock)

  // 表格 Hook
  app.component('tableViewDemo', tableViewDemo)
}
