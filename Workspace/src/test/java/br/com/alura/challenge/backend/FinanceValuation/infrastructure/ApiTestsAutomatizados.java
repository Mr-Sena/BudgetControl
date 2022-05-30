package br.com.alura.challenge.backend.FinanceValuation.infrastructure;

import br.com.alura.challenge.backend.FinanceValuation.domain.Despesa;
import br.com.alura.challenge.backend.FinanceValuation.domain.Receita;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class ApiTestsAutomatizados {

    private static Receita receita;
    private static Despesa despesa;
    private static long registroID;
    private static Integer anoData;
    private static Integer mesData;
    private static String description;

    @Test
    public void testAutomatizadosNaChamadaDaAPI_Receitas() {


        // Login na API Rest

        baseURI = "http://localhost";
        port = 8080;
        basePath = "/auth";


        String token = given()
                .body("{\n" +
                        "    \"email\": \"Laurence.net52@mail.com\",\n" +
                        "    \"senha\": \"123456789\"\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .log().all()
                .extract()
                .path("token");


        baseURI = "http://localhost";
        port = 8080;
        basePath = "/receitas";


        //Inserir novo registro -> Expected: Created (Http Status 201)
        receita = given()
                .header("Authorization", "Bearer " + token)
                .body("{\n" +
                        "\"valor\": \"2100\",\n" +
                        "\"descricao\": \"Renda passiva\",\n" +
                        "\"data\": \"30/06/2022\"" +
                        "}")
                .contentType(ContentType.JSON)
                .when().post()
                .andReturn()
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .and().extract().body().as(Receita.class);


        //IDENTIFICAÇÃO DO REGISTRO
        registroID = receita.getId();
        anoData = receita.getData().getYear();
        mesData = receita.getData().getMonthValue();
        description = receita.getDescricao();

        System.out.println("Id do registro: " + receita.getId()
                + "\nData da criação: " + receita.getData());


        //Inserção de registro duplicado -> Expected: Bad Request (Http Status) 400
        given()
                .header("Authorization", "Bearer " + token)
                .body("{\n" +
                        "\"valor\": \"2100\",\n" +
                        "\"descricao\": \"Renda passiva\",\n" +
                        "\"data\": \"30/06/2022\"" +
                        "}")
                .contentType(ContentType.JSON)
                .when().post()
                .andReturn()
                .then()
                .log().all()
                .assertThat()
                .statusCode(400);


        //Consulta -> Expected: Ok - Http Status Code - 200
        given()
                .header("Authorization", "Bearer " + token)
                .when().get()
                .then()
                .log().all()
                .assertThat().statusCode(200);


        //Consulta por ID -> Expected: Ok - Http Status - 200
        given().pathParam("id", registroID)
                .header("Authorization", "Bearer " + token)
                .get("/{id}")
                .then()
                .assertThat().statusCode(200);


        //Consulta por Descrição -> Expected: Ok - Http Status - 200
        given().param("descricao", "Ouro")
                .header("Authorization", "Bearer " + token)
                .when().get()
                .then()
                .assertThat().statusCode(200);


        //Consulta por Data -> Expected: Ok - Http Status - 200
        given().pathParam("ano", anoData)
                .pathParam("mes", mesData)
                .header("Authorization", "Bearer " + token)
                .when().get("/{ano}/{mes}")
                .then()
                .assertThat().statusCode(200);


        //Update -> Expected: Ok - Http Status 200
        given()
                .body("{\n" +
                        "\"valor\": \"2100\",\n" +
                        "\"descricao\": \"Renda passiva\",\n" +
                        "\"data\": \"30/06/2023\"" +
                        "}")
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .pathParam("id", registroID)
                .when().put("/{id}")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);


        //Exclusao do Registro -> Expected: Ok - Http Status - 200
        given().pathParam("id", registroID)
                .header("Authorization", "Bearer " + token)
                .when().delete("/{id}")
                .then()
                .log().all()
                .assertThat().statusCode(200);


    }





    @Test
    public void testAutomatizadosNaChamadaDaAPI_Despesas() {


        // Login na API Rest

        baseURI = "http://localhost";
        port = 8080;
        basePath = "/auth";


        String token = given()
                .body("{\n" +
                        "    \"email\": \"Laurence.net52@mail.com\",\n" +
                        "    \"senha\": \"123456789\"\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then()
                .log().all()
                .extract()
                .path("token");


        baseURI = "http://localhost";
        port = 8080;
        basePath = "/despesas";


        //Inserir novo registro -> Expected: Created (Http Status 201)
        despesa = given()
                .header("Authorization", "Bearer " + token)
                .body("{\n" +
                        "\"valor\": \"300\",\n" +
                        "\"descricao\": \"Contas de água e luz\",\n" +
                        "\"data\": \"05/06/2022\"" +
                        "}")
                .contentType(ContentType.JSON)
                .when().post()
                .andReturn()
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .and().extract().body().as(Despesa.class);


        //IDENTIFICAÇÃO DO REGISTRO
        registroID = despesa.getId();
        anoData = despesa.getData().getYear();
        mesData = despesa.getData().getMonthValue();
        description = despesa.getDescricao();



        //Inserção de registro duplicado -> Expected: Bad Request (Http Status) 400
        given()
                .header("Authorization", "Bearer " + token)
                .body("{\n" +
                        "\"valor\": \"300\",\n" +
                        "\"descricao\": \"Contas de água e luz\",\n" +
                        "\"data\": \"05/06/2022\"" +
                        "}")
                .contentType(ContentType.JSON)
                .when().post()
                .andReturn()
                .then()
                .log().all()
                .assertThat()
                .statusCode(400);


        //Consulta -> Expected: Ok - Http Status Code - 200
        given()
                .header("Authorization", "Bearer " + token)
                .when().get()
                .then()
                .log().all()
                .assertThat().statusCode(200);


        //Consulta por ID -> Expected: Ok - Http Status - 200
        given().pathParam("id", registroID)
                .header("Authorization", "Bearer " + token)
                .get("/{id}")
                .then()
                .assertThat().statusCode(200);


        //Consulta por Descrição -> Expected: Ok - Http Status - 200
        given().param("descricao", despesa.getDescricao())
                .header("Authorization", "Bearer " + token)
                .when().get()
                .then()
                .assertThat().statusCode(200);


        //Consulta por Data -> Expected: Ok - Http Status - 200
        given().pathParam("ano", anoData)
                .pathParam("mes", mesData)
                .header("Authorization", "Bearer " + token)
                .when().get("/{ano}/{mes}")
                .then()
                .assertThat().statusCode(200);


        //Update -> Expected: Ok - Http Status 200
        given()
                .body("{\n" +
                        "\"valor\": \"1000\",\n" +
                        "\"descricao\": \"Aluguel\",\n" +
                        "\"data\": \"30/06/2023\"" +
                        "}")
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .pathParam("id", registroID)
                .when().put("/{id}")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);


        //Exclusao do Registro -> Expected: Ok - Http Status - 200
        given().pathParam("id", registroID)
                .header("Authorization", "Bearer " + token)
                .when().delete("/{id}")
                .then()
                .log().all()
                .assertThat().statusCode(200);


    }


}
