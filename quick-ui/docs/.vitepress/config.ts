import { defineConfig } from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
  title: "quick-boot快速开发平台",
  description: "quick-boot快速开发平台",
  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: '首页', link: '/' },
      {
        text: '在线文档',
        items: [
          {
            // 该部分的标题
            text: '前端标签文档',
            link: '/ui/index'
          },
          {
            // 该部分的标题
            text: '后端接口文档',
            link: '/docs/index'
          }
        ]

      },
      {text: '在线体验', link: '/markdown-examples'},
    ],

    // 侧边栏菜单栏设置
    sidebar: {
      // 前端标签
      '/tag/': [
        {
          text: '按钮组件',
          items: [
            {text: 'su-button', link: '/tag/button'},
            {text: 'One', link: '/guide/one'},
            {text: 'Two', link: '/guide/two'}
          ]
        },
        {
          text: '表单组件',
          items: [
            {text: 'su-button', link: '/tag/button'},
            {text: 'One', link: '/guide/one'},
            {text: 'Two', link: '/guide/two'}
          ]
        },
        {
          text: '布局',
          items: [
            {text: 'c7-title标题', link: '/tag/title'},
          ],
        }
      ],
      //  框架介绍
      '/docs/': [
        {
          text: '简介',
          items: [
            {text: '简介', link: '/tag/button'},
            {text: '环境要求', link: '/guide/one'},
            {text: '开发环境搭建', link: '/guide/two'},
            {text: '版本发布', link: '/guide/two'},
            {text: '文件上传', link: '/docs/文件上传'}
          ]
        },
        {
          text: '表单组件',
          items: [
            {text: 'su-button', link: '/tag/button'},
            {text: 'One', link: '/guide/one'},
          ],

        }
      ],
      //  标签介绍
      '/ui/': [
        {
          text: '简介',
          items: [
            {text: '表格Hook', link: '/ui/tableHook'},

          ]
        },
        {
          text: '表单组件',
          items: [
            {text: 'su-button', link: '/tag/button'},
            {text: 'One', link: '/guide/one'},
          ],

        }
      ],
    },

    // 导航栏中展示带有图标的社交帐户链接
    socialLinks: [
      {icon: 'gitee', link: 'https://gitee.com/rainsoil/quick-boot'}
    ],
    // 页脚配置
    footer: {
      message: 'Released under the MIT License.',
      copyright: '© 2024-2025 chu7 ™. All Rights Reserved. \r xxxx'
    },
    // 开启本地搜索
    search: {
      provider: 'local'
    }
  }
})

