package com.jetbrains.teamcity.qa.pageObjects;

import com.jetbrains.teamcity.qa.pageObjects.main.Administration;
import com.jetbrains.teamcity.qa.pageObjects.main.Projects;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class BasePage {

    public static final Duration longTimeout  = Duration.ofSeconds(15);
    public static final Duration superLongTimeout  = Duration.ofSeconds(30);
    public static final Duration timeoutForRunBuild  = Duration.ofSeconds(300);

    @Step("Go to administration page")
    public Administration  goToAdministration() {
        $("[data-hint-container-id='header-administration-link'] a").click();
        return page(Administration.class);
    }

    @Step("Open all projects tab")
    public Projects openProjectsTab() {
        $("[title='Projects']").click();
        return page(Projects.class);
    }

    @Step("Check page title equal to \"{titleText}\"")
    public void titleShouldContainText(String titleText) {
        $("#restPageTitle a").shouldBe(visible).shouldHave(text(titleText));
    }
}
