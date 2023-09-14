package starter.utils;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.io.File;

public class GorestAPI {
    public static final String BASE_URL = "https://gorest.co.in/public/v2";
    public static final String DIR = System.getProperty("user.dir");
    public static String REQ_BODY = DIR+"/src/test/resources/JSON/RequestBody/";
    public static String PATH_USERS = "/users";
    public static String AUTHORIZATION = "Authorization";
    public static String BEARER_TOKEN = "Bearer f74b2dea69fc93cf2712abe92c727bad9dee9fa1a3d19f10504663b4bd3d1174";

    public static Integer USER_ID;

    public static String CREATE_USER = BASE_URL+PATH_USERS;
    public static String GET_USER_DETAIL = BASE_URL+PATH_USERS+"/{id}";
    public static String UPDATE_USER = BASE_URL+PATH_USERS+"/{id}";
    public static String DELETE_USER = BASE_URL+PATH_USERS+"/{id}";

    @Step("Create new user")
    public void createANewUser(File json){
        SerenityRest.given()
                .headers(AUTHORIZATION, BEARER_TOKEN)
                .contentType(ContentType.JSON)
                .body(json);
    }

    @Step("Get detail user")
    public void getDetailUser(Object id){
        SerenityRest.given()
                .headers(AUTHORIZATION, BEARER_TOKEN)
                .pathParam("id", id);
    }

    @Step("Get detail user")
    public void updateUser(Object id, File json){
        SerenityRest.given()
                .headers(AUTHORIZATION, BEARER_TOKEN)
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(json);
    }

    @Step("Delete user")
    public void deleteUser(Object id){
        SerenityRest.given()
                .headers(AUTHORIZATION, BEARER_TOKEN)
                .pathParam("id", id);
    }
}
