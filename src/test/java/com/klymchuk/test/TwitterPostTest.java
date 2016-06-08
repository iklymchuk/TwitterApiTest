package com.klymchuk.test;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import com.klymchuk.util.Data;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.model.SeverityLevel;

import java.util.List;

/**
 * Created by iklymchuk on 6/7/16.
 */
@Features("Check posting availability using Twitter API")
public class TwitterPostTest extends BasicTest {

    @Stories("Check successful post")
    @Severity(value = SeverityLevel.CRITICAL)
    @Test(priority = 0, groups = "critical")
    public void checkSuccessfulPost() {

            String actualResult = given()
                 .auth().oauth(Data.CONSUMER_KEY, Data.CONSUMER_SECRET, Data.ACCESS_TOKEN, Data.ACCESS_TOKEN_SECRET)
                 .pathParam("status", Data.POST_VALUE)
            .when()
                 .post("/1.1/statuses/update.json?status={status}")
            .then()
                .statusCode(Data.SUCCESS_CODE)
            .extract()
                .path("text");

            List<String> currentResult = given()
                .auth().oauth(Data.CONSUMER_KEY, Data.CONSUMER_SECRET, Data.ACCESS_TOKEN, Data.ACCESS_TOKEN_SECRET)
                .pathParam("count", Data.COUNT_OF_POST)
            .when()
                .get("/1.1/statuses/user_timeline.json?count={count}")
             .then()
                .statusCode(Data.SUCCESS_CODE)
            .extract()
                .path("text");

        assertThat(actualResult, equalTo(currentResult.get(0)));

    }

    @Stories("Check dublicate post")
    @Severity(value = SeverityLevel.CRITICAL)
    @Test(priority = 1, groups = "critical")
    public void checkDublicatePost() {

            given()
                .auth().oauth(Data.CONSUMER_KEY, Data.CONSUMER_SECRET, Data.ACCESS_TOKEN, Data.ACCESS_TOKEN_SECRET)
                .pathParam("status", Data.POST_VALUE)
            .when()
                .post("/1.1/statuses/update.json?status={status}")
            .then()
                .statusCode(Data.FORBIDDEN_CODE);
    }

    @Stories("Check post with empty required parameter")
    @Severity(value = SeverityLevel.CRITICAL)
    @Test(priority = 2, groups = "critical")
    public void checkPostEmptyRequiredParameter() {

            given()
                .auth().oauth(Data.CONSUMER_KEY, Data.CONSUMER_SECRET, Data.ACCESS_TOKEN, Data.ACCESS_TOKEN_SECRET)
            .when()
                .post("/1.1/statuses/update.json")
            .then()
                .statusCode(Data.FORBIDDEN_CODE);

    }
}
