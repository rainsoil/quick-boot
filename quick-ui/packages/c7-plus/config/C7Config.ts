import {inject} from "vue";
/**
 * 默认的配置文件
 */
import {IC7Config, IObject} from "../types/ITypes";

/*
配置
 */
const defaultConfig: IC7Config = {

}


/**
 * 获取默认的配置
 */
export const getConfig = (): IC7Config => {
    let c7Config = inject<IC7Config>("c7Config");
    if (null == c7Config) {
        return defaultConfig
    }
    // 将 c7Config 跟 defaultConfig 进行合并

    return merge(defaultConfig, c7Config);
}

/**
 * 配置合并
 * @param options
 * @param props
 */
const merge = (options: IObject, props: IObject): IC7Config => {
    if (!options || !props) {
        throw new Error("options and props must be valid objects");
    }

    for (const key in options) {
        if (!props.hasOwnProperty(key)) {
            props[key] = options[key];
        }
    }
    return props;
};

