package io.github.rainsoil.fastapi2.core.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * 自定义字段数据填充
 *
 * @author luyanan
 * @since 2023/09/19
 **/
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

        log.debug("开始insert填充");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("开始update填充");
    }
}
