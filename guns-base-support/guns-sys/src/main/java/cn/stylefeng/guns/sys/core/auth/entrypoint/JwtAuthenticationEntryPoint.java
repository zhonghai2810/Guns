package cn.stylefeng.guns.sys.core.auth.entrypoint;

import cn.stylefeng.guns.base.auth.exception.enums.AuthExceptionEnum;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 这个端点用在用户访问受保护资源但是不提供任何token的情况下
 *
 * @author fengshuonan
 * @Date 2019/7/20 17:57
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -1L;

    /*
    无权限访问资源的情况处理。如果是页面get请求，则返回跳转主页面，要求登陆。如果是接口post请求，则返回json
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // GET请求跳转到主页
        if ("get".equalsIgnoreCase(request.getMethod())
                && !request.getHeader("Accept").contains("application/json")) {

            // 没有权限，调用/global/sessionError的Handler（GlobalController.java）
            response.sendRedirect(request.getContextPath() + "/global/sessionError");

        } else {

            // POST请求返回json
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");

            /*
            返回NO_PERMISSION：没有权限访问资源
             */
            ErrorResponseData errorResponseData = new ErrorResponseData(
                    AuthExceptionEnum.NO_PERMISSION.getCode(), AuthExceptionEnum.NO_PERMISSION.getMessage());

            response.getWriter().write(JSON.toJSONString(errorResponseData));
        }
    }
}