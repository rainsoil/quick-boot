import {defineConfig} from 'vitepress'
import {demoblockPlugin, demoblockVitePlugin} from 'vitepress-theme-demoblock'
import path from 'path'
import vueJsx from '@vitejs/plugin-vue-jsx'
import nav from './configs/nav'
import sidebar from './configs/sidebar'
// https://vitepress.dev/reference/site-config
export default defineConfig({
    title: "quick-boot快速开发平台",
    description: "quick-boot快速开发平台",
    lastUpdated: true,
    cleanUrls: true,
    markdown: {
        theme: { light: 'github-light', dark: 'github-dark' },

        config: (md) => {
            md.use(demoblockPlugin, {
                customClass: 'demoblock-custom'
            })
        }
    },
    vite: {
        plugins: [demoblockVitePlugin(), vueJsx()],
        resolve: {
            alias: {

                // 设置路径
                '~': path.resolve(__dirname, './'),
                // 设置别名
                '@': path.resolve(__dirname, './src')
            }
        }
    },

    themeConfig: {
        // https://vitepress.dev/reference/default-theme-config
        nav,
        sidebar,
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
        },
        editLink: {
            pattern: 'https://github.com/xinlei3166/vitepress-demo/edit/master/docs/:path',
            text: '在 GitHub 上编辑此页'
        },
        outlineTitle: '本页目录',
        lastUpdatedText: '上次更新',
        logo: '/logo.svg',
    },
})

