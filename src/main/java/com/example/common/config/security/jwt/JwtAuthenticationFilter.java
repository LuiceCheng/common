package com.example.common.config.security.jwt;

import com.alibaba.fastjson.JSON;
import com.example.common.entity.Msg;
import com.example.common.enums.EnError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.security.auth.login.AccountExpiredException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/10/25 16:15
 */
public class JwtAuthenticationFilter extends GenericFilterBean {
    private static Logger LOG = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            Msg<String> msg = new Msg<>();
            if(e instanceof AccountExpiredException){
                msg.setMsg(e.getMessage());
                msg.setResult(EnError.FORBIDDEN);
            } else {
                logger.error("异常 ", e);
                return;
            }

            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter out;
            try {
                out = response.getWriter();
                out.println(JSON.toJSONString(msg));
            } catch (IOException e1) {
                ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            return;
        }


        chain.doFilter(request, response);
    }
}
