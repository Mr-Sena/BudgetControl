package br.com.alura.challenge.backend.FinanceValuation.infrastructure.controller.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Setter
public class LoginForm {


    private String email;
    private String senha;


    public UsernamePasswordAuthenticationToken converter() {

        return new UsernamePasswordAuthenticationToken(email, senha);

    }


}
