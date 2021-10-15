package com.jetbrains.teamcity.qa.pageObjects.project;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.jetbrains.teamcity.qa.pageObjects.BasePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CreateProjectMenu extends BasePage {
    private SelenideElement userName = $("#username");

    @Step("Fill form and submit")
    public CreateProjectFromUrlSetup fillFormAndSubmit(String repositoryUrl, String username, String password){
        return this
                .checkTheForm()
                .fillRepositoryUrl(repositoryUrl)
                .fillUserName(username)
                .fillToken(password)
                .submit();
    }

    @Step("Check the form")
    public CreateProjectMenu checkTheForm() {
        titleShouldContainText("Create project");
        $("#parentId").shouldHave(value("_Root"));
        $(".menuList_create").find(".createOption.expanded").shouldHave(text("From a repository URL"));
        return this;
    }

    @Step("Fill the repository url")
    public CreateProjectMenu fillRepositoryUrl(String url) {
        $("#url").shouldBe(visible).val(url);
        return this;
    }

    @Step("Fill the username")
    public CreateProjectMenu fillUserName(String username) {
        userName.shouldBe(visible).val(username);
        return  this;
    }

    @Step("Fill the token")
    public CreateProjectMenu fillToken(String token) {
        $("#password").shouldBe(visible).val(token);
        return  this;
    }

    @Step("Submit form")
    public CreateProjectFromUrlSetup submit() {
        $("[type='submit']").click();
        userName.shouldNotBe(visible, longTimeout);
        return Selenide.page(CreateProjectFromUrlSetup.class);
    }
}
