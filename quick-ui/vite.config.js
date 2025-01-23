import {defineConfig, loadEnv} from 'vite'
import path from 'path'
import createVitePlugins from './vite/plugins'
import {viteMockServe} from 'vite-plugin-mock'

// https://vitejs.dev/config/
export default defineConfig(({mode, command}) => {
    const env = loadEnv(mode, process.cwd())
    const {VITE_APP_ENV} = env
    return {
        // 部署生产环境和开发环境下的URL。
        // 默认情况下，vite 会假设你的应用是被部署在一个域名的根路径上
        // 例如 https://www.ruoyi.vip/。如果应用被部署在一个子路径上，你就需要用这个选项指定这个子路径。例如，如果你的应用被部署在 https://www.ruoyi.vip/admin/，则设置 baseUrl 为 /admin/。
        base: VITE_APP_ENV === 'production' ? '/' : '/',
        plugins: [
            createVitePlugins(env, command === 'build'),
            viteMockServe({
                mockPath: 'mock', // 指定存放 mock 文件的目录
                // localEnabled: process.env.NODE_ENV === 'development', // 只在开发环境中启用本地 mock
                prodEnabled: false, // 禁用生产环境下的 mock（可选）
                supportTs: true, // 如果你的 mock 文件是 TypeScript 编写的，请开启此选项
                watchFiles: true,// 监听 mock 文件的变化并热更新（可选）
                enable: true, // 是否启用
                logger: true, // 日志,

            })
        ],
        resolve: {
            // https://cn.vitejs.dev/config/#resolve-alias
            alias: {
                // 设置路径
                '~': path.resolve(__dirname, './'),
                // 设置别名
                '@': path.resolve(__dirname, './src')
            },
            // https://cn.vitejs.dev/config/#resolve-extensions
            extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.vue']
        },
        // vite 相关配置
        server: {
            port: 8800,
            host: true,
            open: true,
            proxy: {
                '/dev-api/mockapi': {
                    target: 'http://localhost:8800',
                    // target: 'http://localhost:8080',
                    // target: 'https://vue.ruoyi.vip/prod-api',
                    changeOrigin: true,
                    rewrite: (p) => p.replace(/^\/dev-api/, '')
                },
                // https://cn.vitejs.dev/config/#server-proxy
                '/dev-api': {
                    target: 'http://localhost:12000',
                    // target: 'http://localhost:8080',
                    // target: 'https://vue.ruoyi.vip/prod-api',
                    changeOrigin: true,
                    rewrite: (p) => p.replace(/^\/dev-api/, '')
                }
            }
        },
        //fix:error:stdin>:7356:1: warning: "@charset" must be the first rule in the file
        css: {
            postcss: {
                plugins: [
                    {
                        postcssPlugin: 'internal:charset-removal',
                        AtRule: {
                            charset: (atRule) => {
                                if (atRule.name === 'charset') {
                                    atRule.remove();
                                }
                            }
                        }
                    }
                ]
            }
        }
    }
})
