package com.lanyuan.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 主要是在操作日志方法中猎取HttpServletRequest
 * @author lanyuan
 * Email：mmm333zzz520@163.com
 * date：2014-4-11
 */
public class GetContent implements Filter {  
    public void destroy() {  
    }  
    public void doFilter(ServletRequest arg0,ServletResponse arg1, 
            FilterChain arg2) throws IOException,ServletException {  
        SysContent.setRequest((HttpServletRequest) arg0);  
        SysContent.setResponse((HttpServletResponse) arg1);  
        arg2.doFilter(arg0,arg1);  
    }  
    public void init(FilterConfig arg0) throws ServletException {  
    }  
}  
