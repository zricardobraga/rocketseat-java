package br.com.zricardo.gestao_vagas.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTProvider {
    @Value("${security.token.secret}")
    private String secretKey;
    public DecodedJWT validadeToken (String token) {
        //tira a palavra "Bearer" do authentication
        token = token.replace("Bearer ", "");

        //traz o algorithm
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
           var tokenDecoded = JWT.require(algorithm).build().verify(token);
            return tokenDecoded;
        } catch (JWTVerificationException ex) {
            return null;
        }
    }
}
