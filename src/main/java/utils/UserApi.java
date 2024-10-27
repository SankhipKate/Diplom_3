package utils;

import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.qameta.allure.model.Parameter.Mode.HIDDEN;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_ACCEPTED;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class UserApi {

    private static final String BASE_API_URL = "https://stellarburgers.nomoreparties.site/api";
    private static final String REGISTER_ENDPOINT = "/auth/register";
    private static final String LOGIN_ENDPOINT = "/auth/login";
    private static final String DELETE_ENDPOINT = "/auth/user";
    public static final String TEST = "test";
    public static final String TEST_COM = "@test.com";
    public static final String PASSWORD = "password";
    public static final String TEST_USER = "Test User";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String FAILED_TO_SERIALIZE_REQUEST_BODY = "Failed to serialize request body";
    public static final String CONTENT_TYPE = "Content-type";
    public static final String CONTENT_TYPE1 = "application/json";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String AUTHORIZATION = "Authorization";

    public static String email;
    public static String password;
    public static String token;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Step("Создание тестового пользователя")
    public static void createTestUser() {
        email = TEST + UUID.randomUUID().toString() + TEST_COM;
        password = PASSWORD + UUID.randomUUID().toString();
        token = createUser(email, password, TEST_USER);
    }

    @Step("Создание пользователя")
    public static String createUser(String email, String password, String name) {
        RestAssured.baseURI = BASE_API_URL;

        // Формируем тело запроса
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put(EMAIL, email);
        requestBody.put(PASSWORD, password);
        requestBody.put(NAME, name);

        // Сериализуем объект в JSON
        String jsonBody;
        try {
            jsonBody = objectMapper.writeValueAsString(requestBody);
        } catch (Exception e) {
            throw new RuntimeException(FAILED_TO_SERIALIZE_REQUEST_BODY, e);
        }

        // Отправляем POST-запрос на регистрацию пользователя
        Response response = given().log().all()
                .header(CONTENT_TYPE, CONTENT_TYPE1)
                .and()
                .body(jsonBody)
                .when()
                .post(REGISTER_ENDPOINT)
                .then()
                .extract().response();

        // Возвращаем токен для дальнейших действий с пользователем
        return response.jsonPath().getString(ACCESS_TOKEN);
    }

    @Step("Получение токена")
    public static String getToken(String email, String password) {
        RestAssured.baseURI = BASE_API_URL;

        // Формируем тело запроса для входа
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put(EMAIL, email);
        requestBody.put(PASSWORD, password);

        // Сериализуем объект в JSON
        String jsonBody;
        try {
            jsonBody = objectMapper.writeValueAsString(requestBody);
        } catch (Exception e) {
            throw new RuntimeException(FAILED_TO_SERIALIZE_REQUEST_BODY, e);
        }

        // Отправляем POST-запрос на вход
        Response response = given().log().all()
                .header(CONTENT_TYPE, CONTENT_TYPE1)
                .and()
                .body(jsonBody)
                .when()
                .post(LOGIN_ENDPOINT)
                .then()
                .extract().response();

        // Возвращаем токен для дальнейших действий с пользователем
        return response.jsonPath().getString(ACCESS_TOKEN);
    }

    @Step("Логин пользователя")
    public static void loginUser(String email, String password) {
        // Получаем токен с помощью getToken и сохраняем его в поле token
        token = getToken(email, password);
    }

    @Step("Удаление пользователя")
    public static void deleteUser(@Param(mode = HIDDEN) String token) {
        RestAssured.baseURI = BASE_API_URL;

        // Отправляем DELETE-запрос на удаление пользователя
        given().log().all()
                .header(AUTHORIZATION, token)
                .when()
                .delete(DELETE_ENDPOINT)
                .then()
                .statusCode(anyOf(is(HTTP_OK), is(HTTP_ACCEPTED)));
    }
}