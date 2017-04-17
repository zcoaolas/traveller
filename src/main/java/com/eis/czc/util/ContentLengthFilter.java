package com.eis.czc.util;

/**
 * Created by zcoaolas on 2017/4/10.
 */

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

public class ContentLengthFilter implements Filter {


    public void init(FilterConfig filterConfig) throws ServletException {
    }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);

        chain.doFilter(request, responseWrapper);

        responseWrapper.copyBodyToResponse();

    }

    public void destroy() {
    }

}

