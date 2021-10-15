package com.jetbrains.teamcity.qa.pageObjects.login;

import com.jetbrains.teamcity.qa.pageObjects.main.Projects;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class Login {

    @Step("Login as {userName}/{password}")
    public Projects loginAs(String userName, String password) {
        $("#username").val(userName);
        $("#password").val(password);
        $("[name='submitLogin']").click();
        return page(Projects.class);
    }

}
