package br.com.alura.challenge.backend.FinanceValuation.controller;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ReceitaTest {
    @Test
    public void testWhen_InsertNewReceita_Expected_ReturnStatusCode201Created() {


        baseURI = "http://localhost";
        port = 8080;
        basePath = "/receitas";


        // Login na API Rest com administrador
        String token;

        //Inserir o novo registro
        given()
                .body("{\n" +
                    "\"valor\": \"2100\",\n" +
                    "\"descricao\": \"Investimento a curto Prazo\",\n" +
                    "\"data\": \"30/04/2022\"" +
                "}")
                .contentType(ContentType.JSON)
        .when().post()
        .then()
                .log().all()
                .assertThat()
                            .statusCode(201);

        //Obs: O teste só vai funcionar caso o registro for inexistente no banco de dados
        //no momento da inserção, em caso de duplicidade (Mesma descrição e data), o resultado é o code 400

    }

    @Test
    public void testWhen_InsertAgainReceita_Expected_ReturnStatusCode400() {

        baseURI = "http://localhost";
        port = 8080;
        basePath = "/receitas";


        // Login na API Rest com administrador
        String token;

        //Inserir o novo registro
        given()
                .body("{\n" +
                    "\"valor\": \"2100\",\n" +
                    "\"descricao\": \"Investimento a curto Prazo\",\n" +
                    "\"data\": \"30/04/2022\"" +
                "}")
                .contentType(ContentType.JSON)
        .when().post()
        .then()
                .log().all()
                .assertThat()
                            .statusCode(400);
    }


}
