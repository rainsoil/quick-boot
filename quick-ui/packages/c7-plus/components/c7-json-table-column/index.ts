import components from "./src/index.vue";
import {withInstall} from "../../utils/InstallUtils";
import c7Layer from "../c7-layer";
// 定义组件类型
export const c7JsonTableColumn = withInstall(components);
export default c7JsonTableColumn;
