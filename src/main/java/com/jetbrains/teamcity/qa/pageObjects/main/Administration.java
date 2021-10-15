package com.jetbrains.teamcity.qa.pageObjects.main;

import com.jetbrains.teamcity.qa.pageObjects.BasePage;
import com.jetbrains.teamcity.qa.pageObjects.project.CreateProjectMenu;
import com.jetbrains.teamcity.qa.pageObjects.project.EditProjectGeneralSettings;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class Administration extends BasePage {

    @Step("Open project = {projectId}")
    public EditProjectGeneralSettings openProject(String projectId) {
        $(".project_name a[href*='" + projectId + "']").click();
        return page(EditProjectGeneralSettings.class);
    }

    @Step("Create project")
    public CreateProjectMenu createProject() {
        $(".createProject > .btn").click();
        return page(CreateProjectMenu.class);
    }

    @Step("Pause all builds in existing projects")
    public void pauseAllBuilds() {
        this.openProject("=_Root");
        $("[data-hint-container-id='project-admin-actions'] [type='button']").shouldBe(visible).click();
        $("#sp_span_prjActions_RootContent").find(By.linkText("Pause/Activate...")).click();
        $("#pausedStatusTree [type='checkbox'][value='_Root']").click();
        $("#removeAllFromQueue").click();
        $(".popupSaveButtonsBlock [type='submit'][value='Apply']").click();
    }

}
