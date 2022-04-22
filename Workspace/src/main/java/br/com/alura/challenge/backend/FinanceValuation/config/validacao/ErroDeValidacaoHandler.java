package br.com.alura.challenge.backend.FinanceValuation.config.validacao;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ErroDeValidacaoHandler {


    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RegistroDuplicadoException.class)
    public String handle(RegistroDuplicadoException exception) {

        return "Ocorrência de registro duplicado: Usuário já cadastrado.";

    }

}
