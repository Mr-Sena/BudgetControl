package br.com.alura.challenge.backend.FinanceValuation.controller.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Setter
public class CadastroForm {


    private String nome;
    private String email;
    private String senha;


}
