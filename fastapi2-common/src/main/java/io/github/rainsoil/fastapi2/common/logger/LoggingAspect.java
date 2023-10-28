package io.github.rainsoil.fastapi2.common.logger;

import cn.hutool.core.lang.Opt;
import cn.hutool.extra.spring.SpringUtil;
import io.github.rainsoil.fastapi2.common.logger.annotation.IgnoreLogger;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

/**
 * 日志拦截器
 *
 * @author luyanan
 * @since 2023/10/08
 **/
@Slf4j
@Aspect
public class LoggingAspect {


    /**
     * 拦截
     *
     * @param proceedingJoinPoint proceedingJoinPoint
     * @return 结果
     * @since 2023/10/08
     */
    @Around("@annotation(org.springframework.web.bind.annotation.RestController)" +
            "||@annotation(org.springframework.stereotype.Controller)" +
            "||@annotation(org.springframework.web.bind.annotation.RequestMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PostMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.GetMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PatchMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object[] args = proceedingJoinPoint.getArgs();
        Throwable e = null;

        Object result = null;

        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable ex) {
            e = ex;
            throw ex;
        } finally {
            long endTime = System.currentTimeMillis();
            MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
            Method method =
                    methodSignature.getMethod();
            IgnoreLogger ignoreLogger = method.getAnnotation(IgnoreLogger.class);
            if (isIgnore(ignoreLogger, IgnoreLogger.Type.PARAMS)) {
                args = null;
            }
            if (null == ignoreLogger || ignoreLogger.type() != IgnoreLogger.Type.ALL) {
                LoggerEventDto loggerEventDto = new LoggerEventDto()
                        .setEndTime(System.currentTimeMillis())
                        .setStartTime(startTime)
                        .setThrowable(e)
                        .setSignature(proceedingJoinPoint.getSignature())
                        .setArgs(args);
                if (isIgnore(ignoreLogger, IgnoreLogger.Type.RESULT)) {
                    loggerEventDto.setResult(null);
                } else {
                    loggerEventDto.setResult(result);
                }
                // 发送事件
                SpringUtil.publishEvent(new LoggerEvent(loggerEventDto));
            }
        }
        return result;

    }

    /**
     * 是否忽略
     *
     * @param ignoreLogger 忽略注解
     * @param type         忽略类型
     * @return boolean
     * @since 2023/05/21
     */
    private boolean isIgnore(IgnoreLogger ignoreLogger, IgnoreLogger.Type type) {
        return null != ignoreLogger && (ignoreLogger.type().equals(type) ||
                ignoreLogger.type().equals(IgnoreLogger.Type.ALL));
    }
}
