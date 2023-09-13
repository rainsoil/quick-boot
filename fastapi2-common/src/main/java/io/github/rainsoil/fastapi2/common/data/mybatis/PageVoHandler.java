package io.github.rainsoil.fastapi2.common.data.mybatis;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.rainsoil.fastapi2.common.bean.BeanConvertUtils;
import io.github.rainsoil.fastapi2.common.bean.ConvertCallBack;
import io.github.rainsoil.fastapi2.common.core.PageInfo;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 分页vo类处理
 *
 * @author luyanan
 * @since 2023/09/13
 **/
public interface PageVoHandler<T, V> {

    /**
     * 条件构造处理
     *
     * @param vo           vo类
     * @param t            实体类
     * @param queryWrapper 条件构造
     * @since 2023/09/13
     */
    default void queryWrapperHandler(V vo, T t, LambdaQueryWrapper<T> queryWrapper) {
        queryWrapper.setEntity(t);
    }


    /**
     * 获取PageInfo对象
     *
     * @param page    分页
     * @param voClazz vo类class
     * @return 分页对象
     * @since 2023/09/13
     */
    default PageInfo<V> getPageInfo(Page<T> page, Class<V> voClazz) {
        PageInfo<V> pageInfo = new PageInfo<>();
        pageInfo.setCurrent(page.getCurrent());
        pageInfo.setSize(page.getSize());
        pageInfo.setPages(page.getPages());
        List<T> records = page.getRecords();
        List<V> vList = BeanConvertUtils.convertListTo(records, () -> initVo(voClazz), new ConvertCallBack<T, V>() {
            @Override
            public void callBack(T t, V s) {
                recordHandler(t, s);
            }
        });
        if (CollectionUtil.isNotEmpty(records)) {
            recordsHandler(records, vList);
        }
        pageInfo.setRecords(vList);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }


    /**
     * 初始化vo对象
     *
     * @param voClazz vo类class
     * @return vo类对象
     * @since 2023/09/13
     */
    default V initVo(Class<V> voClazz) {
        V aClass = BeanUtils.instantiateClass(voClazz);
        return aClass;
    }


    /**
     * 分页结果处理
     *
     * @param ts 实体类
     * @param vs vo类
     * @since 2023/09/13
     */
    default void recordsHandler(List<T> ts, List<V> vs) {

    }


    /**
     * 单条处理
     *
     * @param t 实体类
     * @param v vo类
     * @since 2023/09/13
     */
    default void recordHandler(T t, V v) {

    }
}
