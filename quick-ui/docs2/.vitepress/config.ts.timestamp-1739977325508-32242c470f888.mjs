// .vitepress/config.ts
import { defineConfig } from "file:///E:/workspace/luyanan/quick-boot/quick-ui/node_modules/.pnpm/vitepress@1.0.0-beta.3_@algolia+client-search@5.20.3_@types+node@22.13.4_async-validator@4.2._bl43pyy7xj3eavkjdzhdyfllfi/node_modules/vitepress/dist/node/index.js";
import path2 from "path";
import vueJsx from "file:///E:/workspace/luyanan/quick-boot/quick-ui/node_modules/.pnpm/@vitejs+plugin-vue-jsx@3.1.0_vite@4.5.9_@types+node@22.13.4_sass@1.77.5__vue@3.5.13/node_modules/@vitejs/plugin-vue-jsx/dist/index.mjs";

// .vitepress/config/sidebar.ts
var sidebar_default = {
  // "/components/": [
  //   {
  //     text: "基础组件",
  //     items: [
  //       { text: "QuickFrom", link: "/components/quick-form/index" },
  //       { text: "QTable", link: "/components/quick-table/index" },
  //     ],
  //   },
  // ],
  "/guide/": [
    { text: "\u8BBE\u8BA1", link: "/guide/design" },
    {
      text: "\u6307\u5357",
      items: [
        { text: "\u5B89\u88C5", link: "/guide/installation" },
        { text: "\u5FEB\u901F\u5F00\u59CB", link: "/guide/quickstart" }
      ]
    },
    { text: "\u66F4\u65B0\u65E5\u5FD7", link: "/updatelog/index" }
  ]
};

// .vitepress/config/nav.ts
var nav_default = [
  { text: "\u6307\u5357", link: "/guide/design", activeMatch: "^/guide/" },
  { text: "QForm", link: "/components/quick-form/index" },
  { text: "QTable", link: "/components/quick-table/index" },
  { text: "Element-Plus\u5B98\u7F51", link: "https://element-plus.org/zh-CN/", icon: "https://element-plus.org/images/element-plus-logo-small.svg" }
];

// .vitepress/config/plugins.ts
import path from "path";
import fs from "fs";
import MarkdownIt from "file:///E:/workspace/luyanan/quick-boot/quick-ui/node_modules/.pnpm/markdown-it@13.0.2/node_modules/markdown-it/index.js";
import mdContainer from "file:///E:/workspace/luyanan/quick-boot/quick-ui/node_modules/.pnpm/markdown-it-container@3.0.0/node_modules/markdown-it-container/index.js";

// .vitepress/utils/highlight.ts
import chalk from "file:///E:/workspace/luyanan/quick-boot/quick-ui/node_modules/.pnpm/chalk@5.4.1/node_modules/chalk/source/index.js";
import escapeHtml from "file:///E:/workspace/luyanan/quick-boot/quick-ui/node_modules/.pnpm/escape-html@1.0.3/node_modules/escape-html/index.js";
import prism from "file:///E:/workspace/luyanan/quick-boot/quick-ui/node_modules/.pnpm/prismjs@1.29.0/node_modules/prismjs/prism.js";
import consola from "file:///E:/workspace/luyanan/quick-boot/quick-ui/node_modules/.pnpm/consola@3.4.0/node_modules/consola/dist/index.mjs";
import loadLanguages from "file:///E:/workspace/luyanan/quick-boot/quick-ui/node_modules/.pnpm/prismjs@1.29.0/node_modules/prismjs/components/index.js";
loadLanguages(["markup", "css", "javascript"]);
function wrap(code, lang) {
  if (lang === "text") {
    code = escapeHtml(code);
  }
  return `<pre v-pre><code>${code}</code></pre>`;
}
var highlight = (str, lang) => {
  if (!lang) {
    return wrap(str, "text");
  }
  lang = lang.toLowerCase();
  const rawLang = lang;
  if (lang === "vue" || lang === "html") {
    lang = "markup";
  }
  if (lang === "md") {
    lang = "markdown";
  }
  if (lang === "ts") {
    lang = "typescript";
  }
  if (lang === "py") {
    lang = "python";
  }
  if (!prism.languages[lang]) {
    try {
      loadLanguages([lang]);
    } catch {
      consola.warn(
        chalk.yellow(
          `[vitepress] Syntax highlight for language "${lang}" is not supported.`
        )
      );
    }
  }
  if (prism.languages[lang]) {
    const code = prism.highlight(str, prism.languages[lang], lang);
    return wrap(code, rawLang);
  }
  return wrap(str, "text");
};

// .vitepress/theme/global.ts
import { resolve } from "path";
var __vite_injected_original_dirname = "E:\\workspace\\luyanan\\quick-boot\\quick-ui\\docs\\.vitepress\\theme";
var projRoot = resolve(__vite_injected_original_dirname, "..", "..", "..");
var docsDirName = "docs";
var docRoot = resolve(projRoot, docsDirName);
var exampleDir = resolve(projRoot, "src/views/demo");

// .vitepress/plugins/external-link-icon.ts
var external_link_icon_default = (md) => {
  const renderToken = (tokens, idx, options, env, self) => self.renderToken(tokens, idx, options);
  const defaultLinkOpenRenderer = md.renderer.rules.link_open || renderToken;
  const defaultLinkCloseRenderer = md.renderer.rules.link_close || renderToken;
  let isExternalLink = false;
  md.renderer.rules.link_open = (tokens, idx, options, env, self) => {
    const token = tokens[idx];
    const href = token.attrGet("href");
    if (href) {
      token.attrJoin("class", "vp-link");
      if (/^((ht|f)tps?):\/\/?/.test(href)) {
        isExternalLink = true;
      }
    }
    return defaultLinkOpenRenderer(tokens, idx, options, env, self);
  };
  md.renderer.rules.link_close = (tokens, idx, options, env, self) => {
    if (isExternalLink) {
      isExternalLink = false;
      return `<i-ri-external-link-line class="link-icon" />${self.renderToken(
        tokens,
        idx,
        options
      )}`;
    }
    return defaultLinkCloseRenderer(tokens, idx, options, env, self);
  };
};

// .vitepress/plugins/tooltip.ts
var tooltip_default = (md) => {
  md.renderer.rules.tooltip = (tokens, idx) => {
    const token = tokens[idx];
    return `<api-typing type="${token.content}" details="${token.info}" />`;
  };
  md.inline.ruler.before("emphasis", "tooltip", (state, silent) => {
    const tooltipRegExp = /^\^\[([^\]]*)\](`[^`]*`)?/;
    const str = state.src.slice(state.pos, state.posMax);
    if (!tooltipRegExp.test(str))
      return false;
    if (silent)
      return true;
    const result = str.match(tooltipRegExp);
    if (!result)
      return false;
    const token = state.push("tooltip", "tooltip", 0);
    token.content = result[1].replace(/\\\|/g, "|");
    token.info = (result[2] || "").replace(/^`(.*)`$/, "$1");
    token.level = state.level;
    state.pos += result[0].length;
    return true;
  });
};

// .vitepress/plugins/table-wrapper.ts
var table_wrapper_default = (md) => {
  md.renderer.rules.table_open = () => '<div class="vp-table"><table>';
  md.renderer.rules.table_close = () => "</table></div>";
};

// .vitepress/plugins/tag.ts
var tag_default = (md) => {
  md.renderer.rules.tag = (tokens, idx) => {
    const token = tokens[idx];
    const value = token.content;
    const tagClass = ["beta", "deprecated", "a11y", "required"].includes(value) ? value : "";
    return `<span class="vp-tag ${tagClass}">${value}</span>`;
  };
  md.inline.ruler.before("emphasis", "tag", (state, silent) => {
    const tagRegExp = /^\^\(([^)]*)\)/;
    const str = state.src.slice(state.pos, state.posMax);
    if (!tagRegExp.test(str))
      return false;
    if (silent)
      return true;
    const result = str.match(tagRegExp);
    if (!result)
      return false;
    const token = state.push("tag", "tag", 0);
    token.content = result[1].trim();
    token.level = state.level;
    state.pos += result[0].length;
    return true;
  });
};

// .vitepress/plugins/api-table.ts
import markdown from "file:///E:/workspace/luyanan/quick-boot/quick-ui/node_modules/.pnpm/markdown-it@13.0.2/node_modules/markdown-it/index.js";
var ApiMd = new markdown();
var ApiTableContainer = (md) => {
  const fence = md.renderer.rules.fence;
  ApiMd.renderer.rules = md.renderer.rules;
  md.renderer.rules.fence = (...args) => {
    const [tokens, idx, ...rest] = args;
    const [options, env] = rest;
    const token = tokens[idx];
    if (token.info === "api") {
      const newTokens = md.parse(token.content, env);
      let result = "";
      const { rules } = md.renderer;
      newTokens.forEach((newToken, idx2) => {
        const { type } = newToken;
        if (type === "inline") {
          result += md.renderer.renderInline(newToken.children, options, env);
        } else if (typeof rules[type] !== "undefined") {
          result += rules[newToken.type](
            newTokens,
            idx2,
            options,
            env,
            md.renderer
          );
        } else {
          result += md.renderer.renderToken(newTokens, idx2, options);
        }
      });
      return result;
    }
    return fence.call(md, ...args);
  };
};

// .vitepress/config/plugins.ts
var localMd = MarkdownIt();
var mdPlugin = (md) => {
  md.use(external_link_icon_default);
  md.use(tooltip_default);
  md.use(table_wrapper_default);
  md.use(tag_default);
  md.use(mdContainer, "demo", {
    validate(params) {
      return !!params.trim().match(/^demo\s*(.*)$/);
    },
    render(tokens, idx) {
      var _a;
      const m = tokens[idx].info.trim().match(/^demo\s+(.*)$/);
      if (tokens[idx].nesting === 1) {
        const description = m && m.length > 1 ? m[1] : "";
        const sourceFileToken = tokens[idx + 2];
        let source = "";
        const sourceFile = ((_a = sourceFileToken.children) == null ? void 0 : _a[0].content) ?? "";
        if (sourceFileToken.type === "inline") {
          source = fs.readFileSync(
            path.resolve(exampleDir, `${sourceFile}.vue`),
            "utf-8"
          );
        }
        if (!source)
          throw new Error(`Incorrect source file: ${sourceFile}`);
        return `<Demo
                        source="${encodeURIComponent(highlight(source, "vue"))}"
                        path="${sourceFile}"
                        raw-source="${encodeURIComponent(source)}"
                        description="${encodeURIComponent(
          localMd.render(description)
        )}">`;
      } else {
        return "</Demo>\n";
      }
    }
  });
  md.use(ApiTableContainer);
};

