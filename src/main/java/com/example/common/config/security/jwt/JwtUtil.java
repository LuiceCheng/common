package com.example.common.config.security.jwt;




import com.example.common.model.JwtUser;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/10/25 16:19
 */
@Component
public class JwtUtil {
    private static final String CLAIM_KEY_ACCOUNT = "account";
    private static final String CLAIM_KEY_USERNAME = "name";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_USER_ID = "id";
    private static final String CLAIM_KEY_AUTHORITIES = "scope";
    private long expiration = 1800;//token有效期30分钟

    /**
     * token前缀
     */
    public static final String TOKEN_PREFIX = "Bearer";

    /**
     * JWT密码
     */
    private static final String SECRET = "sdf8kwei@128@@R#^&@!asdjfaASDFAEdeigkGKiedce(*&";

    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    public static Claims getClaimsFromToken(String token) {
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
        String token = request.getHeader(TOKEN_PREFIX);
        return token;
    }

    public String generateAccessToken(JwtUser jwtUser){
        return null;
    }

    public String generateToken(JwtUser user){
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_ACCOUNT, user.getUser().getUserName());
        claims.put(CLAIM_KEY_USERNAME, user.getUser().getUserName());
        String subject = user.getUser().getUserName();
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setId(UUID.randomUUID().toString())
            .setIssuedAt(new Date())
            .setExpiration(generateExpirationDate(expiration))
            .compressWith(CompressionCodecs.DEFLATE)
            .signWith(SIGNATURE_ALGORITHM, SECRET)
            .compact();
    }

    private Date generateExpirationDate(long expiration){
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    public static JwtUser getUserInfo(){
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    public Map<String, Object> generateClaims(JwtUser user){
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_ACCOUNT, user.getUser().getUserName());
        claims.put(CLAIM_KEY_USERNAME, user.getUser().getUserName());
        return claims;
    }
}
