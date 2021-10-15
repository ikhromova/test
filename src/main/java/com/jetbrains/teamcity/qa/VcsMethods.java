package com.jetbrains.teamcity.qa;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class VcsMethods {

    public static RequestSpecification withHeaders(String authToken) {
        return RestAssured.given()
                .header("Accept", "application/vnd.github.v3+json")
                .header("Authorization", "Basic " + authToken);
    }

    @Step("Get pull requests from Github")
    public static String getPullRequest(String authToken) {
        var response = withHeaders(authToken)
                .get("https://api.github.com/repos/ikhromova/gradle-project-test/pulls");
        response.then().statusCode(200);
        return response.then().extract().path("[0].url");
    }

    @Step("Create pull request to Github")
    public static String createPullRequest(String authToken) {
        var response = withHeaders(authToken)
                .header("Content-Type", "application/json")
                .body("{\"head\":\"test-branch\",\"base\":\"master\", \"title\":\"Create pull request\"}")
                .post("https://api.github.com/repos/ikhromova/gradle-project-test/pulls");
        response.then().statusCode(201);
        return response.then().extract().path("url");
    }

    @Step("Close pull request")
    public static void closePullRequest(String url, String authToken) {
        var response = withHeaders(authToken)
                .header("Content-Type", "application/json")
                .body("{\"state\":\"closed\"}")
                .patch(url);
        response.then().statusCode(200);
    }

    @Step("Close previous pull requests from Github")
    public static void closePreviousPullRequest(String authToken) {
        var pr = getPullRequest(authToken);
        if (pr != null) {
            closePullRequest(pr, authToken);
        }
    }
}
