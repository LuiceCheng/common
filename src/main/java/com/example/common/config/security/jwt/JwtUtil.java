package com.example.common.config.security.jwt;




import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/10/25 16:19
 */
public class JwtUtil {
    /**
     * token前缀
     */
    private static final String TOKEN_PREFIX = "Bearer";

    /**
     * token存放的header名称
     */
    private static final String HEADER_STRING = "Authorization";

    /**
     * JWT密码
     */
    private static final String SECRET = "sdf8kwei@128@@R#^&@!asdjfaASDFAEdeigkGKiedce(*&";

    public static Claims parse(String token) {
        try {
            return Jwts.parser()
                // 验签
                .setSigningKey(SECRET)
                // 去掉 Bearer
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();
        } catch (Exception e) {
            return null;
        }
    }


    public static String getToken(HttpServletRequest request) {
        if (null == request) {
            return "";
        }
        String token = request.getHeader(HEADER_STRING);
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("token");
        }

        return token;
    }
}
