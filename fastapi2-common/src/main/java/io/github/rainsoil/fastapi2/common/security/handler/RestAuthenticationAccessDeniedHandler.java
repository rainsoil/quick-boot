package io.github.rainsoil.fastapi2.common.security.handler;

import com.alibaba.fastjson.JSON;
import io.github.rainsoil.fastapi2.common.core.R;
import io.github.rainsoil.fastapi2.common.exception.GlobalMsgCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 权限不足
 *
 * @author luyanan
 * @since 2023/11/28
 **/
@Slf4j
public class RestAuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        log.debug("{}访问权限不足:{}", request.getRequestURI(), e.getMessage());
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(JSON.toJSONString(R.failed(GlobalMsgCode.FORBIDDEN, e.getMessage())));
        printWriter.flush();
    }
}
