import {resolve} from "path";
// 项目目录
export const projRoot = resolve(__dirname, "..", "..", "..");
// 项目名称
export const docsDirName = "docs";
// 文档库目录
export const docRoot = resolve(projRoot, docsDirName);

// example例子
export  const  exampleDir = resolve(projRoot, "src/views/demo");
