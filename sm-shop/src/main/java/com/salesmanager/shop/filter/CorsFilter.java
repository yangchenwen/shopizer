package com.salesmanager.shop.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsFilter extends HandlerInterceptorAdapter {

    public CorsFilter() {

    }

    /**
     * Allows public web services to work from remote hosts
     */
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String origin = "*";
        if (!StringUtils.isBlank(request.getHeader("origin"))) {
            origin = request.getHeader("origin");
        }

        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        httpResponse.setHeader("Access-Control-Allow-Headers", "X-Auth-Token, Content-Type, Authorization, Cache-Control, X-Requested-With");
        httpResponse.setHeader("Access-Control-Allow-Origin", origin);

        return true;

    }
}
