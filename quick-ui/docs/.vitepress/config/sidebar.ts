export default {
    "/guide/": [
        {text: "设计", link: "/guide/design"},
        {
            text: "指南",
            items: [
                {text: "安装", link: "/guide/installation"},
                {text: "快速开始", link: "/guide/quickstart"},
                {text: "tableView", link: "/guide/tableView"},
            ],
        },
        {
            text: "脚本",
            items: [
                {text: "数据库备份脚本", link: "/guide/installation"},
                {text: "nginx日志备份", link: "/guide/quickstart"},
            ],
        },
        {
            text: "安全",
            items: [
                {text: "服务器配置", link: "/guide/installation"},
                {text: "mysql配置", link: "/guide/quickstart"},
            ],
        },
        {text: "更新日志", link: "/updatelog/index"}
    ],
    //  标签介绍
    '/components/c7/': [
        {
            text: '简介',
            link: '/components/c7/index',
            items: []
        },
        {
            text: 'Hook',
            items: [
                {text: '表格Hook', link: '/components/c7/tableHook'},
                {text: '字典Hook', link: '/components/c7/dictHook'},
            ],
        },
        {
            text: '布局组件',
            items: [
                {text: 'title组件', link: '/components/c7/c7Title'},
                {text: 'card卡片组件', link: '/components/c7/card'},
            ],
        },
        {
            text: '表单组件',
            items: [
                {text: 'Button按钮', link: '/components/c7/button'},
                {text: 'c7-select', link: '/components/c7/select'},
                {text: 'c7-date-picker', link: '/components/c7/datePicker'},
                {text: "c7-checkbox", link: "/components/c7/checkbox"},
                {text: "c7-cascader", link: "/components/c7/cascader"},
                {text: "c7-upload", link: "/components/c7/upload"},
                {text: "c7-preview", link: "/components/c7/preview"},
                {text: "c7-dialog", link: "/components/c7/dialog"},
                {text: "c7-search", link: "/components/c7/search"},
                {text: "c7-table", link: "/components/c7/table"},
                {text: "c7-radio", link: "/components/c7/radio"},
                {text: "c7-switch-form", link: "/components/c7/switchForm"}
            ],

        },
    ]
};
