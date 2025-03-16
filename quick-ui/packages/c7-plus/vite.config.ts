import {defineConfig} from 'vite'
import path from 'path';

import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import {resolve} from 'path'
// import AutoImport from 'unplugin-auto-import/vite' // 自动导入
import viteCompression from 'vite-plugin-compression' // 静态资源压缩
// import {visualizer} from 'rollup-plugin-visualizer' // 打包后的视图文件

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        vue(),

        vueJsx(),

        viteCompression({
            verbose: true,
            disable: false, // 不禁用压缩
            deleteOriginFile: false, // 压缩后是否删除原文件
            threshold: 10240, // 压缩前最小文件大小
            algorithm: 'gzip', // 压缩算法
            ext: '.gz', // 文件类型
        }),
        // visualizer({
        //   open:true,  //注意这里要设置为true，否则无效
        //   gzipSize:true,
        //   brotliSize:true
        // })
    ],

    resolve: {
        // 配置别名
        alias: {
            // '@': resolve(__dirname, 'examples'),
            '@': resolve(__dirname, 'packages'),
        },
        // 类型： string[] 导入时想要省略的扩展名列表。
        extensions: ['.js', '.ts', '.jsx', '.tsx', '.json', '.vue', '.mjs'],
    },
    // vite.config.js
    build: {
        outDir: 'lib',
        rollupOptions: {
            external: ['vue'],
            input: {
                index: path.resolve(__dirname, 'index.js'),
                'hooks/dictHook': path.resolve(__dirname, 'hooks/dictHook.ts'),
                'hooks/tableView': path.resolve(__dirname, 'hooks/tableView.ts')
            },
            output: [
                {
                    format: 'es',
                    entryFileNames: '[name].js',
                    dir: 'dist/es', // ES 模块输出到 dist/es 目录
                    preserveModules: true // 保留文件结构
                },
                {
                    format: 'cjs',
                    entryFileNames: '[name].js',
                    dir: 'dist/cjs', // CJS 模块输出到 dist/cjs 目录
                    preserveModules: true
                }
            ]
        },
        lib: {
            entry: './index.js',
            name: 'c7-plus',
            fileName: (format) => `c7-plus.${format}.js` // 主入口文件名称
        }
    }
})
