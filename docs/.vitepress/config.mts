import {defineConfig} from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
    title: "quick-boot文档",
    description: "quick-boot 前后端文档",
    themeConfig: {
        // 最后更新时间
        lastUpdated: true,
        // https://vitepress.dev/reference/default-theme-config
        // 导航栏设置
        nav: [
            {text: '首页', link: '/'},
            {
                text: '在线文档',
                items: [
                    {
                        // 该部分的标题
                        text: '前端标签文档',
                        link: '/tag/index'
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
                        {text: 'Two', link: '/guide/two'}
                    ]
                }
            ],
        },

        // 导航栏中展示带有图标的社交帐户链接
        socialLinks: [
            {icon: 'github', link: 'https://github.com/vuejs/vitepress'}
        ],
        // 页脚配置
        footer: {
            message: 'Released under the MIT License.',
            copyright: '© 2024-2024 su60™. All Rights Reserved. \r xxxx'
        },
        // 开启本地搜索
        search: {
            provider: 'local'
        }
    }
})
