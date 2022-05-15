package br.com.alura.challenge.backend.FinanceValuation.infrastructure.config.security;

import br.com.alura.challenge.backend.FinanceValuation.domain.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${app.jwt.expiration}")
    private String tokenExpiration;

    @Value("${app.jwt.secret}")
    private String secret;


    public String gerarToken(Authentication authentication) {

        Usuario logado = (Usuario)authentication.getPrincipal();
        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(tokenExpiration));

        return Jwts.builder()
                .setIssuer("Serviço de controler financeiro familiar")
                .setSubject(logado.getId().toString())
                .setIssuedAt(hoje)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }

    public boolean isTokenValido(String token) {

        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception exception) {
            return false;
        }

    }

    public Long getIdUsuario(String token) {

        Claims claims =  Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());

    }

}
