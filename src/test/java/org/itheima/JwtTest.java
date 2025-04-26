package org.itheima;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    @Test
    public void testGen() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", "1");
        claims.put("username", "zhangsan");
        // 生成jwt的代码
        String token = JWT.create()
                .withClaim("user", claims)// 设置载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))// 设置过期时间
                .sign(Algorithm.HMAC256( "itheima"));// 设置密钥;
        System.out.println(token);
    }

    @Test
    public void testParse() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VyIjp7ImlkIjoiMSIsInVzZXJuYW1lIjoiemhhbmdzYW4ifSwiZXhwIjoxNzQ1NzIwODYzfQ" +
                "._l5JV-_XARafrU7vw_eH1YHeqAWo5q88VBADHEe7Fmo";
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256("itheima")).build();
        DecodedJWT jwt = verifier.verify(token);
        Map<String, Claim> claims = jwt.getClaims();
        System.out.println(claims.get("user"));
    }
}
