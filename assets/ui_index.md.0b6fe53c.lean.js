import{_ as a,c as l,S as i,o as t}from"./chunks/framework.db654418.js";const s=JSON.parse('{"title":"c7-ui 组件","description":"","frontmatter":{},"headers":[],"relativePath":"ui/index.md","filePath":"ui/index.md"}'),o={name:"ui/index.md"};function c(r,e,u,h,n,d){return t(),l("div",null,e[0]||(e[0]=[i('<h1 id="c7-ui-组件" tabindex="-1">c7-ui 组件 <a class="header-anchor vp-link" href="#c7-ui-组件" aria-label="Permalink to &quot;c7-ui 组件&quot;">​</a></h1><h2 id="_1-介绍" tabindex="-1">1. 介绍 <a class="header-anchor vp-link" href="#_1-介绍" aria-label="Permalink to &quot;1. 介绍&quot;">​</a></h2><blockquote><p>基于 vue3+ ts+ Element-plus 二次封装组件</p></blockquote><h2 id="_2-组件介绍" tabindex="-1">2. 组件介绍 <a class="header-anchor vp-link" href="#_2-组件介绍" aria-label="Permalink to &quot;2. 组件介绍&quot;">​</a></h2><h3 id="_2-1-hook" tabindex="-1">2.1. Hook <a class="header-anchor vp-link" href="#_2-1-hook" aria-label="Permalink to &quot;2.1. Hook&quot;">​</a></h3><blockquote><p>封装常用hook</p></blockquote><h4 id="_2-1-1-tablehook" tabindex="-1">2.1.1 tableHook <a class="header-anchor vp-link" href="#_2-1-1-tablehook" aria-label="Permalink to &quot;2.1.1 tableHook&quot;">​</a></h4><blockquote><p>对表格的常用方法的封装</p></blockquote><h3 id="_2-2-组件" tabindex="-1">2.2 组件 <a class="header-anchor vp-link" href="#_2-2-组件" aria-label="Permalink to &quot;2.2 组件&quot;">​</a></h3><h4 id="_2-2-1-c7-button组件" tabindex="-1">2.2.1 c7-button组件 <a class="header-anchor vp-link" href="#_2-2-1-c7-button组件" aria-label="Permalink to &quot;2.2.1  c7-button组件&quot;">​</a></h4><blockquote><p>对button 组件的封装, 在不对其原有功能的影响下,增加了 事件的触发,分为</p></blockquote><ul><li>增加api参数和params参数实现对后端接口的触发</li><li>对前端事件的触发</li><li>增加href 参数实现路由跳转</li><li>放重复和点击的防抖</li></ul><h4 id="_2-2-2-c7-select-下拉选择器" tabindex="-1">2.2.2 c7-select 下拉选择器 <a class="header-anchor vp-link" href="#_2-2-2-c7-select-下拉选择器" aria-label="Permalink to &quot;2.2.2  c7-select 下拉选择器&quot;">​</a></h4><blockquote><p>对select 组件的封装, 在不对其原有功能的影响下,增加了</p></blockquote><ul><li>增加api参数和params参数实现对后端接口的触发</li><li>增加dictType(代码类型)参数,可以直接实现获取代码字典的下拉</li><li>增加dictData参数实现对 数据的下拉(静态数据)</li></ul><h3 id="_2-2-3-c7-cascader-级联多选器" tabindex="-1">2.2.3 c7-cascader 级联多选器 <a class="header-anchor vp-link" href="#_2-2-3-c7-cascader-级联多选器" aria-label="Permalink to &quot;2.2.3 c7-cascader  级联多选器&quot;">​</a></h3><blockquote><p>对cascader 组件的封装, 在不对其原有功能的影响下,增加了</p></blockquote><ul><li>增加api参数和params参数实现对后端接口的触发</li><li>当数据为远程加载的时候,自动实现对数据的反显</li></ul><h3 id="_2-2-4-c7-file-upload-文件上传组件" tabindex="-1">2.2.4 c7-file-upload 文件上传组件 <a class="header-anchor vp-link" href="#_2-2-4-c7-file-upload-文件上传组件" aria-label="Permalink to &quot;2.2.4 c7-file-upload  文件上传组件&quot;">​</a></h3><blockquote><p>对文件上传进行封装,实现</p></blockquote><ul><li>对文件格式、大小进行限制</li></ul><h3 id="_2-2-5-c7-file-preview-文件预览组件" tabindex="-1">2.2.5 c7-file-preview 文件预览组件 <a class="header-anchor vp-link" href="#_2-2-5-c7-file-preview-文件预览组件" aria-label="Permalink to &quot;2.2.5 c7-file-preview  文件预览组件&quot;">​</a></h3><blockquote><p>对文件预览进行封装,实现</p></blockquote><ul><li>针对文件的格式自动分析进行预览</li></ul><h3 id="_2-2-6-c7-table-表格组件" tabindex="-1">2.2.6 c7-table 表格组件 <a class="header-anchor vp-link" href="#_2-2-6-c7-table-表格组件" aria-label="Permalink to &quot;2.2.6 c7-table  表格组件&quot;">​</a></h3><blockquote><p>对表格进行封装,实现</p></blockquote><ul><li>基于json 对表格内容进行展示</li><li>增加卡槽机制对 可以对表格内容进行自定义</li><li>增加字段formater 可以对表格的列进行处理</li><li>增加api参数和params参数实现对后端接口的触发</li><li>列增加类型, <ul><li>dict 当为dict并且指定dictType的时候, 自动翻译字典值</li><li>image 当为image的时候,预览图片</li></ul></li></ul><h3 id="_2-2-7-c7-tree-树形组件" tabindex="-1">2.2.7 c7-tree 树形组件 <a class="header-anchor vp-link" href="#_2-2-7-c7-tree-树形组件" aria-label="Permalink to &quot;2.2.7 c7-tree  树形组件&quot;">​</a></h3><blockquote><p>对树形组件进行封装,实现</p></blockquote><ul><li>增加api参数和params参数实现对后端接口的触发</li><li>实现对tree内容的回显和其他方法的触发</li></ul><h3 id="_2-2-8-c7-search-搜索组件" tabindex="-1">2.2.8 c7-search 搜索组件 <a class="header-anchor vp-link" href="#_2-2-8-c7-search-搜索组件" aria-label="Permalink to &quot;2.2.8 c7-search  搜索组件&quot;">​</a></h3><blockquote><p>对搜索的内容进行封装,实现</p></blockquote><ul><li>基于json的配置</li><li>列增加类型 <ul><li>select 当为select的时候,实现对select的下拉框, 下拉指定dictType的时候, 显示字典值</li><li>date 当为date的时候,实现对日期的格式化</li><li>datetime 当为datetime的时候,实现对日期时间的格式化</li></ul></li></ul><h3 id="_2-2-9-c7-title组件" tabindex="-1">2.2.9 c7-title组件 <a class="header-anchor vp-link" href="#_2-2-9-c7-title组件" aria-label="Permalink to &quot;2.2.9 c7-title组件&quot;">​</a></h3><blockquote><p>标题组件</p></blockquote><h3 id="_2-2-10-c7-care组件" tabindex="-1">2.2.10 c7-care组件 <a class="header-anchor vp-link" href="#_2-2-10-c7-care组件" aria-label="Permalink to &quot;2.2.10 c7-care组件&quot;">​</a></h3><blockquote><p>对el-card 组件进行扩展,增加 展开和收回功能</p></blockquote>',37)]))}const b=a(o,[["render",c]]);export{s as __pageData,b as default};
