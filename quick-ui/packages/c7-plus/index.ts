import type {App, Component} from "vue";
import c7Button from "./components/c7-button";
import c7Card from "./components/c7-card";
import c7Title from "./components/c7-title";
import c7DatePicker from "./components/c7-date-picker";
import c7Select from "./components/c7-select";
import c7Checkbox from "./components/c7-checkbox";
import c7Cascader from "./components/c7-cascader";
import C7Upload from "./components/c7-upload";
import C7Preview from "./components/c7-preview";
import C7Layer from "./components/c7-layer";
import c7Dialog from "./components/c7-dialog";
import c7Table from "./components/c7-table";
import c7TableSearch from "./components/c7-search";

export * from './types'
export * from './components'
export * from './hooks'
const components = [c7Button,
    c7Card,
    c7Title,
    c7DatePicker,
    c7Select,
    c7Checkbox,
    c7Cascader,
    C7Upload,
    C7Preview,
    C7Layer,
    c7Dialog,
    c7TableSearch,
    c7Table];
const install = (app: App) => {
    components.forEach((comp) => {
        app.component(comp.name, comp);
    });
};
if (typeof window !== 'undefined' && window.Vue) {
    install(window.Vue)
}

export default {
    install
};
