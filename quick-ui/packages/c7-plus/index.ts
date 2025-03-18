import type {App, Component} from "vue";
import QForm from "./quick-form";
import QTable from "./quick-table";

export * from './types'
export * from './components'
export * from './hooks'
const components = [QForm, QTable];

const install = (app: App) => {
    components.forEach((comp) => {
        app.component(comp.name, comp);
    });
};

export default install;
