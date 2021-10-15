package com.jetbrains.teamcity.qa.pageObjects.main;

import com.jetbrains.teamcity.qa.pageObjects.BasePage;
import com.jetbrains.teamcity.qa.pageObjects.project.Project;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class Projects extends BasePage {

    @Step("Open project = {projectId}")
    public Project openProject(String projectId) {
        $(".projectName a[href*='" + projectId + "']").should(exist).click();
        return page(Project.class);

    }


}
