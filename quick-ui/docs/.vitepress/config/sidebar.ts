export default {
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
        {text: "设计", link: "/guide/design"},
        {
            text: "指南",
            items: [
                {text: "安装", link: "/guide/installation"},
                {text: "快速开始", link: "/guide/quickstart"},
                {text: "tableView", link: "/guide/tableView"},
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
    ]
};
