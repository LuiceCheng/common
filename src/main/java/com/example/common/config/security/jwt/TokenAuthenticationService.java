package com.example.common.config.security.jwt;


import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/10/25 16:18
 */
public class TokenAuthenticationService {
    private static Logger logger = LoggerFactory.getLogger(TokenAuthenticationService.class);

    /**
     * JWT验证方法
     */
    static Authentication getAuthentication(HttpServletRequest request) throws AccountExpiredException {
        logger.debug("get authentication from token");
        // 从Header中拿到token
        String token = JwtUtil.getToken(request);

        if (token != null && !"null".equals(token)) {
            // 解析 Token
            Claims claims = (Claims) JwtUtil.getClaimsFromToken(token);
            if(null == claims){
                throw new AccountExpiredException("token过期");
            }
            // 取用户ID
            String userId = claims.getSubject();

            // 取用户详情
            String details = (String) claims.get("details");

            // 是否过期
//            Date expiration = claims.getExpiration();

//            if(expiration.compareTo(new Date()) < 0){
//                // 过期了 todo
//                logger.error("token过期了,请重新登录");
//                return null;
//            }

            // 得到 权限（角色）
            List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));


            // 返回验证令牌
            if(null == userId){
                return null;
            }
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userId, null, authorities);
            usernamePasswordAuthenticationToken.setDetails(details);
            return usernamePasswordAuthenticationToken;
        }
        return null;
    }
}
