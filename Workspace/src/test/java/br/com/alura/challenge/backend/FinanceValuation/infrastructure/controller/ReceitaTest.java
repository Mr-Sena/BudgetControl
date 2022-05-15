package br.com.alura.challenge.backend.FinanceValuation.infrastructure.controller;

import br.com.alura.challenge.backend.FinanceValuation.domain.Receita;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
//import org.junit.Test;

import static io.restassured.RestAssured.*;

public class ReceitaTest {

    //TODO: Definir uma ordem de execução para os métodos - eles estão dependentes!

    private static Receita registroCriado;
    private static long registroID;
    private static Integer anoData;
    private static Integer mesData;

    @BeforeAll
    public static void callAPI() {

        baseURI = "http://localhost";
        port = 8080;
        basePath = "/receitas";

    }


    @Test
    public void testWhen_InsertNewReceita_Expected_ReturnStatusCode201Created() {

        // Login na API Rest com administrador
        String token;

        //Inserir o novo registro
        registroCriado = given()
                .body("{\n" +
                    "\"valor\": \"2100\",\n" +
                    "\"descricao\": \"Renda passiva\",\n" +
                    "\"data\": \"30/04/2022\"" +
                "}")
                .contentType(ContentType.JSON)
        .when().post()
        .andReturn()
        .then()
                .log().all()
                .assertThat()
                            .statusCode(201)
                .and().extract().body().as(Receita.class);

        registroID = registroCriado.getId();
        anoData = registroCriado.getData().getYear();
        mesData = registroCriado.getData().getMonthValue();
        System.out.println("Id do registro" + registroCriado.getId()
                + "\nData da criação: " + registroCriado.getData());

        //Obs: O teste só vai funcionar caso o registro for inexistente no banco de dados
        //no momento da inserção, em caso de duplicidade (Mesma descrição e data), o resultado é o code 400

    }


    @Test
    public void testWhen_InsertAgainReceita_Expected_ReturnStatusCode400() {

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
                //.log().all()
                .assertThat()
                            .statusCode(400);
    }


    @Test
    public void testWhen_requestedQuery_Expected_ReturnStatusCode200() {

        given().when()
                .get()
                .then()
                //.log().all()
                .assertThat().statusCode(200);

    }

    @Test
    public void testWhen_requestedQueryById_Expected_ReturnStatusCode200() {

        given().pathParam("id", 1)
                .when()
                .get("/{id}")
                .then()
                .assertThat().statusCode(200);

    }



    @Test
    public void testWhen_requestedQueryByDescricao_Expected_ReturnStatusCode200() {

        given().param("descricao", "Ouro")
                .when()
                .get()
                .then()
                .assertThat().statusCode(200);

    }

    @Test
    public void testWhen_requestedQueryByDate_Expected_ReturnStatusCode200() {

        given().pathParam("ano", anoData)
                .pathParam("mes", mesData)
                .when().get("/{ano}/{mes}")
                .then()
                .assertThat().statusCode(200);

    }

    @Test
    public void testWhen_AUpdatedRegister_Expected_ReturnStatusCode200() {

        given()
                .body("{\n" +
                        "\"valor\": \"2100\",\n" +
                        "\"descricao\": \"Renda passiva -> id: " + registroID + "\",\n" +
                        "\"data\": \"30/04/2022\"" +
                        "}")
                .contentType(ContentType.JSON)
                .pathParam("id", registroID)
                .when().put("/{id}")
                .then()
                .log().all()
                .assertThat().statusCode(200);

    }

    @Test
    public void testWhen_DeletedRegister_Expected_ReturnStatusCode200() {


        given().pathParam("id", registroID)
                .when().delete("/{id}")
                .then()
                .log().all()
                .assertThat().statusCode(200);

    }



}
