package uk.co.xsc.intercom;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.co.xsc.intercom.exception.NumberNotFoundException;

import java.util.Date;

@Component
public class JWTUtil {

    private final String secret;

    public JWTUtil(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    public String generateToken(Long id) {
        return JWT.create()
                .withSubject(String.valueOf(id))
                .withIssuedAt(new Date())
                .withIssuer("intercom")
                .sign(Algorithm.HMAC256(secret));
    }

    public Long validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
        try {
            return Long.parseLong(JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("intercom")
                    .build()
                    .verify(token)
                    .getSubject());
        } catch (NumberFormatException e) {
            throw new JWTVerificationException("Invalid token subject");
        }
    }

}
