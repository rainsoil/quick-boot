package io.github.rainsoil.fastapi2.common.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * yml工具类
 *
 * @author luyanan
 * @since 2023/09/19
 **/
@Slf4j
@UtilityClass
public class YmlUtils {


    /**
     * 根据文件名获取文件的内容
     *
     * @param filePath 文件名
     * @return 文件内容
     * @since 2023/09/19
     */
    public Map<String, String> getYmlByFileName(String filePath) {
        Map<String, String> result = new HashMap<>();

        InputStream in = null;
        try {
            File file = ResourceUtils.getFile(filePath);
            in = new BufferedInputStream(new FileInputStream(file));
            Yaml props = new Yaml();
            Object obj = props.loadAs(in, Map.class);
            Map<String, Object> param = (Map<String, Object>) obj;

            for (Map.Entry<String, Object> entry : param.entrySet()) {
                String key = entry.getKey();
                Object val = entry.getValue();

                if (val instanceof Map) {
                    forEachYaml(key, (Map<String, Object>) val, 1, result);
                } else {
                    result.put(key, val.toString());
                }
            }
            return result;
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }

    /**
     * 根据key获取内容
     *
     * @param filePath 文件路径
     * @param key      key
     * @return 内容
     * @since 2023/09/19
     */
    public String getValue(String key, String filePath) throws FileNotFoundException {
        Map<String, String> map = getYmlByFileName(filePath);
        if (map == null) {
            return null;
        }
        return map.get(key);
    }

    /**
     * 遍历yml文件,获取map集合
     *
     * @param key_str ket
     * @param obj     内容
     * @param i       下标
     * @param result  结果
     * @since 2023/09/19
     */
    public Map<String, String> forEachYaml(String key_str, Map<String, Object> obj, int i, Map<String, String> result) {
        for (Map.Entry<String, Object> entry : obj.entrySet()) {
            String key = entry.getKey();
            Object val = entry.getValue();
            String strNew = "";
            if (StringUtils.isNotEmpty(key_str)) {
                strNew = key_str + "." + key;
            } else {
                strNew = key;
            }
            if (val instanceof Map) {
                forEachYaml(strNew, (Map<String, Object>) val, ++i, result);
                i--;
            } else {

                result.put(strNew, val.toString());
            }
        }

        return result;
    }
}
