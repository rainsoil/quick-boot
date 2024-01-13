package io.github.rainsoil.fastboot.common.logger;

import org.springframework.context.ApplicationEvent;

/**
 * 日志 事件
 *
 * @author luyanan
 * @since 2023/10/08
 **/
public class LoggerEvent extends ApplicationEvent {
    public LoggerEvent(Object source) {
        super(source);
    }
}
