package com.jetbrains.teamcity.qa.pageObjects.project;

import com.jetbrains.teamcity.qa.pageObjects.BasePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class EditProject extends BasePage {

    @Step("Open general settings")
    public EditProjectGeneralSettings openGeneralSettings() {
        $("#projectGeneralTab_Tab").click();
        return page(EditProjectGeneralSettings.class);
    }

    @Step("Open VCS roots")
    public EditProjectVcsRoots openVcsRoots() {
        $("#projectVcsRoots_Tab").click();
        return page(EditProjectVcsRoots.class);
    }

}
