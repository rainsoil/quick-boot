import { defineConfig, normalizePath } from "vite";
import vue from "@vitejs/plugin-vue";
import vueJsx from "@vitejs/plugin-vue-jsx";
import dts from "vite-plugin-dts";
import AutoImport from "unplugin-auto-import/vite";
import Components from "unplugin-vue-components/vite";
import { ElementPlusResolver } from "unplugin-vue-components/resolvers";
import { format, resolve } from "path";
import { viteStaticCopy } from 'vite-plugin-static-copy'
const projRoot = resolve(__dirname, '..', '..')
export default defineConfig({
  plugins: [
    vue(),
    vueJsx(),
    dts({
      outDir: resolve(projRoot, 'dist/types'),
      staticImport: true,
      insertTypesEntry: true
    }),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
    viteStaticCopy({
      targets: [
        { src: normalizePath(resolve(projRoot, 'packages', 'c7-plus', 'package.json')), dest: normalizePath(resolve(projRoot, 'dist')) },
        { src: normalizePath(resolve(projRoot, 'README.md')), dest: normalizePath(resolve(projRoot, 'dist')) },
        { src: normalizePath(resolve(projRoot, 'LICENSE')), dest: normalizePath(resolve(projRoot, 'dist')) }
      ],
    })
  ],
  build: {
    outDir: resolve(__dirname, "dist/"),
    emptyOutDir: true,
    target: "modules",
    minify: true,
    // cssCodeSplit: true,
    rollupOptions: {
      // 忽略vue文件
      external: ['vue', 'element-plus', 'lodash-es'],
      // input: ["index.ts"],
      output: {
        globals: {
          vue: 'Vue',
          'element-plus': 'element-plus',
          // lodash: '_'
        },
      }
      // [
      //   {
      //     format: "es",
      //     entryFileNames: "[name].mjs",
      //     preserveModules: false,
      //     dir: resolve(projRoot, "dist/es"),
      //     preserveModulesRoot: resolve(projRoot, "dist/es"),
      //   },
      //   {
      //     format: "cjs",
      //     entryFileNames: "[name].js",
      //     preserveModules: false,
      //     dir: resolve(projRoot, "dist/cjs"),
      //     preserveModulesRoot: resolve(projRoot, "dist/cjs"),
      //   },
      // ],
    },
    lib: {
      entry: "index.ts",
      name: 'c7-plus',
      fileName: (format, name) => `${format}/${name}.js`,
      formats: ['es', 'cjs'],
    },
  },
});
