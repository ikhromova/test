package com.jetbrains.teamcity.qa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.jetbrains.teamcity.qa.pageObjects.BasePage;
import com.jetbrains.teamcity.qa.pageObjects.login.Login;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.util.Base64;
import java.util.Optional;

import static com.codeborne.selenide.Selenide.open;

public class BaseTestDouble {
    public String repositoryUrl = "https://github.com/ikhromova/gradle-project-test";
    public String githubUser = "test-integration-adventure";
    public String githubPair = githubUser + ":" + githubToken();
    public String githubAuthToken = Base64.getEncoder().encodeToString(githubPair.getBytes());
    public String buildTypeName = "Build";
    public String runner = "Gradle";
    public String runnerdsaad = "Gradle";

    @BeforeSuite(description = "Set up configuration")
    public void setUpConfiguration() {
        Configuration.baseUrl = url();
        Configuration.remote = token();
    }

    @BeforeMethod(description = "Login to TeamCity server")
    public void loginToTeamCity() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(false).savePageSource(true).includeSelenideSteps(true));
        open("/");
        new Login().loginAs(null, token());
    }

    @AfterMethod(description = "Close Selenide")
    public void closeSelenide() {
        SelenideLogger.removeListener("AllureSelenide");
    }

    @AfterSuite(description = "Clean up after tests")
    public void tearDown() {
        loginToTeamCity();
        new BasePage().goToAdministration().pauseAllBuilds();
    }

    @Step("Create default project")
    public String createDefaultProject() {
        var createProjectFromUrlSetup = new BasePage().goToAdministration().createProject()
                .fillFormAndSubmit(repositoryUrl, githubUser, githubToken());
        createProjectFromUrlSetup.setRandomProjectName();
        var autoDetectedBuildSteps = createProjectFromUrlSetup.checkTheForm().submit();
        var buildSteps = autoDetectedBuildSteps.useAllAutodetectedSteps().successMessageIsShown();
        return buildSteps.getProjectId();
    }

    public String url() {
        return Optional.ofNullable(System.getProperty("url"))
                .orElseThrow(() -> new AssertionError("URL property doesn't set"));
    }

    public String token() {
        return Optional.ofNullable(System.getProperty("token"))
                .orElseThrow(() -> new AssertionError("Token property doesn't set"));
    }

    public String githubToken() {
        return Optional.ofNullable(System.getProperty("github-token"))
                .orElseThrow(() -> new AssertionError("Github-token property doesn't set"));
    }

}
