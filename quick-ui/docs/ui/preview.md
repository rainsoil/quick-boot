# 文件预览

:::demo

preview/preview
:::

## Attributes

| 属性名       | 说明           | 类型      | 可选值                           | 默认值  |
| ------------ | -------------- | --------- | -------------------------------- | ------- |
urls | 以逗号分隔的文件 URL 列表 | String  |     |
width | 预览区域宽度，可传数字或带单位的字符串 | String |    |   200px
height | 预览区域高度 | String  |   | 200px
displayType | 文件显示类型：'image' | 'video' | 'file'|  String |  文件显示类型：'image' 、 'video' 、 'file'  |image
coverType| 封面类型：'None'（直接展示所有预览）、'button'（按钮封面）、'file'（文件封面） |String | 'None'（直接展示所有预览）、'button'（按钮封面）、'file'（文件封面）  |None
buttonText| 当封面类型为 button 时，按钮显示的文本| String |  | 预览文件
defaultVideoImage| 当 displayType 为 video 时，默认的视频缩略图 | String |   | https://via.placeholder.com/200?text=Video
defaultFileImage | 当 displayType 为 file 且封面类型为 file 时，默认的文件封面图 | String |  | https://via.placeholder.com/200?text=File

