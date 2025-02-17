import {provide} from "vue";
import baseService from "./baseService.js";

export function getRequest(path, params, headers) {
    return baseService.get(path, params, headers)
}



