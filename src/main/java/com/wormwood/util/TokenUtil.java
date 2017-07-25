package com.wormwood.util;

import com.google.common.base.Preconditions;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.Map;

/**
 * Created by kasimodo on 2017-07-04.
 */
public class TokenUtil {
    private final static Log logger = LogFactory.getLog(TokenUtil.class);
    private static final String SECRET = "alKjvd**&&^^^hdhfhfefef..//.334344590409042r23r2fdsfdsvjsdvjkskjjjkjjjeDF&*#&!^!@~@DVNMBISEjndfh67343**&1213Lknvnbj<MNb";
    public static final String CLAIM_KEY_CREATED = "c";
    public static final String CLAIM_KEY_SYSTEM= "s";
    private static final String ALG_HEADER = "eyJhbGciOiJIUzI1NiJ9.";
    /**
     * 生成token
     *
     * @param claims
     * @return
     */
    public static String generateToken(Map<String, Object> claims) {
        String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, SECRET).compact();
        String[] tokenArr = StringUtils.split(token, ".");
        Preconditions.checkState(tokenArr != null && tokenArr.length == 3);
        return tokenArr[1] + "." + tokenArr[2];
    }

    /**
     * 获得创建时间
     *
     * @param token
     * @return
     */
    public static Date getCreatedDateFromToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            logger.debug(e.getLocalizedMessage());
            created = null;
        }
        return created;
    }
    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(ALG_HEADER + token).getBody();
        } catch (Exception e) {
            logger.debug(e.getLocalizedMessage());
            claims = new DefaultClaims();
        }
        return claims;
    }
}
