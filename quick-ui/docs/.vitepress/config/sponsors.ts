export const rightRichTextSponsors = []

export const rightBigLogoSponsors = [
    {
        name: 'CRMEB',
        img: '/images/sponsors/CRMEB.png',
        imgL: '/images/sponsors/CRMEB-l.png',
        url: 'http://github.crmeb.net/u/Element?from=element-plus',
        slogan: 'High-quality open source mall system',
        slogan_cn: '高品质开源商城系统 累计服务40W+开发者',
        slogan_index: '高品质开源商城系统累计服务40W+开发者',
    },
]

export const rightLogoSmallSponsors = [
    {
        name: 'BuildAdmin',
        img: '/images/sponsors/buildadmin.png',
        imgL: '/images/sponsors/buildadmin-l.png',
        url: 'https://doc.buildadmin.com/?from=element-plus',
        slogan: 'Vue3 open source admin system',
        slogan_cn: 'Vue3企业级开源后台管理系统',
    },

]

export const leftCustomImgSponsors = [
    {
        name: '美乐',
        img: '/images/sponsors/mele.png',
        url: 'https://melecode.com/',
        slogan: 'Enterprise-level low-code development platform',
        slogan_cn: '企业级全栈低代码开发平台',
        banner_img: '/images/sponsors/mele-banner.png',
    },

]

export const platinumSponsors = [
    ...leftCustomImgSponsors,
    ...rightBigLogoSponsors,
    ...rightRichTextSponsors,
]

export const leftLogoSponsors = []

export const goldSponsors = [...rightLogoSmallSponsors, ...leftLogoSponsors]
