package com.jetbrains.teamcity.qa.pageObjects.project;

import com.jetbrains.teamcity.qa.pageObjects.BasePage;
import com.jetbrains.teamcity.qa.pageObjects.build.Build;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class Project extends BasePage {

    @Step("Go to edit project settings")
    public EditProjectGeneralSettings editProjectSettings() {
        $("[title='Edit project settings']").click();
        return page(EditProjectGeneralSettings.class);
    }

    @Step("Open build")
    public Build openBuild() {
        $(".buildTypeName").click();
        return page(Build.class);
    }


}
