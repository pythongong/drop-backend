package com.drop.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JWTUtils {
    public static final long EXPIRE = 86400000L;
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    public JWTUtils() {
    }

    public static String getJwtToken(String id, String nickname) {
        String JwtToken = Jwts.builder().setHeaderParam("typ", "JWT").setHeaderParam("alg", "HS256").setSubject("drop-user").setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 86400000L)).claim("id", id).claim("nickname", nickname).signWith(SignatureAlgorithm.HS256, "ukc8BDbRigUDaY6pZFfWus2jZWLPHO").compact();
        return JwtToken;
    }

    public static boolean checkToken(String jwtToken) {
        if (StringUtils.isEmpty(jwtToken)) {
            return false;
        } else {
            try {
                Jwts.parser().setSigningKey("ukc8BDbRigUDaY6pZFfWus2jZWLPHO").parseClaimsJws(jwtToken);
                return true;
            } catch (Exception var2) {
                var2.printStackTrace();
                return false;
            }
        }
    }

    public static String getIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if (StringUtils.isEmpty(jwtToken)) {
            return "";
        } else {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey("ukc8BDbRigUDaY6pZFfWus2jZWLPHO").parseClaimsJws(jwtToken);
            Claims claims = (Claims)claimsJws.getBody();
            return (String)claims.get("id");
        }
    }
}
