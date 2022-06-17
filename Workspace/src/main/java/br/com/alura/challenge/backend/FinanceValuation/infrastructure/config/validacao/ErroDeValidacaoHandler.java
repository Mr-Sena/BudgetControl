package br.com.alura.challenge.backend.FinanceValuation.infrastructure.config.validacao;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ErroDeValidacaoHandler {


    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsuarioDuplicadoException.class)
    public String handle(UsuarioDuplicadoException exception) {

        return "Ocorrência de registro duplicado: Usuário já cadastrado.";

    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RegistroDuplicadoException.class)
    public String alreadyCadastre(RegistroDuplicadoException e) {

        return "Ocorrência de registro duplicado: cadastro com descrição nessa data(mes/ano) já foi efetuado.";

    }



}
