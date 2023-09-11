package io.github.rainsoil.fastapi2.common.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页返回
 *
 * @author luyanan
 * @since 2023/09/09
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo<T> {

    /**
     * 当前页数
     *
     * @since 2023/09/09
     */

    private long current;


    /**
     * 每页条数
     *
     * @since 2023/09/09
     */

    private long size;

    /**
     * 返回内容
     *
     * @since 2023/09/09
     */

    private List<T> records;


    /**
     * 总条数
     *
     * @since 2023/09/09
     */

    private long total;

    /**
     * 总页数
     *
     * @since 2023/09/09
     */

    private long pages;

    /**
     * 扩展字段
     *
     * @since 2023/09/09
     */

    private Map<String, Object> ext = new HashMap<>();
}
