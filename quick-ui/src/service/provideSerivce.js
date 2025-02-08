import {provide} from "vue";
import baseService from "./baseService.js";

function getRequest(path, params, headers) {
    return baseService.get(path, params, headers)
}

export default {
    getRequest
}

