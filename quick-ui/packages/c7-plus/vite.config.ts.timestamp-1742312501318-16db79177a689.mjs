// vite.config.ts
import { defineConfig, normalizePath } from "file:///E:/workspace/luyanan/quick-boot/quick-ui/node_modules/.pnpm/vite@4.5.9_@types+node@18.16.0_sass@1.77.5/node_modules/vite/dist/node/index.js";
import vue from "file:///E:/workspace/luyanan/quick-boot/quick-ui/node_modules/.pnpm/@vitejs+plugin-vue@4.6.2_vite@4.5.9_@types+node@18.16.0_sass@1.77.5__vue@3.5.13_typescript@5.8.2_/node_modules/@vitejs/plugin-vue/dist/index.mjs";
import vueJsx from "file:///E:/workspace/luyanan/quick-boot/quick-ui/node_modules/.pnpm/@vitejs+plugin-vue-jsx@3.1.0_vite@4.5.9_@types+node@18.16.0_sass@1.77.5__vue@3.5.13_typescript@5.8.2_/node_modules/@vitejs/plugin-vue-jsx/dist/index.mjs";
import dts from "file:///E:/workspace/luyanan/quick-boot/quick-ui/node_modules/.pnpm/vite-plugin-dts@3.9.1_@types+node@18.16.0_rollup@4.34.8_typescript@5.8.2_vite@4.5.9_@types+node@18.16.0_sass@1.77.5_/node_modules/vite-plugin-dts/dist/index.mjs";
import AutoImport from "file:///E:/workspace/luyanan/quick-boot/quick-ui/node_modules/.pnpm/unplugin-auto-import@0.16.7_@vueuse+core@10.11.0_vue@3.5.13_typescript@5.8.2___rollup@4.34.8/node_modules/unplugin-auto-import/dist/vite.js";
import Components from "file:///E:/workspace/luyanan/quick-boot/quick-ui/node_modules/.pnpm/unplugin-vue-components@0.25.2_@babel+parser@7.26.9_rollup@4.34.8_vue@3.5.13_typescript@5.8.2_/node_modules/unplugin-vue-components/dist/vite.mjs";
import { ElementPlusResolver } from "file:///E:/workspace/luyanan/quick-boot/quick-ui/node_modules/.pnpm/unplugin-vue-components@0.25.2_@babel+parser@7.26.9_rollup@4.34.8_vue@3.5.13_typescript@5.8.2_/node_modules/unplugin-vue-components/dist/resolvers.mjs";
import { resolve } from "path";
import { viteStaticCopy } from "file:///E:/workspace/luyanan/quick-boot/quick-ui/node_modules/.pnpm/vite-plugin-static-copy@2.3.0_vite@4.5.9_@types+node@18.16.0_sass@1.77.5_/node_modules/vite-plugin-static-copy/dist/index.js";
var __vite_injected_original_dirname = "E:\\workspace\\luyanan\\quick-boot\\quick-ui\\packages\\c7-plus";
var projRoot = resolve(__vite_injected_original_dirname, "..", "..");
var vite_config_default = defineConfig({
  plugins: [
    vue(),
    vueJsx(),
    dts({
      outDir: resolve(projRoot, "dist/types"),
      staticImport: true,
      insertTypesEntry: true
    }),
    AutoImport({
      resolvers: [ElementPlusResolver()]
    }),
    Components({
      resolvers: [ElementPlusResolver()]
    }),
    viteStaticCopy({
      targets: [
        { src: normalizePath(resolve(projRoot, "packages", "c7-plus", "package.json")), dest: normalizePath(resolve(projRoot, "dist")) },
        { src: normalizePath(resolve(projRoot, "README.md")), dest: normalizePath(resolve(projRoot, "dist")) },
        { src: normalizePath(resolve(projRoot, "LICENSE")), dest: normalizePath(resolve(projRoot, "dist")) }
      ]
    })
  ],
  build: {
    outDir: resolve(projRoot, "dist/"),
    emptyOutDir: true,
    target: "modules",
    minify: true,
    // cssCodeSplit: true,
    rollupOptions: {
      // 忽略vue文件
      external: ["vue", "element-plus", "lodash-es"],
      // input: ["index.ts"],
      output: {
        globals: {
          vue: "Vue",
          "element-plus": "element-plus"
          // lodash: '_'
        }
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
      name: "c7-plus",
      fileName: (format2, name) => `${format2}/${name}.js`,
      formats: ["es", "cjs"]
    }
  }
});
export {
  vite_config_default as default
};
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcudHMiXSwKICAic291cmNlc0NvbnRlbnQiOiBbImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJFOlxcXFx3b3Jrc3BhY2VcXFxcbHV5YW5hblxcXFxxdWljay1ib290XFxcXHF1aWNrLXVpXFxcXHBhY2thZ2VzXFxcXGM3LXBsdXNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZmlsZW5hbWUgPSBcIkU6XFxcXHdvcmtzcGFjZVxcXFxsdXlhbmFuXFxcXHF1aWNrLWJvb3RcXFxccXVpY2stdWlcXFxccGFja2FnZXNcXFxcYzctcGx1c1xcXFx2aXRlLmNvbmZpZy50c1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vRTovd29ya3NwYWNlL2x1eWFuYW4vcXVpY2stYm9vdC9xdWljay11aS9wYWNrYWdlcy9jNy1wbHVzL3ZpdGUuY29uZmlnLnRzXCI7aW1wb3J0IHsgZGVmaW5lQ29uZmlnLCBub3JtYWxpemVQYXRoIH0gZnJvbSBcInZpdGVcIjtcclxuaW1wb3J0IHZ1ZSBmcm9tIFwiQHZpdGVqcy9wbHVnaW4tdnVlXCI7XHJcbmltcG9ydCB2dWVKc3ggZnJvbSBcIkB2aXRlanMvcGx1Z2luLXZ1ZS1qc3hcIjtcclxuaW1wb3J0IGR0cyBmcm9tIFwidml0ZS1wbHVnaW4tZHRzXCI7XHJcbmltcG9ydCBBdXRvSW1wb3J0IGZyb20gXCJ1bnBsdWdpbi1hdXRvLWltcG9ydC92aXRlXCI7XHJcbmltcG9ydCBDb21wb25lbnRzIGZyb20gXCJ1bnBsdWdpbi12dWUtY29tcG9uZW50cy92aXRlXCI7XHJcbmltcG9ydCB7IEVsZW1lbnRQbHVzUmVzb2x2ZXIgfSBmcm9tIFwidW5wbHVnaW4tdnVlLWNvbXBvbmVudHMvcmVzb2x2ZXJzXCI7XHJcbmltcG9ydCB7IGZvcm1hdCwgcmVzb2x2ZSB9IGZyb20gXCJwYXRoXCI7XHJcbmltcG9ydCB7IHZpdGVTdGF0aWNDb3B5IH0gZnJvbSAndml0ZS1wbHVnaW4tc3RhdGljLWNvcHknXHJcbmNvbnN0IHByb2pSb290ID0gcmVzb2x2ZShfX2Rpcm5hbWUsICcuLicsICcuLicpXHJcbmV4cG9ydCBkZWZhdWx0IGRlZmluZUNvbmZpZyh7XHJcbiAgcGx1Z2luczogW1xyXG4gICAgdnVlKCksXHJcbiAgICB2dWVKc3goKSxcclxuICAgIGR0cyh7XHJcbiAgICAgIG91dERpcjogcmVzb2x2ZShwcm9qUm9vdCwgJ2Rpc3QvdHlwZXMnKSxcclxuICAgICAgc3RhdGljSW1wb3J0OiB0cnVlLFxyXG4gICAgICBpbnNlcnRUeXBlc0VudHJ5OiB0cnVlXHJcbiAgICB9KSxcclxuICAgIEF1dG9JbXBvcnQoe1xyXG4gICAgICByZXNvbHZlcnM6IFtFbGVtZW50UGx1c1Jlc29sdmVyKCldLFxyXG4gICAgfSksXHJcbiAgICBDb21wb25lbnRzKHtcclxuICAgICAgcmVzb2x2ZXJzOiBbRWxlbWVudFBsdXNSZXNvbHZlcigpXSxcclxuICAgIH0pLFxyXG4gICAgdml0ZVN0YXRpY0NvcHkoe1xyXG4gICAgICB0YXJnZXRzOiBbXHJcbiAgICAgICAgeyBzcmM6IG5vcm1hbGl6ZVBhdGgocmVzb2x2ZShwcm9qUm9vdCwgJ3BhY2thZ2VzJywgJ2M3LXBsdXMnLCAncGFja2FnZS5qc29uJykpLCBkZXN0OiBub3JtYWxpemVQYXRoKHJlc29sdmUocHJvalJvb3QsICdkaXN0JykpIH0sXHJcbiAgICAgICAgeyBzcmM6IG5vcm1hbGl6ZVBhdGgocmVzb2x2ZShwcm9qUm9vdCwgJ1JFQURNRS5tZCcpKSwgZGVzdDogbm9ybWFsaXplUGF0aChyZXNvbHZlKHByb2pSb290LCAnZGlzdCcpKSB9LFxyXG4gICAgICAgIHsgc3JjOiBub3JtYWxpemVQYXRoKHJlc29sdmUocHJvalJvb3QsICdMSUNFTlNFJykpLCBkZXN0OiBub3JtYWxpemVQYXRoKHJlc29sdmUocHJvalJvb3QsICdkaXN0JykpIH1cclxuICAgICAgXSxcclxuICAgIH0pXHJcbiAgXSxcclxuICBidWlsZDoge1xyXG4gICAgb3V0RGlyOiByZXNvbHZlKHByb2pSb290LCBcImRpc3QvXCIpLFxyXG4gICAgZW1wdHlPdXREaXI6IHRydWUsXHJcbiAgICB0YXJnZXQ6IFwibW9kdWxlc1wiLFxyXG4gICAgbWluaWZ5OiB0cnVlLFxyXG4gICAgLy8gY3NzQ29kZVNwbGl0OiB0cnVlLFxyXG4gICAgcm9sbHVwT3B0aW9uczoge1xyXG4gICAgICAvLyBcdTVGRkRcdTc1NjV2dWVcdTY1ODdcdTRFRjZcclxuICAgICAgZXh0ZXJuYWw6IFsndnVlJywgJ2VsZW1lbnQtcGx1cycsICdsb2Rhc2gtZXMnXSxcclxuICAgICAgLy8gaW5wdXQ6IFtcImluZGV4LnRzXCJdLFxyXG4gICAgICBvdXRwdXQ6IHtcclxuICAgICAgICBnbG9iYWxzOiB7XHJcbiAgICAgICAgICB2dWU6ICdWdWUnLFxyXG4gICAgICAgICAgJ2VsZW1lbnQtcGx1cyc6ICdlbGVtZW50LXBsdXMnLFxyXG4gICAgICAgICAgLy8gbG9kYXNoOiAnXydcclxuICAgICAgICB9LFxyXG4gICAgICB9XHJcbiAgICAgIC8vIFtcclxuICAgICAgLy8gICB7XHJcbiAgICAgIC8vICAgICBmb3JtYXQ6IFwiZXNcIixcclxuICAgICAgLy8gICAgIGVudHJ5RmlsZU5hbWVzOiBcIltuYW1lXS5tanNcIixcclxuICAgICAgLy8gICAgIHByZXNlcnZlTW9kdWxlczogZmFsc2UsXHJcbiAgICAgIC8vICAgICBkaXI6IHJlc29sdmUocHJvalJvb3QsIFwiZGlzdC9lc1wiKSxcclxuICAgICAgLy8gICAgIHByZXNlcnZlTW9kdWxlc1Jvb3Q6IHJlc29sdmUocHJvalJvb3QsIFwiZGlzdC9lc1wiKSxcclxuICAgICAgLy8gICB9LFxyXG4gICAgICAvLyAgIHtcclxuICAgICAgLy8gICAgIGZvcm1hdDogXCJjanNcIixcclxuICAgICAgLy8gICAgIGVudHJ5RmlsZU5hbWVzOiBcIltuYW1lXS5qc1wiLFxyXG4gICAgICAvLyAgICAgcHJlc2VydmVNb2R1bGVzOiBmYWxzZSxcclxuICAgICAgLy8gICAgIGRpcjogcmVzb2x2ZShwcm9qUm9vdCwgXCJkaXN0L2Nqc1wiKSxcclxuICAgICAgLy8gICAgIHByZXNlcnZlTW9kdWxlc1Jvb3Q6IHJlc29sdmUocHJvalJvb3QsIFwiZGlzdC9janNcIiksXHJcbiAgICAgIC8vICAgfSxcclxuICAgICAgLy8gXSxcclxuICAgIH0sXHJcbiAgICBsaWI6IHtcclxuICAgICAgZW50cnk6IFwiaW5kZXgudHNcIixcclxuICAgICAgbmFtZTogJ2M3LXBsdXMnLFxyXG4gICAgICBmaWxlTmFtZTogKGZvcm1hdCwgbmFtZSkgPT4gYCR7Zm9ybWF0fS8ke25hbWV9LmpzYCxcclxuICAgICAgZm9ybWF0czogWydlcycsICdjanMnXSxcclxuICAgIH0sXHJcbiAgfSxcclxufSk7XHJcbiJdLAogICJtYXBwaW5ncyI6ICI7QUFBMlcsU0FBUyxjQUFjLHFCQUFxQjtBQUN2WixPQUFPLFNBQVM7QUFDaEIsT0FBTyxZQUFZO0FBQ25CLE9BQU8sU0FBUztBQUNoQixPQUFPLGdCQUFnQjtBQUN2QixPQUFPLGdCQUFnQjtBQUN2QixTQUFTLDJCQUEyQjtBQUNwQyxTQUFpQixlQUFlO0FBQ2hDLFNBQVMsc0JBQXNCO0FBUi9CLElBQU0sbUNBQW1DO0FBU3pDLElBQU0sV0FBVyxRQUFRLGtDQUFXLE1BQU0sSUFBSTtBQUM5QyxJQUFPLHNCQUFRLGFBQWE7QUFBQSxFQUMxQixTQUFTO0FBQUEsSUFDUCxJQUFJO0FBQUEsSUFDSixPQUFPO0FBQUEsSUFDUCxJQUFJO0FBQUEsTUFDRixRQUFRLFFBQVEsVUFBVSxZQUFZO0FBQUEsTUFDdEMsY0FBYztBQUFBLE1BQ2Qsa0JBQWtCO0FBQUEsSUFDcEIsQ0FBQztBQUFBLElBQ0QsV0FBVztBQUFBLE1BQ1QsV0FBVyxDQUFDLG9CQUFvQixDQUFDO0FBQUEsSUFDbkMsQ0FBQztBQUFBLElBQ0QsV0FBVztBQUFBLE1BQ1QsV0FBVyxDQUFDLG9CQUFvQixDQUFDO0FBQUEsSUFDbkMsQ0FBQztBQUFBLElBQ0QsZUFBZTtBQUFBLE1BQ2IsU0FBUztBQUFBLFFBQ1AsRUFBRSxLQUFLLGNBQWMsUUFBUSxVQUFVLFlBQVksV0FBVyxjQUFjLENBQUMsR0FBRyxNQUFNLGNBQWMsUUFBUSxVQUFVLE1BQU0sQ0FBQyxFQUFFO0FBQUEsUUFDL0gsRUFBRSxLQUFLLGNBQWMsUUFBUSxVQUFVLFdBQVcsQ0FBQyxHQUFHLE1BQU0sY0FBYyxRQUFRLFVBQVUsTUFBTSxDQUFDLEVBQUU7QUFBQSxRQUNyRyxFQUFFLEtBQUssY0FBYyxRQUFRLFVBQVUsU0FBUyxDQUFDLEdBQUcsTUFBTSxjQUFjLFFBQVEsVUFBVSxNQUFNLENBQUMsRUFBRTtBQUFBLE1BQ3JHO0FBQUEsSUFDRixDQUFDO0FBQUEsRUFDSDtBQUFBLEVBQ0EsT0FBTztBQUFBLElBQ0wsUUFBUSxRQUFRLFVBQVUsT0FBTztBQUFBLElBQ2pDLGFBQWE7QUFBQSxJQUNiLFFBQVE7QUFBQSxJQUNSLFFBQVE7QUFBQTtBQUFBLElBRVIsZUFBZTtBQUFBO0FBQUEsTUFFYixVQUFVLENBQUMsT0FBTyxnQkFBZ0IsV0FBVztBQUFBO0FBQUEsTUFFN0MsUUFBUTtBQUFBLFFBQ04sU0FBUztBQUFBLFVBQ1AsS0FBSztBQUFBLFVBQ0wsZ0JBQWdCO0FBQUE7QUFBQSxRQUVsQjtBQUFBLE1BQ0Y7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBLElBaUJGO0FBQUEsSUFDQSxLQUFLO0FBQUEsTUFDSCxPQUFPO0FBQUEsTUFDUCxNQUFNO0FBQUEsTUFDTixVQUFVLENBQUNBLFNBQVEsU0FBUyxHQUFHQSxPQUFNLElBQUksSUFBSTtBQUFBLE1BQzdDLFNBQVMsQ0FBQyxNQUFNLEtBQUs7QUFBQSxJQUN2QjtBQUFBLEVBQ0Y7QUFDRixDQUFDOyIsCiAgIm5hbWVzIjogWyJmb3JtYXQiXQp9Cg==