// .vitepress/config.ts
var __vite_injected_original_dirname2 = "E:\\workspace\\luyanan\\quick-boot\\quick-ui\\docs\\.vitepress";
var config_default = defineConfig({
  // base: "/",
  title: "ElementPlus Kit",
  description: "\u57FA\u4E8E ElementPlus \u7684\u7EC4\u4EF6\u751F\u6210\u5668",
  head: [
    ["link", { rel: "icon", href: "logo.png" }],
    ["meta", { name: "referrer", content: "no-referrer" }]
  ],
  themeConfig: {
    logo: "logo.png",
    outline: [2, 3],
    nav: nav_default,
    //头部导航
    sidebar: sidebar_default,
    //侧边栏
    search: {
      provider: "local"
    }
  },
  markdown: {
    theme: {
      light: "vitesse-light",
      dark: "vitesse-dark"
    },
    lineNumbers: true,
    config(md) {
      mdPlugin(md);
    }
  },
  vite: {
    plugins: [vueJsx()],
    resolve: {
      alias: {
        "@alias": path2.resolve(__vite_injected_original_dirname2, "../")
      }
    }
  }
});
export {
  config_default as default
};
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsiLnZpdGVwcmVzcy9jb25maWcudHMiLCAiLnZpdGVwcmVzcy9jb25maWcvc2lkZWJhci50cyIsICIudml0ZXByZXNzL2NvbmZpZy9uYXYudHMiLCAiLnZpdGVwcmVzcy9jb25maWcvcGx1Z2lucy50cyIsICIudml0ZXByZXNzL3V0aWxzL2hpZ2hsaWdodC50cyIsICIudml0ZXByZXNzL3RoZW1lL2dsb2JhbC50cyIsICIudml0ZXByZXNzL3BsdWdpbnMvZXh0ZXJuYWwtbGluay1pY29uLnRzIiwgIi52aXRlcHJlc3MvcGx1Z2lucy90b29sdGlwLnRzIiwgIi52aXRlcHJlc3MvcGx1Z2lucy90YWJsZS13cmFwcGVyLnRzIiwgIi52aXRlcHJlc3MvcGx1Z2lucy90YWcudHMiLCAiLnZpdGVwcmVzcy9wbHVnaW5zL2FwaS10YWJsZS50cyJdLAogICJzb3VyY2VzQ29udGVudCI6IFsiY29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2Rpcm5hbWUgPSBcIkU6XFxcXHdvcmtzcGFjZVxcXFxsdXlhbmFuXFxcXHF1aWNrLWJvb3RcXFxccXVpY2stdWlcXFxcZG9jc1xcXFwudml0ZXByZXNzXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ZpbGVuYW1lID0gXCJFOlxcXFx3b3Jrc3BhY2VcXFxcbHV5YW5hblxcXFxxdWljay1ib290XFxcXHF1aWNrLXVpXFxcXGRvY3NcXFxcLnZpdGVwcmVzc1xcXFxjb25maWcudHNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfaW1wb3J0X21ldGFfdXJsID0gXCJmaWxlOi8vL0U6L3dvcmtzcGFjZS9sdXlhbmFuL3F1aWNrLWJvb3QvcXVpY2stdWkvZG9jcy8udml0ZXByZXNzL2NvbmZpZy50c1wiO2ltcG9ydCB7IGRlZmluZUNvbmZpZyB9IGZyb20gXCJ2aXRlcHJlc3NcIjtcclxuLy8gaW1wb3J0IHsgY29tcG9uZW50UHJldmlldywgY29udGFpbmVyUHJldmlldyB9IGZyb20gXCJAdml0ZXByZXNzLWRlbW8tcHJldmlldy9wbHVnaW5cIlxyXG5pbXBvcnQgcGF0aCBmcm9tIFwicGF0aFwiO1xyXG5cclxuaW1wb3J0IHZ1ZUpzeCBmcm9tIFwiQHZpdGVqcy9wbHVnaW4tdnVlLWpzeFwiO1xyXG4vLyBpbXBvcnQgdnVlU2V0dXBFeHRlbmQgZnJvbSAndml0ZS1wbHVnaW4tdnVlLXNldHVwLWV4dGVuZCdcclxuaW1wb3J0IHNpZGViYXIgZnJvbSBcIi4vY29uZmlnL3NpZGViYXJcIjtcclxuaW1wb3J0IG5hdiBmcm9tIFwiLi9jb25maWcvbmF2XCI7XHJcbmltcG9ydCB7IG1kUGx1Z2luIH0gZnJvbSBcIi4vY29uZmlnL3BsdWdpbnNcIjtcclxuXHJcbmV4cG9ydCBkZWZhdWx0IGRlZmluZUNvbmZpZyh7XHJcbiAgLy8gYmFzZTogXCIvXCIsXHJcbiAgdGl0bGU6IFwiRWxlbWVudFBsdXMgS2l0XCIsXHJcbiAgZGVzY3JpcHRpb246IFwiXHU1N0ZBXHU0RThFIEVsZW1lbnRQbHVzIFx1NzY4NFx1N0VDNFx1NEVGNlx1NzUxRlx1NjIxMFx1NTY2OFwiLFxyXG4gIGhlYWQ6IFtcclxuICAgIFsnbGluaycsIHsgcmVsOiAnaWNvbicsIGhyZWY6ICdsb2dvLnBuZycgfV0sXHJcbiAgICBbXCJtZXRhXCIsIHsgbmFtZTogJ3JlZmVycmVyJywgY29udGVudDogJ25vLXJlZmVycmVyJyB9XVxyXG4gIF0sXHJcbiAgdGhlbWVDb25maWc6IHtcclxuICAgIGxvZ286ICdsb2dvLnBuZycsXHJcbiAgICBvdXRsaW5lOiBbMiwgM10sXHJcbiAgICBuYXYsIC8vXHU1OTM0XHU5MEU4XHU1QkZDXHU4MjJBXHJcbiAgICBzaWRlYmFyLCAvL1x1NEZBN1x1OEZCOVx1NjgwRlxyXG4gICAgc2VhcmNoOiB7XHJcbiAgICAgIHByb3ZpZGVyOiAnbG9jYWwnXHJcbiAgICB9XHJcbiAgfSxcclxuICBtYXJrZG93bjoge1xyXG4gICAgdGhlbWU6IHtcclxuICAgICAgbGlnaHQ6IFwidml0ZXNzZS1saWdodFwiLFxyXG4gICAgICBkYXJrOiBcInZpdGVzc2UtZGFya1wiLFxyXG4gICAgfSxcclxuICAgIGxpbmVOdW1iZXJzOiB0cnVlLFxyXG4gICAgY29uZmlnKG1kKSB7XHJcbiAgICAgIG1kUGx1Z2luKG1kKTtcclxuICAgIH0sXHJcbiAgfSxcclxuICB2aXRlOiB7XHJcbiAgICBwbHVnaW5zOiBbdnVlSnN4KCldLFxyXG4gICAgcmVzb2x2ZToge1xyXG4gICAgICBhbGlhczoge1xyXG4gICAgICAgIFwiQGFsaWFzXCI6IHBhdGgucmVzb2x2ZShfX2Rpcm5hbWUsIFwiLi4vXCIpLFxyXG4gICAgICB9LFxyXG4gICAgfSxcclxuICB9LFxyXG59KTtcclxuIiwgImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJFOlxcXFx3b3Jrc3BhY2VcXFxcbHV5YW5hblxcXFxxdWljay1ib290XFxcXHF1aWNrLXVpXFxcXGRvY3NcXFxcLnZpdGVwcmVzc1xcXFxjb25maWdcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZmlsZW5hbWUgPSBcIkU6XFxcXHdvcmtzcGFjZVxcXFxsdXlhbmFuXFxcXHF1aWNrLWJvb3RcXFxccXVpY2stdWlcXFxcZG9jc1xcXFwudml0ZXByZXNzXFxcXGNvbmZpZ1xcXFxzaWRlYmFyLnRzXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ltcG9ydF9tZXRhX3VybCA9IFwiZmlsZTovLy9FOi93b3Jrc3BhY2UvbHV5YW5hbi9xdWljay1ib290L3F1aWNrLXVpL2RvY3MvLnZpdGVwcmVzcy9jb25maWcvc2lkZWJhci50c1wiO2V4cG9ydCBkZWZhdWx0IHtcclxuICAvLyBcIi9jb21wb25lbnRzL1wiOiBbXHJcbiAgLy8gICB7XHJcbiAgLy8gICAgIHRleHQ6IFwiXHU1N0ZBXHU3ODQwXHU3RUM0XHU0RUY2XCIsXHJcbiAgLy8gICAgIGl0ZW1zOiBbXHJcbiAgLy8gICAgICAgeyB0ZXh0OiBcIlF1aWNrRnJvbVwiLCBsaW5rOiBcIi9jb21wb25lbnRzL3F1aWNrLWZvcm0vaW5kZXhcIiB9LFxyXG4gIC8vICAgICAgIHsgdGV4dDogXCJRVGFibGVcIiwgbGluazogXCIvY29tcG9uZW50cy9xdWljay10YWJsZS9pbmRleFwiIH0sXHJcbiAgLy8gICAgIF0sXHJcbiAgLy8gICB9LFxyXG4gIC8vIF0sXHJcbiAgXCIvZ3VpZGUvXCI6IFtcclxuICAgIHsgdGV4dDogXCJcdThCQkVcdThCQTFcIiwgbGluazogXCIvZ3VpZGUvZGVzaWduXCIgfSxcclxuICAgIHtcclxuICAgICAgdGV4dDogXCJcdTYzMDdcdTUzNTdcIixcclxuICAgICAgaXRlbXM6IFtcclxuICAgICAgICB7IHRleHQ6IFwiXHU1Qjg5XHU4OEM1XCIsIGxpbms6IFwiL2d1aWRlL2luc3RhbGxhdGlvblwiIH0sXHJcbiAgICAgICAgeyB0ZXh0OiBcIlx1NUZFQlx1OTAxRlx1NUYwMFx1NTlDQlwiLCBsaW5rOiBcIi9ndWlkZS9xdWlja3N0YXJ0XCIgfSxcclxuICAgICAgXSxcclxuICAgIH0sXHJcbiAgICB7IHRleHQ6IFwiXHU2NkY0XHU2NUIwXHU2NUU1XHU1RkQ3XCIsIGxpbms6IFwiL3VwZGF0ZWxvZy9pbmRleFwiIH1cclxuICBdLFxyXG59O1xyXG4iLCAiY29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2Rpcm5hbWUgPSBcIkU6XFxcXHdvcmtzcGFjZVxcXFxsdXlhbmFuXFxcXHF1aWNrLWJvb3RcXFxccXVpY2stdWlcXFxcZG9jc1xcXFwudml0ZXByZXNzXFxcXGNvbmZpZ1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9maWxlbmFtZSA9IFwiRTpcXFxcd29ya3NwYWNlXFxcXGx1eWFuYW5cXFxccXVpY2stYm9vdFxcXFxxdWljay11aVxcXFxkb2NzXFxcXC52aXRlcHJlc3NcXFxcY29uZmlnXFxcXG5hdi50c1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vRTovd29ya3NwYWNlL2x1eWFuYW4vcXVpY2stYm9vdC9xdWljay11aS9kb2NzLy52aXRlcHJlc3MvY29uZmlnL25hdi50c1wiO2V4cG9ydCBkZWZhdWx0IFtcclxuICB7IHRleHQ6IFwiXHU2MzA3XHU1MzU3XCIsIGxpbms6IFwiL2d1aWRlL2Rlc2lnblwiLCBhY3RpdmVNYXRjaDogXCJeL2d1aWRlL1wiIH0sXHJcbiAgeyB0ZXh0OiBcIlFGb3JtXCIsIGxpbms6IFwiL2NvbXBvbmVudHMvcXVpY2stZm9ybS9pbmRleFwiIH0sXHJcbiAgeyB0ZXh0OiBcIlFUYWJsZVwiLCBsaW5rOiBcIi9jb21wb25lbnRzL3F1aWNrLXRhYmxlL2luZGV4XCIgfSxcclxuICB7IHRleHQ6IFwiRWxlbWVudC1QbHVzXHU1Qjk4XHU3RjUxXCIsIGxpbms6IFwiaHR0cHM6Ly9lbGVtZW50LXBsdXMub3JnL3poLUNOL1wiLCBpY29uOiAnaHR0cHM6Ly9lbGVtZW50LXBsdXMub3JnL2ltYWdlcy9lbGVtZW50LXBsdXMtbG9nby1zbWFsbC5zdmcnIH0sXHJcbl07XHJcbiIsICJjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZGlybmFtZSA9IFwiRTpcXFxcd29ya3NwYWNlXFxcXGx1eWFuYW5cXFxccXVpY2stYm9vdFxcXFxxdWljay11aVxcXFxkb2NzXFxcXC52aXRlcHJlc3NcXFxcY29uZmlnXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ZpbGVuYW1lID0gXCJFOlxcXFx3b3Jrc3BhY2VcXFxcbHV5YW5hblxcXFxxdWljay1ib290XFxcXHF1aWNrLXVpXFxcXGRvY3NcXFxcLnZpdGVwcmVzc1xcXFxjb25maWdcXFxccGx1Z2lucy50c1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vRTovd29ya3NwYWNlL2x1eWFuYW4vcXVpY2stYm9vdC9xdWljay11aS9kb2NzLy52aXRlcHJlc3MvY29uZmlnL3BsdWdpbnMudHNcIjtpbXBvcnQgcGF0aCBmcm9tIFwicGF0aFwiO1xyXG5pbXBvcnQgZnMgZnJvbSBcImZzXCI7XHJcbi8vIEB0cy1pZ25vcmVcclxuaW1wb3J0IE1hcmtkb3duSXQgZnJvbSBcIm1hcmtkb3duLWl0XCI7XHJcbi8vIEB0cy1pZ25vcmVcclxuaW1wb3J0IG1kQ29udGFpbmVyIGZyb20gXCJtYXJrZG93bi1pdC1jb250YWluZXJcIjtcclxuLy8gQHRzLWlnbm9yZVxyXG5pbXBvcnQgdHlwZSBUb2tlbiBmcm9tIFwibWFya2Rvd24taXQvbGliL3Rva2VuXCI7XHJcbmltcG9ydCB7IGhpZ2hsaWdodCB9IGZyb20gXCIuLi91dGlscy9oaWdobGlnaHRcIjtcclxuaW1wb3J0IHsgZG9jUm9vdCxwcm9qUm9vdCxleGFtcGxlRGlyIH0gZnJvbSBcIi4uL3RoZW1lL2dsb2JhbFwiO1xyXG5pbXBvcnQgZXh0ZXJuYWxMaW5rSWNvbiBmcm9tICcuLi9wbHVnaW5zL2V4dGVybmFsLWxpbmstaWNvbidcclxuaW1wb3J0IHRvb2x0aXAgZnJvbSAnLi4vcGx1Z2lucy90b29sdGlwJ1xyXG5pbXBvcnQgdGFibGVXcmFwcGVyIGZyb20gXCIuLi9wbHVnaW5zL3RhYmxlLXdyYXBwZXJcIjtcclxuaW1wb3J0IHRhZyBmcm9tICcuLi9wbHVnaW5zL3RhZydcclxuaW1wb3J0IHsgQXBpVGFibGVDb250YWluZXIgfSBmcm9tICcuLi9wbHVnaW5zL2FwaS10YWJsZSdcclxuXHJcbmNvbnN0IGxvY2FsTWQgPSBNYXJrZG93bkl0KCk7XHJcblxyXG5pbnRlcmZhY2UgQ29udGFpbmVyT3B0cyB7XHJcbiAgbWFya2VyPzogc3RyaW5nIHwgdW5kZWZpbmVkO1xyXG4gIHZhbGlkYXRlPyhwYXJhbXM6IHN0cmluZyk6IGJvb2xlYW47XHJcbiAgcmVuZGVyPyh0b2tlbnM6IFRva2VuW10sIGluZGV4OiBudW1iZXIpOiBzdHJpbmc7XHJcbn1cclxuZXhwb3J0IGNvbnN0IG1kUGx1Z2luID0gKG1kOiBNYXJrZG93bkl0KSA9PiB7XHJcbiAgbWQudXNlKGV4dGVybmFsTGlua0ljb24pXHJcbiAgbWQudXNlKHRvb2x0aXApXHJcbiAgbWQudXNlKHRhYmxlV3JhcHBlcilcclxuICBtZC51c2UodGFnKVxyXG4gIG1kLnVzZShtZENvbnRhaW5lciwgXCJkZW1vXCIsIHtcclxuICAgIHZhbGlkYXRlKHBhcmFtcykge1xyXG4gICAgICByZXR1cm4gISFwYXJhbXMudHJpbSgpLm1hdGNoKC9eZGVtb1xccyooLiopJC8pO1xyXG4gICAgfSxcclxuICAgIHJlbmRlcih0b2tlbnMsIGlkeCkge1xyXG4gICAgICBjb25zdCBtID0gdG9rZW5zW2lkeF0uaW5mby50cmltKCkubWF0Y2goL15kZW1vXFxzKyguKikkLyk7XHJcbiAgICAgIGlmICh0b2tlbnNbaWR4XS5uZXN0aW5nID09PSAxKSB7XHJcbiAgICAgICAgY29uc3QgZGVzY3JpcHRpb24gPSBtICYmIG0ubGVuZ3RoID4gMSA/IG1bMV0gOiBcIlwiO1xyXG4gICAgICAgIGNvbnN0IHNvdXJjZUZpbGVUb2tlbiA9IHRva2Vuc1tpZHggKyAyXTtcclxuICAgICAgICBsZXQgc291cmNlID0gXCJcIjtcclxuICAgICAgICAvLyBkZW1vXHU2NTg3XHU0RUY2XHU1NDBEXHU3OUYwXHJcbiAgICAgICAgY29uc3Qgc291cmNlRmlsZSA9IHNvdXJjZUZpbGVUb2tlbi5jaGlsZHJlbj8uWzBdLmNvbnRlbnQgPz8gXCJcIjtcclxuICAgICAgICBpZiAoc291cmNlRmlsZVRva2VuLnR5cGUgPT09IFwiaW5saW5lXCIpIHtcclxuICAgICAgICAgIC8vIFx1OEJGQlx1NTNENlx1NzkzQVx1NTIxN1x1NEVFM1x1NzgwMVx1NjU4N1x1NEVGNlxyXG4gICAgICAgICAgc291cmNlID0gZnMucmVhZEZpbGVTeW5jKFxyXG4gICAgICAgICAgICBwYXRoLnJlc29sdmUoZXhhbXBsZURpciwgYCR7c291cmNlRmlsZX0udnVlYCksXHJcbiAgICAgICAgICAgIFwidXRmLThcIlxyXG4gICAgICAgICAgKTtcclxuICAgICAgICB9XHJcbiAgICAgICAgaWYgKCFzb3VyY2UpIHRocm93IG5ldyBFcnJvcihgSW5jb3JyZWN0IHNvdXJjZSBmaWxlOiAke3NvdXJjZUZpbGV9YCk7XHJcbiAgICAgICAgLy8gb3BlbmluZyB0YWdcclxuICAgICAgICByZXR1cm4gYDxEZW1vXHJcbiAgICAgICAgICAgICAgICAgICAgICAgIHNvdXJjZT1cIiR7ZW5jb2RlVVJJQ29tcG9uZW50KGhpZ2hsaWdodChzb3VyY2UsIFwidnVlXCIpKX1cIlxyXG4gICAgICAgICAgICAgICAgICAgICAgICBwYXRoPVwiJHtzb3VyY2VGaWxlfVwiXHJcbiAgICAgICAgICAgICAgICAgICAgICAgIHJhdy1zb3VyY2U9XCIke2VuY29kZVVSSUNvbXBvbmVudChzb3VyY2UpfVwiXHJcbiAgICAgICAgICAgICAgICAgICAgICAgIGRlc2NyaXB0aW9uPVwiJHtlbmNvZGVVUklDb21wb25lbnQoXHJcbiAgICAgICAgICBsb2NhbE1kLnJlbmRlcihkZXNjcmlwdGlvbilcclxuICAgICAgICApfVwiPmA7XHJcbiAgICAgIH0gZWxzZSB7XHJcbiAgICAgICAgLy8gY2xvc2luZyB0YWdcclxuICAgICAgICByZXR1cm4gXCI8L0RlbW8+XFxuXCI7XHJcbiAgICAgIH1cclxuICAgIH0sXHJcbiAgfSBhcyBDb250YWluZXJPcHRzKTtcclxuICBtZC51c2UoQXBpVGFibGVDb250YWluZXIpXHJcbn07XHJcbiIsICJjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZGlybmFtZSA9IFwiRTpcXFxcd29ya3NwYWNlXFxcXGx1eWFuYW5cXFxccXVpY2stYm9vdFxcXFxxdWljay11aVxcXFxkb2NzXFxcXC52aXRlcHJlc3NcXFxcdXRpbHNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZmlsZW5hbWUgPSBcIkU6XFxcXHdvcmtzcGFjZVxcXFxsdXlhbmFuXFxcXHF1aWNrLWJvb3RcXFxccXVpY2stdWlcXFxcZG9jc1xcXFwudml0ZXByZXNzXFxcXHV0aWxzXFxcXGhpZ2hsaWdodC50c1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vRTovd29ya3NwYWNlL2x1eWFuYW4vcXVpY2stYm9vdC9xdWljay11aS9kb2NzLy52aXRlcHJlc3MvdXRpbHMvaGlnaGxpZ2h0LnRzXCI7Ly8gXHU0RUUzXHU3ODAxXHU5QUQ4XHU0RUFFXHJcbmltcG9ydCBjaGFsayBmcm9tIFwiY2hhbGtcIjtcclxuLy8gQHRzLWlnbm9yZVxyXG5pbXBvcnQgZXNjYXBlSHRtbCBmcm9tIFwiZXNjYXBlLWh0bWxcIjtcclxuLy8gQHRzLWlnbm9yZVxyXG5pbXBvcnQgcHJpc20gZnJvbSBcInByaXNtanNcIjtcclxuaW1wb3J0IGNvbnNvbGEgZnJvbSBcImNvbnNvbGFcIjtcclxuXHJcbi8vIHByaXNtIGlzIGxpc3RlZCBhcyBhY3R1YWwgZGVwIHNvIGl0J3Mgb2sgdG8gcmVxdWlyZVxyXG4vLyBlc2xpbnQtZGlzYWJsZS1uZXh0LWxpbmUgQHR5cGVzY3JpcHQtZXNsaW50L25vLXZhci1yZXF1aXJlc1xyXG4vLyBjb25zdCBsb2FkTGFuZ3VhZ2VzID0gcmVxdWlyZSgncHJpc21qcy9jb21wb25lbnRzL2luZGV4JylcclxuaW1wb3J0IGxvYWRMYW5ndWFnZXMgZnJvbSBcInByaXNtanMvY29tcG9uZW50cy9pbmRleFwiO1xyXG4vLyByZXF1aXJlZCB0byBtYWtlIGVtYmVkZGVkIGhpZ2hsaWdodGluZyB3b3JrLi4uXHJcbmxvYWRMYW5ndWFnZXMoW1wibWFya3VwXCIsIFwiY3NzXCIsIFwiamF2YXNjcmlwdFwiXSk7XHJcblxyXG5mdW5jdGlvbiB3cmFwKGNvZGU6IHN0cmluZywgbGFuZzogc3RyaW5nKTogc3RyaW5nIHtcclxuICBpZiAobGFuZyA9PT0gXCJ0ZXh0XCIpIHtcclxuICAgIGNvZGUgPSBlc2NhcGVIdG1sKGNvZGUpO1xyXG4gIH1cclxuICByZXR1cm4gYDxwcmUgdi1wcmU+PGNvZGU+JHtjb2RlfTwvY29kZT48L3ByZT5gO1xyXG59XHJcblxyXG5leHBvcnQgY29uc3QgaGlnaGxpZ2h0ID0gKHN0cjogc3RyaW5nLCBsYW5nOiBzdHJpbmcpID0+IHtcclxuICBpZiAoIWxhbmcpIHtcclxuICAgIHJldHVybiB3cmFwKHN0ciwgXCJ0ZXh0XCIpO1xyXG4gIH1cclxuICBsYW5nID0gbGFuZy50b0xvd2VyQ2FzZSgpO1xyXG4gIGNvbnN0IHJhd0xhbmcgPSBsYW5nO1xyXG4gIGlmIChsYW5nID09PSBcInZ1ZVwiIHx8IGxhbmcgPT09IFwiaHRtbFwiKSB7XHJcbiAgICBsYW5nID0gXCJtYXJrdXBcIjtcclxuICB9XHJcbiAgaWYgKGxhbmcgPT09IFwibWRcIikge1xyXG4gICAgbGFuZyA9IFwibWFya2Rvd25cIjtcclxuICB9XHJcbiAgaWYgKGxhbmcgPT09IFwidHNcIikge1xyXG4gICAgbGFuZyA9IFwidHlwZXNjcmlwdFwiO1xyXG4gIH1cclxuICBpZiAobGFuZyA9PT0gXCJweVwiKSB7XHJcbiAgICBsYW5nID0gXCJweXRob25cIjtcclxuICB9XHJcbiAgaWYgKCFwcmlzbS5sYW5ndWFnZXNbbGFuZ10pIHtcclxuICAgIHRyeSB7XHJcbiAgICAgIGxvYWRMYW5ndWFnZXMoW2xhbmddKTtcclxuICAgIH0gY2F0Y2gge1xyXG4gICAgICAvLyBlc2xpbnQtZGlzYWJsZS1uZXh0LWxpbmUgbm8tY29uc29sZVxyXG4gICAgICBjb25zb2xhLndhcm4oXHJcbiAgICAgICAgY2hhbGsueWVsbG93KFxyXG4gICAgICAgICAgYFt2aXRlcHJlc3NdIFN5bnRheCBoaWdobGlnaHQgZm9yIGxhbmd1YWdlIFwiJHtsYW5nfVwiIGlzIG5vdCBzdXBwb3J0ZWQuYFxyXG4gICAgICAgIClcclxuICAgICAgKTtcclxuICAgIH1cclxuICB9XHJcbiAgaWYgKHByaXNtLmxhbmd1YWdlc1tsYW5nXSkge1xyXG4gICAgY29uc3QgY29kZSA9IHByaXNtLmhpZ2hsaWdodChzdHIsIHByaXNtLmxhbmd1YWdlc1tsYW5nXSwgbGFuZyk7XHJcbiAgICByZXR1cm4gd3JhcChjb2RlLCByYXdMYW5nKTtcclxuICB9XHJcbiAgcmV0dXJuIHdyYXAoc3RyLCBcInRleHRcIik7XHJcbn07XHJcbiIsICJjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZGlybmFtZSA9IFwiRTpcXFxcd29ya3NwYWNlXFxcXGx1eWFuYW5cXFxccXVpY2stYm9vdFxcXFxxdWljay11aVxcXFxkb2NzXFxcXC52aXRlcHJlc3NcXFxcdGhlbWVcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZmlsZW5hbWUgPSBcIkU6XFxcXHdvcmtzcGFjZVxcXFxsdXlhbmFuXFxcXHF1aWNrLWJvb3RcXFxccXVpY2stdWlcXFxcZG9jc1xcXFwudml0ZXByZXNzXFxcXHRoZW1lXFxcXGdsb2JhbC50c1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vRTovd29ya3NwYWNlL2x1eWFuYW4vcXVpY2stYm9vdC9xdWljay11aS9kb2NzLy52aXRlcHJlc3MvdGhlbWUvZ2xvYmFsLnRzXCI7aW1wb3J0IHtyZXNvbHZlfSBmcm9tIFwicGF0aFwiO1xyXG4vLyBcdTk4NzlcdTc2RUVcdTc2RUVcdTVGNTVcclxuZXhwb3J0IGNvbnN0IHByb2pSb290ID0gcmVzb2x2ZShfX2Rpcm5hbWUsIFwiLi5cIiwgXCIuLlwiLCBcIi4uXCIpO1xyXG4vLyBcdTk4NzlcdTc2RUVcdTU0MERcdTc5RjBcclxuZXhwb3J0IGNvbnN0IGRvY3NEaXJOYW1lID0gXCJkb2NzXCI7XHJcbi8vIFx1NjU4N1x1Njg2M1x1NUU5M1x1NzZFRVx1NUY1NVxyXG5leHBvcnQgY29uc3QgZG9jUm9vdCA9IHJlc29sdmUocHJvalJvb3QsIGRvY3NEaXJOYW1lKTtcclxuXHJcbi8vIGV4YW1wbGVcdTRGOEJcdTVCNTBcclxuZXhwb3J0ICBjb25zdCAgZXhhbXBsZURpciA9IHJlc29sdmUocHJvalJvb3QsIFwic3JjL3ZpZXdzL2RlbW9cIik7XHJcbiIsICJjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZGlybmFtZSA9IFwiRTpcXFxcd29ya3NwYWNlXFxcXGx1eWFuYW5cXFxccXVpY2stYm9vdFxcXFxxdWljay11aVxcXFxkb2NzXFxcXC52aXRlcHJlc3NcXFxccGx1Z2luc1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9maWxlbmFtZSA9IFwiRTpcXFxcd29ya3NwYWNlXFxcXGx1eWFuYW5cXFxccXVpY2stYm9vdFxcXFxxdWljay11aVxcXFxkb2NzXFxcXC52aXRlcHJlc3NcXFxccGx1Z2luc1xcXFxleHRlcm5hbC1saW5rLWljb24udHNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfaW1wb3J0X21ldGFfdXJsID0gXCJmaWxlOi8vL0U6L3dvcmtzcGFjZS9sdXlhbmFuL3F1aWNrLWJvb3QvcXVpY2stdWkvZG9jcy8udml0ZXByZXNzL3BsdWdpbnMvZXh0ZXJuYWwtbGluay1pY29uLnRzXCI7aW1wb3J0IHR5cGUgTWFya2Rvd25JdCBmcm9tICdtYXJrZG93bi1pdCdcclxuaW1wb3J0IHR5cGUgUmVuZGVyZXIgZnJvbSAnbWFya2Rvd24taXQvbGliL3JlbmRlcmVyJ1xyXG5cclxuZXhwb3J0IGRlZmF1bHQgKG1kOiBNYXJrZG93bkl0KTogdm9pZCA9PiB7XHJcbiAgICBjb25zdCByZW5kZXJUb2tlbjogUmVuZGVyZXIuUmVuZGVyUnVsZSA9ICh0b2tlbnMsIGlkeCwgb3B0aW9ucywgZW52LCBzZWxmKSA9PlxyXG4gICAgICAgIHNlbGYucmVuZGVyVG9rZW4odG9rZW5zLCBpZHgsIG9wdGlvbnMpXHJcbiAgICBjb25zdCBkZWZhdWx0TGlua09wZW5SZW5kZXJlciA9IG1kLnJlbmRlcmVyLnJ1bGVzLmxpbmtfb3BlbiB8fCByZW5kZXJUb2tlblxyXG4gICAgY29uc3QgZGVmYXVsdExpbmtDbG9zZVJlbmRlcmVyID0gbWQucmVuZGVyZXIucnVsZXMubGlua19jbG9zZSB8fCByZW5kZXJUb2tlblxyXG4gICAgbGV0IGlzRXh0ZXJuYWxMaW5rID0gZmFsc2VcclxuXHJcbiAgICBtZC5yZW5kZXJlci5ydWxlcy5saW5rX29wZW4gPSAodG9rZW5zLCBpZHgsIG9wdGlvbnMsIGVudiwgc2VsZikgPT4ge1xyXG4gICAgICAgIGNvbnN0IHRva2VuID0gdG9rZW5zW2lkeF1cclxuICAgICAgICBjb25zdCBocmVmID0gdG9rZW4uYXR0ckdldCgnaHJlZicpXHJcblxyXG4gICAgICAgIGlmIChocmVmKSB7XHJcbiAgICAgICAgICAgIHRva2VuLmF0dHJKb2luKCdjbGFzcycsICd2cC1saW5rJylcclxuICAgICAgICAgICAgaWYgKC9eKChodHxmKXRwcz8pOlxcL1xcLz8vLnRlc3QoaHJlZikpIHtcclxuICAgICAgICAgICAgICAgIGlzRXh0ZXJuYWxMaW5rID0gdHJ1ZVxyXG4gICAgICAgICAgICB9XHJcbiAgICAgICAgfVxyXG5cclxuICAgICAgICByZXR1cm4gZGVmYXVsdExpbmtPcGVuUmVuZGVyZXIodG9rZW5zLCBpZHgsIG9wdGlvbnMsIGVudiwgc2VsZilcclxuICAgIH1cclxuXHJcbiAgICBtZC5yZW5kZXJlci5ydWxlcy5saW5rX2Nsb3NlID0gKHRva2VucywgaWR4LCBvcHRpb25zLCBlbnYsIHNlbGYpID0+IHtcclxuICAgICAgICBpZiAoaXNFeHRlcm5hbExpbmspIHtcclxuICAgICAgICAgICAgaXNFeHRlcm5hbExpbmsgPSBmYWxzZVxyXG4gICAgICAgICAgICByZXR1cm4gYDxpLXJpLWV4dGVybmFsLWxpbmstbGluZSBjbGFzcz1cImxpbmstaWNvblwiIC8+JHtzZWxmLnJlbmRlclRva2VuKFxyXG4gICAgICAgICAgICAgICAgdG9rZW5zLFxyXG4gICAgICAgICAgICAgICAgaWR4LFxyXG4gICAgICAgICAgICAgICAgb3B0aW9uc1xyXG4gICAgICAgICAgICApfWBcclxuICAgICAgICB9XHJcblxyXG4gICAgICAgIHJldHVybiBkZWZhdWx0TGlua0Nsb3NlUmVuZGVyZXIodG9rZW5zLCBpZHgsIG9wdGlvbnMsIGVudiwgc2VsZilcclxuICAgIH1cclxufSIsICJjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZGlybmFtZSA9IFwiRTpcXFxcd29ya3NwYWNlXFxcXGx1eWFuYW5cXFxccXVpY2stYm9vdFxcXFxxdWljay11aVxcXFxkb2NzXFxcXC52aXRlcHJlc3NcXFxccGx1Z2luc1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9maWxlbmFtZSA9IFwiRTpcXFxcd29ya3NwYWNlXFxcXGx1eWFuYW5cXFxccXVpY2stYm9vdFxcXFxxdWljay11aVxcXFxkb2NzXFxcXC52aXRlcHJlc3NcXFxccGx1Z2luc1xcXFx0b29sdGlwLnRzXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ltcG9ydF9tZXRhX3VybCA9IFwiZmlsZTovLy9FOi93b3Jrc3BhY2UvbHV5YW5hbi9xdWljay1ib290L3F1aWNrLXVpL2RvY3MvLnZpdGVwcmVzcy9wbHVnaW5zL3Rvb2x0aXAudHNcIjtpbXBvcnQgdHlwZSBNYXJrZG93bkl0IGZyb20gJ21hcmtkb3duLWl0J1xyXG5cclxuZXhwb3J0IGRlZmF1bHQgKG1kOiBNYXJrZG93bkl0KTogdm9pZCA9PiB7XHJcbiAgICBtZC5yZW5kZXJlci5ydWxlcy50b29sdGlwID0gKHRva2VucywgaWR4KSA9PiB7XHJcbiAgICAgICAgY29uc3QgdG9rZW4gPSB0b2tlbnNbaWR4XVxyXG5cclxuICAgICAgICByZXR1cm4gYDxhcGktdHlwaW5nIHR5cGU9XCIke3Rva2VuLmNvbnRlbnR9XCIgZGV0YWlscz1cIiR7dG9rZW4uaW5mb31cIiAvPmBcclxuICAgIH1cclxuXHJcbiAgICBtZC5pbmxpbmUucnVsZXIuYmVmb3JlKCdlbXBoYXNpcycsICd0b29sdGlwJywgKHN0YXRlLCBzaWxlbnQpID0+IHtcclxuICAgICAgICBjb25zdCB0b29sdGlwUmVnRXhwID0gL15cXF5cXFsoW15cXF1dKilcXF0oYFteYF0qYCk/L1xyXG4gICAgICAgIGNvbnN0IHN0ciA9IHN0YXRlLnNyYy5zbGljZShzdGF0ZS5wb3MsIHN0YXRlLnBvc01heClcclxuXHJcbiAgICAgICAgaWYgKCF0b29sdGlwUmVnRXhwLnRlc3Qoc3RyKSkgcmV0dXJuIGZhbHNlXHJcbiAgICAgICAgaWYgKHNpbGVudCkgcmV0dXJuIHRydWVcclxuXHJcbiAgICAgICAgY29uc3QgcmVzdWx0ID0gc3RyLm1hdGNoKHRvb2x0aXBSZWdFeHApXHJcblxyXG4gICAgICAgIGlmICghcmVzdWx0KSByZXR1cm4gZmFsc2VcclxuXHJcbiAgICAgICAgY29uc3QgdG9rZW4gPSBzdGF0ZS5wdXNoKCd0b29sdGlwJywgJ3Rvb2x0aXAnLCAwKVxyXG4gICAgICAgIHRva2VuLmNvbnRlbnQgPSByZXN1bHRbMV0ucmVwbGFjZSgvXFxcXFxcfC9nLCAnfCcpXHJcbiAgICAgICAgdG9rZW4uaW5mbyA9IChyZXN1bHRbMl0gfHwgJycpLnJlcGxhY2UoL15gKC4qKWAkLywgJyQxJylcclxuICAgICAgICB0b2tlbi5sZXZlbCA9IHN0YXRlLmxldmVsXHJcbiAgICAgICAgc3RhdGUucG9zICs9IHJlc3VsdFswXS5sZW5ndGhcclxuXHJcbiAgICAgICAgcmV0dXJuIHRydWVcclxuICAgIH0pXHJcbn1cclxuIiwgImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJFOlxcXFx3b3Jrc3BhY2VcXFxcbHV5YW5hblxcXFxxdWljay1ib290XFxcXHF1aWNrLXVpXFxcXGRvY3NcXFxcLnZpdGVwcmVzc1xcXFxwbHVnaW5zXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ZpbGVuYW1lID0gXCJFOlxcXFx3b3Jrc3BhY2VcXFxcbHV5YW5hblxcXFxxdWljay1ib290XFxcXHF1aWNrLXVpXFxcXGRvY3NcXFxcLnZpdGVwcmVzc1xcXFxwbHVnaW5zXFxcXHRhYmxlLXdyYXBwZXIudHNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfaW1wb3J0X21ldGFfdXJsID0gXCJmaWxlOi8vL0U6L3dvcmtzcGFjZS9sdXlhbmFuL3F1aWNrLWJvb3QvcXVpY2stdWkvZG9jcy8udml0ZXByZXNzL3BsdWdpbnMvdGFibGUtd3JhcHBlci50c1wiO2ltcG9ydCB0eXBlIE1hcmtkb3duSXQgZnJvbSAnbWFya2Rvd24taXQnXHJcblxyXG5leHBvcnQgZGVmYXVsdCAobWQ6IE1hcmtkb3duSXQpOiB2b2lkID0+IHtcclxuICAgIG1kLnJlbmRlcmVyLnJ1bGVzLnRhYmxlX29wZW4gPSAoKSA9PiAnPGRpdiBjbGFzcz1cInZwLXRhYmxlXCI+PHRhYmxlPidcclxuICAgIG1kLnJlbmRlcmVyLnJ1bGVzLnRhYmxlX2Nsb3NlID0gKCkgPT4gJzwvdGFibGU+PC9kaXY+J1xyXG59XHJcbiIsICJjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZGlybmFtZSA9IFwiRTpcXFxcd29ya3NwYWNlXFxcXGx1eWFuYW5cXFxccXVpY2stYm9vdFxcXFxxdWljay11aVxcXFxkb2NzXFxcXC52aXRlcHJlc3NcXFxccGx1Z2luc1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9maWxlbmFtZSA9IFwiRTpcXFxcd29ya3NwYWNlXFxcXGx1eWFuYW5cXFxccXVpY2stYm9vdFxcXFxxdWljay11aVxcXFxkb2NzXFxcXC52aXRlcHJlc3NcXFxccGx1Z2luc1xcXFx0YWcudHNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfaW1wb3J0X21ldGFfdXJsID0gXCJmaWxlOi8vL0U6L3dvcmtzcGFjZS9sdXlhbmFuL3F1aWNrLWJvb3QvcXVpY2stdWkvZG9jcy8udml0ZXByZXNzL3BsdWdpbnMvdGFnLnRzXCI7aW1wb3J0IHR5cGUgTWFya2Rvd25JdCBmcm9tICdtYXJrZG93bi1pdCdcclxuXHJcbmV4cG9ydCBkZWZhdWx0IChtZDogTWFya2Rvd25JdCk6IHZvaWQgPT4ge1xyXG4gICAgLyoqXHJcbiAgICAgKiBUbyBlbmFibGUgdGhlIHBsdWdpbiB0byBiZSBwYXJzZWQgaW4gdGhlIGRlbW8gZGVzY3JpcHRpb24sIHRoZSBjb250ZW50IGlzIHJlbmRlcmVkIGFzIHNwYW4gaW5zdGVhZCBvZiBFbFRhZy5cclxuICAgICAqL1xyXG4gICAgbWQucmVuZGVyZXIucnVsZXMudGFnID0gKHRva2VucywgaWR4KSA9PiB7XHJcbiAgICAgICAgY29uc3QgdG9rZW4gPSB0b2tlbnNbaWR4XVxyXG4gICAgICAgIGNvbnN0IHZhbHVlID0gdG9rZW4uY29udGVudFxyXG4gICAgICAgIC8qKlxyXG4gICAgICAgICAqIEFkZCBzdHlsZXMgZm9yIHNvbWUgc3BlY2lhbCB0YWdzXHJcbiAgICAgICAgICogdml0ZXByZXNzL3N0eWxlcy9jb250ZW50L3RhZy1jb250ZW50LnNjc3NcclxuICAgICAgICAgKi9cclxuICAgICAgICBjb25zdCB0YWdDbGFzcyA9IFsnYmV0YScsICdkZXByZWNhdGVkJywgJ2ExMXknLCAncmVxdWlyZWQnXS5pbmNsdWRlcyh2YWx1ZSlcclxuICAgICAgICAgICAgPyB2YWx1ZVxyXG4gICAgICAgICAgICA6ICcnXHJcbiAgICAgICAgcmV0dXJuIGA8c3BhbiBjbGFzcz1cInZwLXRhZyAke3RhZ0NsYXNzfVwiPiR7dmFsdWV9PC9zcGFuPmBcclxuICAgIH1cclxuXHJcbiAgICBtZC5pbmxpbmUucnVsZXIuYmVmb3JlKCdlbXBoYXNpcycsICd0YWcnLCAoc3RhdGUsIHNpbGVudCkgPT4ge1xyXG4gICAgICAgIGNvbnN0IHRhZ1JlZ0V4cCA9IC9eXFxeXFwoKFteKV0qKVxcKS9cclxuICAgICAgICBjb25zdCBzdHIgPSBzdGF0ZS5zcmMuc2xpY2Uoc3RhdGUucG9zLCBzdGF0ZS5wb3NNYXgpXHJcblxyXG4gICAgICAgIGlmICghdGFnUmVnRXhwLnRlc3Qoc3RyKSkgcmV0dXJuIGZhbHNlXHJcbiAgICAgICAgaWYgKHNpbGVudCkgcmV0dXJuIHRydWVcclxuXHJcbiAgICAgICAgY29uc3QgcmVzdWx0ID0gc3RyLm1hdGNoKHRhZ1JlZ0V4cClcclxuXHJcbiAgICAgICAgaWYgKCFyZXN1bHQpIHJldHVybiBmYWxzZVxyXG5cclxuICAgICAgICBjb25zdCB0b2tlbiA9IHN0YXRlLnB1c2goJ3RhZycsICd0YWcnLCAwKVxyXG4gICAgICAgIHRva2VuLmNvbnRlbnQgPSByZXN1bHRbMV0udHJpbSgpXHJcbiAgICAgICAgdG9rZW4ubGV2ZWwgPSBzdGF0ZS5sZXZlbFxyXG4gICAgICAgIHN0YXRlLnBvcyArPSByZXN1bHRbMF0ubGVuZ3RoXHJcblxyXG4gICAgICAgIHJldHVybiB0cnVlXHJcbiAgICB9KVxyXG59XHJcbiIsICJjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZGlybmFtZSA9IFwiRTpcXFxcd29ya3NwYWNlXFxcXGx1eWFuYW5cXFxccXVpY2stYm9vdFxcXFxxdWljay11aVxcXFxkb2NzXFxcXC52aXRlcHJlc3NcXFxccGx1Z2luc1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9maWxlbmFtZSA9IFwiRTpcXFxcd29ya3NwYWNlXFxcXGx1eWFuYW5cXFxccXVpY2stYm9vdFxcXFxxdWljay11aVxcXFxkb2NzXFxcXC52aXRlcHJlc3NcXFxccGx1Z2luc1xcXFxhcGktdGFibGUudHNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfaW1wb3J0X21ldGFfdXJsID0gXCJmaWxlOi8vL0U6L3dvcmtzcGFjZS9sdXlhbmFuL3F1aWNrLWJvb3QvcXVpY2stdWkvZG9jcy8udml0ZXByZXNzL3BsdWdpbnMvYXBpLXRhYmxlLnRzXCI7aW1wb3J0IG1hcmtkb3duIGZyb20gJ21hcmtkb3duLWl0J1xyXG5pbXBvcnQgdHlwZSBNYXJrZG93bkl0IGZyb20gJ21hcmtkb3duLWl0J1xyXG5cclxuY29uc3QgQXBpTWQgPSBuZXcgbWFya2Rvd24oKVxyXG5cclxuZXhwb3J0IGNvbnN0IEFwaVRhYmxlQ29udGFpbmVyID0gKG1kOiBNYXJrZG93bkl0KSA9PiB7XHJcbiAgICBjb25zdCBmZW5jZSA9IG1kLnJlbmRlcmVyLnJ1bGVzLmZlbmNlIVxyXG5cclxuICAgIEFwaU1kLnJlbmRlcmVyLnJ1bGVzID0gbWQucmVuZGVyZXIucnVsZXNcclxuICAgIG1kLnJlbmRlcmVyLnJ1bGVzLmZlbmNlID0gKC4uLmFyZ3MpID0+IHtcclxuICAgICAgICBjb25zdCBbdG9rZW5zLCBpZHgsIC4uLnJlc3RdID0gYXJnc1xyXG4gICAgICAgIGNvbnN0IFtvcHRpb25zLCBlbnZdID0gcmVzdFxyXG4gICAgICAgIGNvbnN0IHRva2VuID0gdG9rZW5zW2lkeF1cclxuICAgICAgICBpZiAodG9rZW4uaW5mbyA9PT0gJ2FwaScpIHtcclxuICAgICAgICAgICAgY29uc3QgbmV3VG9rZW5zID0gbWQucGFyc2UodG9rZW4uY29udGVudCwgZW52KVxyXG5cclxuICAgICAgICAgICAgbGV0IHJlc3VsdCA9ICcnXHJcbiAgICAgICAgICAgIGNvbnN0IHsgcnVsZXMgfSA9IG1kLnJlbmRlcmVyXHJcbiAgICAgICAgICAgIG5ld1Rva2Vucy5mb3JFYWNoKChuZXdUb2tlbiwgaWR4KSA9PiB7XHJcbiAgICAgICAgICAgICAgICBjb25zdCB7IHR5cGUgfSA9IG5ld1Rva2VuXHJcbiAgICAgICAgICAgICAgICBpZiAodHlwZSA9PT0gJ2lubGluZScpIHtcclxuICAgICAgICAgICAgICAgICAgICByZXN1bHQgKz0gbWQucmVuZGVyZXIucmVuZGVySW5saW5lKG5ld1Rva2VuLmNoaWxkcmVuISwgb3B0aW9ucywgZW52KVxyXG4gICAgICAgICAgICAgICAgfSBlbHNlIGlmICh0eXBlb2YgcnVsZXNbdHlwZV0gIT09ICd1bmRlZmluZWQnKSB7XHJcbiAgICAgICAgICAgICAgICAgICAgcmVzdWx0ICs9IHJ1bGVzW25ld1Rva2VuLnR5cGVdIShcclxuICAgICAgICAgICAgICAgICAgICAgICAgbmV3VG9rZW5zLFxyXG4gICAgICAgICAgICAgICAgICAgICAgICBpZHgsXHJcbiAgICAgICAgICAgICAgICAgICAgICAgIG9wdGlvbnMsXHJcbiAgICAgICAgICAgICAgICAgICAgICAgIGVudixcclxuICAgICAgICAgICAgICAgICAgICAgICAgbWQucmVuZGVyZXJcclxuICAgICAgICAgICAgICAgICAgICApXHJcbiAgICAgICAgICAgICAgICB9IGVsc2Uge1xyXG4gICAgICAgICAgICAgICAgICAgIHJlc3VsdCArPSBtZC5yZW5kZXJlci5yZW5kZXJUb2tlbihuZXdUb2tlbnMsIGlkeCwgb3B0aW9ucylcclxuICAgICAgICAgICAgICAgIH1cclxuICAgICAgICAgICAgfSlcclxuICAgICAgICAgICAgcmV0dXJuIHJlc3VsdFxyXG4gICAgICAgIH1cclxuICAgICAgICByZXR1cm4gZmVuY2UuY2FsbChtZCwgLi4uYXJncylcclxuICAgIH1cclxufVxyXG4iXSwKICAibWFwcGluZ3MiOiAiO0FBQThWLFNBQVMsb0JBQW9CO0FBRTNYLE9BQU9BLFdBQVU7QUFFakIsT0FBTyxZQUFZOzs7QUNKb1csSUFBTyxrQkFBUTtBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBLEVBVXBZLFdBQVc7QUFBQSxJQUNULEVBQUUsTUFBTSxnQkFBTSxNQUFNLGdCQUFnQjtBQUFBLElBQ3BDO0FBQUEsTUFDRSxNQUFNO0FBQUEsTUFDTixPQUFPO0FBQUEsUUFDTCxFQUFFLE1BQU0sZ0JBQU0sTUFBTSxzQkFBc0I7QUFBQSxRQUMxQyxFQUFFLE1BQU0sNEJBQVEsTUFBTSxvQkFBb0I7QUFBQSxNQUM1QztBQUFBLElBQ0Y7QUFBQSxJQUNBLEVBQUUsTUFBTSw0QkFBUSxNQUFNLG1CQUFtQjtBQUFBLEVBQzNDO0FBQ0Y7OztBQ3JCK1csSUFBTyxjQUFRO0FBQUEsRUFDNVgsRUFBRSxNQUFNLGdCQUFNLE1BQU0saUJBQWlCLGFBQWEsV0FBVztBQUFBLEVBQzdELEVBQUUsTUFBTSxTQUFTLE1BQU0sK0JBQStCO0FBQUEsRUFDdEQsRUFBRSxNQUFNLFVBQVUsTUFBTSxnQ0FBZ0M7QUFBQSxFQUN4RCxFQUFFLE1BQU0sNEJBQWtCLE1BQU0sbUNBQW1DLE1BQU0sOERBQThEO0FBQ3pJOzs7QUNMdVgsT0FBTyxVQUFVO0FBQ3hZLE9BQU8sUUFBUTtBQUVmLE9BQU8sZ0JBQWdCO0FBRXZCLE9BQU8saUJBQWlCOzs7QUNKeEIsT0FBTyxXQUFXO0FBRWxCLE9BQU8sZ0JBQWdCO0FBRXZCLE9BQU8sV0FBVztBQUNsQixPQUFPLGFBQWE7QUFLcEIsT0FBTyxtQkFBbUI7QUFFMUIsY0FBYyxDQUFDLFVBQVUsT0FBTyxZQUFZLENBQUM7QUFFN0MsU0FBUyxLQUFLLE1BQWMsTUFBc0I7QUFDaEQsTUFBSSxTQUFTLFFBQVE7QUFDbkIsV0FBTyxXQUFXLElBQUk7QUFBQSxFQUN4QjtBQUNBLFNBQU8sb0JBQW9CLElBQUk7QUFDakM7QUFFTyxJQUFNLFlBQVksQ0FBQyxLQUFhLFNBQWlCO0FBQ3RELE1BQUksQ0FBQyxNQUFNO0FBQ1QsV0FBTyxLQUFLLEtBQUssTUFBTTtBQUFBLEVBQ3pCO0FBQ0EsU0FBTyxLQUFLLFlBQVk7QUFDeEIsUUFBTSxVQUFVO0FBQ2hCLE1BQUksU0FBUyxTQUFTLFNBQVMsUUFBUTtBQUNyQyxXQUFPO0FBQUEsRUFDVDtBQUNBLE1BQUksU0FBUyxNQUFNO0FBQ2pCLFdBQU87QUFBQSxFQUNUO0FBQ0EsTUFBSSxTQUFTLE1BQU07QUFDakIsV0FBTztBQUFBLEVBQ1Q7QUFDQSxNQUFJLFNBQVMsTUFBTTtBQUNqQixXQUFPO0FBQUEsRUFDVDtBQUNBLE1BQUksQ0FBQyxNQUFNLFVBQVUsSUFBSSxHQUFHO0FBQzFCLFFBQUk7QUFDRixvQkFBYyxDQUFDLElBQUksQ0FBQztBQUFBLElBQ3RCLFFBQVE7QUFFTixjQUFRO0FBQUEsUUFDTixNQUFNO0FBQUEsVUFDSiw4Q0FBOEMsSUFBSTtBQUFBLFFBQ3BEO0FBQUEsTUFDRjtBQUFBLElBQ0Y7QUFBQSxFQUNGO0FBQ0EsTUFBSSxNQUFNLFVBQVUsSUFBSSxHQUFHO0FBQ3pCLFVBQU0sT0FBTyxNQUFNLFVBQVUsS0FBSyxNQUFNLFVBQVUsSUFBSSxHQUFHLElBQUk7QUFDN0QsV0FBTyxLQUFLLE1BQU0sT0FBTztBQUFBLEVBQzNCO0FBQ0EsU0FBTyxLQUFLLEtBQUssTUFBTTtBQUN6Qjs7O0FDekRrWCxTQUFRLGVBQWM7QUFBeFksSUFBTSxtQ0FBbUM7QUFFbEMsSUFBTSxXQUFXLFFBQVEsa0NBQVcsTUFBTSxNQUFNLElBQUk7QUFFcEQsSUFBTSxjQUFjO0FBRXBCLElBQU0sVUFBVSxRQUFRLFVBQVUsV0FBVztBQUc1QyxJQUFPLGFBQWEsUUFBUSxVQUFVLGdCQUFnQjs7O0FDTjlELElBQU8sNkJBQVEsQ0FBQyxPQUF5QjtBQUNyQyxRQUFNLGNBQW1DLENBQUMsUUFBUSxLQUFLLFNBQVMsS0FBSyxTQUNqRSxLQUFLLFlBQVksUUFBUSxLQUFLLE9BQU87QUFDekMsUUFBTSwwQkFBMEIsR0FBRyxTQUFTLE1BQU0sYUFBYTtBQUMvRCxRQUFNLDJCQUEyQixHQUFHLFNBQVMsTUFBTSxjQUFjO0FBQ2pFLE1BQUksaUJBQWlCO0FBRXJCLEtBQUcsU0FBUyxNQUFNLFlBQVksQ0FBQyxRQUFRLEtBQUssU0FBUyxLQUFLLFNBQVM7QUFDL0QsVUFBTSxRQUFRLE9BQU8sR0FBRztBQUN4QixVQUFNLE9BQU8sTUFBTSxRQUFRLE1BQU07QUFFakMsUUFBSSxNQUFNO0FBQ04sWUFBTSxTQUFTLFNBQVMsU0FBUztBQUNqQyxVQUFJLHNCQUFzQixLQUFLLElBQUksR0FBRztBQUNsQyx5QkFBaUI7QUFBQSxNQUNyQjtBQUFBLElBQ0o7QUFFQSxXQUFPLHdCQUF3QixRQUFRLEtBQUssU0FBUyxLQUFLLElBQUk7QUFBQSxFQUNsRTtBQUVBLEtBQUcsU0FBUyxNQUFNLGFBQWEsQ0FBQyxRQUFRLEtBQUssU0FBUyxLQUFLLFNBQVM7QUFDaEUsUUFBSSxnQkFBZ0I7QUFDaEIsdUJBQWlCO0FBQ2pCLGFBQU8sZ0RBQWdELEtBQUs7QUFBQSxRQUN4RDtBQUFBLFFBQ0E7QUFBQSxRQUNBO0FBQUEsTUFDSixDQUFDO0FBQUEsSUFDTDtBQUVBLFdBQU8seUJBQXlCLFFBQVEsS0FBSyxTQUFTLEtBQUssSUFBSTtBQUFBLEVBQ25FO0FBQ0o7OztBQ2xDQSxJQUFPLGtCQUFRLENBQUMsT0FBeUI7QUFDckMsS0FBRyxTQUFTLE1BQU0sVUFBVSxDQUFDLFFBQVEsUUFBUTtBQUN6QyxVQUFNLFFBQVEsT0FBTyxHQUFHO0FBRXhCLFdBQU8scUJBQXFCLE1BQU0sT0FBTyxjQUFjLE1BQU0sSUFBSTtBQUFBLEVBQ3JFO0FBRUEsS0FBRyxPQUFPLE1BQU0sT0FBTyxZQUFZLFdBQVcsQ0FBQyxPQUFPLFdBQVc7QUFDN0QsVUFBTSxnQkFBZ0I7QUFDdEIsVUFBTSxNQUFNLE1BQU0sSUFBSSxNQUFNLE1BQU0sS0FBSyxNQUFNLE1BQU07QUFFbkQsUUFBSSxDQUFDLGNBQWMsS0FBSyxHQUFHO0FBQUcsYUFBTztBQUNyQyxRQUFJO0FBQVEsYUFBTztBQUVuQixVQUFNLFNBQVMsSUFBSSxNQUFNLGFBQWE7QUFFdEMsUUFBSSxDQUFDO0FBQVEsYUFBTztBQUVwQixVQUFNLFFBQVEsTUFBTSxLQUFLLFdBQVcsV0FBVyxDQUFDO0FBQ2hELFVBQU0sVUFBVSxPQUFPLENBQUMsRUFBRSxRQUFRLFNBQVMsR0FBRztBQUM5QyxVQUFNLFFBQVEsT0FBTyxDQUFDLEtBQUssSUFBSSxRQUFRLFlBQVksSUFBSTtBQUN2RCxVQUFNLFFBQVEsTUFBTTtBQUNwQixVQUFNLE9BQU8sT0FBTyxDQUFDLEVBQUU7QUFFdkIsV0FBTztBQUFBLEVBQ1gsQ0FBQztBQUNMOzs7QUMxQkEsSUFBTyx3QkFBUSxDQUFDLE9BQXlCO0FBQ3JDLEtBQUcsU0FBUyxNQUFNLGFBQWEsTUFBTTtBQUNyQyxLQUFHLFNBQVMsTUFBTSxjQUFjLE1BQU07QUFDMUM7OztBQ0hBLElBQU8sY0FBUSxDQUFDLE9BQXlCO0FBSXJDLEtBQUcsU0FBUyxNQUFNLE1BQU0sQ0FBQyxRQUFRLFFBQVE7QUFDckMsVUFBTSxRQUFRLE9BQU8sR0FBRztBQUN4QixVQUFNLFFBQVEsTUFBTTtBQUtwQixVQUFNLFdBQVcsQ0FBQyxRQUFRLGNBQWMsUUFBUSxVQUFVLEVBQUUsU0FBUyxLQUFLLElBQ3BFLFFBQ0E7QUFDTixXQUFPLHVCQUF1QixRQUFRLEtBQUssS0FBSztBQUFBLEVBQ3BEO0FBRUEsS0FBRyxPQUFPLE1BQU0sT0FBTyxZQUFZLE9BQU8sQ0FBQyxPQUFPLFdBQVc7QUFDekQsVUFBTSxZQUFZO0FBQ2xCLFVBQU0sTUFBTSxNQUFNLElBQUksTUFBTSxNQUFNLEtBQUssTUFBTSxNQUFNO0FBRW5ELFFBQUksQ0FBQyxVQUFVLEtBQUssR0FBRztBQUFHLGFBQU87QUFDakMsUUFBSTtBQUFRLGFBQU87QUFFbkIsVUFBTSxTQUFTLElBQUksTUFBTSxTQUFTO0FBRWxDLFFBQUksQ0FBQztBQUFRLGFBQU87QUFFcEIsVUFBTSxRQUFRLE1BQU0sS0FBSyxPQUFPLE9BQU8sQ0FBQztBQUN4QyxVQUFNLFVBQVUsT0FBTyxDQUFDLEVBQUUsS0FBSztBQUMvQixVQUFNLFFBQVEsTUFBTTtBQUNwQixVQUFNLE9BQU8sT0FBTyxDQUFDLEVBQUU7QUFFdkIsV0FBTztBQUFBLEVBQ1gsQ0FBQztBQUNMOzs7QUNyQzhYLE9BQU8sY0FBYztBQUduWixJQUFNLFFBQVEsSUFBSSxTQUFTO0FBRXBCLElBQU0sb0JBQW9CLENBQUMsT0FBbUI7QUFDakQsUUFBTSxRQUFRLEdBQUcsU0FBUyxNQUFNO0FBRWhDLFFBQU0sU0FBUyxRQUFRLEdBQUcsU0FBUztBQUNuQyxLQUFHLFNBQVMsTUFBTSxRQUFRLElBQUksU0FBUztBQUNuQyxVQUFNLENBQUMsUUFBUSxLQUFLLEdBQUcsSUFBSSxJQUFJO0FBQy9CLFVBQU0sQ0FBQyxTQUFTLEdBQUcsSUFBSTtBQUN2QixVQUFNLFFBQVEsT0FBTyxHQUFHO0FBQ3hCLFFBQUksTUFBTSxTQUFTLE9BQU87QUFDdEIsWUFBTSxZQUFZLEdBQUcsTUFBTSxNQUFNLFNBQVMsR0FBRztBQUU3QyxVQUFJLFNBQVM7QUFDYixZQUFNLEVBQUUsTUFBTSxJQUFJLEdBQUc7QUFDckIsZ0JBQVUsUUFBUSxDQUFDLFVBQVVDLFNBQVE7QUFDakMsY0FBTSxFQUFFLEtBQUssSUFBSTtBQUNqQixZQUFJLFNBQVMsVUFBVTtBQUNuQixvQkFBVSxHQUFHLFNBQVMsYUFBYSxTQUFTLFVBQVcsU0FBUyxHQUFHO0FBQUEsUUFDdkUsV0FBVyxPQUFPLE1BQU0sSUFBSSxNQUFNLGFBQWE7QUFDM0Msb0JBQVUsTUFBTSxTQUFTLElBQUk7QUFBQSxZQUN6QjtBQUFBLFlBQ0FBO0FBQUEsWUFDQTtBQUFBLFlBQ0E7QUFBQSxZQUNBLEdBQUc7QUFBQSxVQUNQO0FBQUEsUUFDSixPQUFPO0FBQ0gsb0JBQVUsR0FBRyxTQUFTLFlBQVksV0FBV0EsTUFBSyxPQUFPO0FBQUEsUUFDN0Q7QUFBQSxNQUNKLENBQUM7QUFDRCxhQUFPO0FBQUEsSUFDWDtBQUNBLFdBQU8sTUFBTSxLQUFLLElBQUksR0FBRyxJQUFJO0FBQUEsRUFDakM7QUFDSjs7O0FQdEJBLElBQU0sVUFBVSxXQUFXO0FBT3BCLElBQU0sV0FBVyxDQUFDLE9BQW1CO0FBQzFDLEtBQUcsSUFBSSwwQkFBZ0I7QUFDdkIsS0FBRyxJQUFJLGVBQU87QUFDZCxLQUFHLElBQUkscUJBQVk7QUFDbkIsS0FBRyxJQUFJLFdBQUc7QUFDVixLQUFHLElBQUksYUFBYSxRQUFRO0FBQUEsSUFDMUIsU0FBUyxRQUFRO0FBQ2YsYUFBTyxDQUFDLENBQUMsT0FBTyxLQUFLLEVBQUUsTUFBTSxlQUFlO0FBQUEsSUFDOUM7QUFBQSxJQUNBLE9BQU8sUUFBUSxLQUFLO0FBaEN4QjtBQWlDTSxZQUFNLElBQUksT0FBTyxHQUFHLEVBQUUsS0FBSyxLQUFLLEVBQUUsTUFBTSxlQUFlO0FBQ3ZELFVBQUksT0FBTyxHQUFHLEVBQUUsWUFBWSxHQUFHO0FBQzdCLGNBQU0sY0FBYyxLQUFLLEVBQUUsU0FBUyxJQUFJLEVBQUUsQ0FBQyxJQUFJO0FBQy9DLGNBQU0sa0JBQWtCLE9BQU8sTUFBTSxDQUFDO0FBQ3RDLFlBQUksU0FBUztBQUViLGNBQU0sZUFBYSxxQkFBZ0IsYUFBaEIsbUJBQTJCLEdBQUcsWUFBVztBQUM1RCxZQUFJLGdCQUFnQixTQUFTLFVBQVU7QUFFckMsbUJBQVMsR0FBRztBQUFBLFlBQ1YsS0FBSyxRQUFRLFlBQVksR0FBRyxVQUFVLE1BQU07QUFBQSxZQUM1QztBQUFBLFVBQ0Y7QUFBQSxRQUNGO0FBQ0EsWUFBSSxDQUFDO0FBQVEsZ0JBQU0sSUFBSSxNQUFNLDBCQUEwQixVQUFVLEVBQUU7QUFFbkUsZUFBTztBQUFBLGtDQUNtQixtQkFBbUIsVUFBVSxRQUFRLEtBQUssQ0FBQyxDQUFDO0FBQUEsZ0NBQzlDLFVBQVU7QUFBQSxzQ0FDSixtQkFBbUIsTUFBTSxDQUFDO0FBQUEsdUNBQ3pCO0FBQUEsVUFDN0IsUUFBUSxPQUFPLFdBQVc7QUFBQSxRQUM1QixDQUFDO0FBQUEsTUFDSCxPQUFPO0FBRUwsZUFBTztBQUFBLE1BQ1Q7QUFBQSxJQUNGO0FBQUEsRUFDRixDQUFrQjtBQUNsQixLQUFHLElBQUksaUJBQWlCO0FBQzFCOzs7QUgvREEsSUFBTUMsb0NBQW1DO0FBVXpDLElBQU8saUJBQVEsYUFBYTtBQUFBO0FBQUEsRUFFMUIsT0FBTztBQUFBLEVBQ1AsYUFBYTtBQUFBLEVBQ2IsTUFBTTtBQUFBLElBQ0osQ0FBQyxRQUFRLEVBQUUsS0FBSyxRQUFRLE1BQU0sV0FBVyxDQUFDO0FBQUEsSUFDMUMsQ0FBQyxRQUFRLEVBQUUsTUFBTSxZQUFZLFNBQVMsY0FBYyxDQUFDO0FBQUEsRUFDdkQ7QUFBQSxFQUNBLGFBQWE7QUFBQSxJQUNYLE1BQU07QUFBQSxJQUNOLFNBQVMsQ0FBQyxHQUFHLENBQUM7QUFBQSxJQUNkO0FBQUE7QUFBQSxJQUNBO0FBQUE7QUFBQSxJQUNBLFFBQVE7QUFBQSxNQUNOLFVBQVU7QUFBQSxJQUNaO0FBQUEsRUFDRjtBQUFBLEVBQ0EsVUFBVTtBQUFBLElBQ1IsT0FBTztBQUFBLE1BQ0wsT0FBTztBQUFBLE1BQ1AsTUFBTTtBQUFBLElBQ1I7QUFBQSxJQUNBLGFBQWE7QUFBQSxJQUNiLE9BQU8sSUFBSTtBQUNULGVBQVMsRUFBRTtBQUFBLElBQ2I7QUFBQSxFQUNGO0FBQUEsRUFDQSxNQUFNO0FBQUEsSUFDSixTQUFTLENBQUMsT0FBTyxDQUFDO0FBQUEsSUFDbEIsU0FBUztBQUFBLE1BQ1AsT0FBTztBQUFBLFFBQ0wsVUFBVUMsTUFBSyxRQUFRQyxtQ0FBVyxLQUFLO0FBQUEsTUFDekM7QUFBQSxJQUNGO0FBQUEsRUFDRjtBQUNGLENBQUM7IiwKICAibmFtZXMiOiBbInBhdGgiLCAiaWR4IiwgIl9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lIiwgInBhdGgiLCAiX192aXRlX2luamVjdGVkX29yaWdpbmFsX2Rpcm5hbWUiXQp9Cg==
