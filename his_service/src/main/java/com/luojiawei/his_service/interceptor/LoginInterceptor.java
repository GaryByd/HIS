// LoginInterceptor.java
package com.luojiawei.his_service.interceptor;

import com.luojiawei.common.utils.UserHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断是否需要拦截
        System.out.println("preHandle called: " + request.getRequestURI());
        if (request.getRequestURI().contains("error")) {
            // 设置404 状态码以及没有找到路径的消息
            response.setStatus(520);
            response.getWriter().write("Server error error error error error!");
            return false;
        }
        if (UserHolder.getUser() == null) {
            System.out.println("被拦截了！");
            // 拦截
            response.setStatus(403);
            return false;
        }
        // 放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}