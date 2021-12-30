package com.example.projectdemo.config;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Zero
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    private final String[] strings = new String[]{"/" ,"/login.html", "/webjars/**", "/img/**", "/js/**", "/css/**", "/static/favicon.ico", "/teacherLogin", "/studentLogin", "/swagger-ui.html/**", "/swagger-resources/**", "/csrf"};

    // 注册拦截器
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 注册Sa-Token的路由拦截器
//        registry.addInterceptor(new SaRouteInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns(strings);
//
//
//        // 注册路由拦截器，自定义认证规则
//        registry.addInterceptor(new SaRouteInterceptor((req, res, handler) -> {
//            SaRouter.match("/student/**", r -> StpUtil.checkRoleOr("student"));
//            SaRouter.match("/teacher/**", r -> StpUtil.checkRoleOr("teacher"));
//        })).addPathPatterns("/**");
//    }
}

