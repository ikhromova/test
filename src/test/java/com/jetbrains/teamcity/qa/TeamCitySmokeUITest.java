package com.jetbrains.teamcity.qa;

import com.jetbrains.teamcity.qa.pageObjects.BasePage;
import com.jetbrains.teamcity.qa.pageObjects.build.Build;
import org.testng.annotations.Test;

import java.io.IOException;

public class TeamCitySmokeUITest extends BaseTest {

    @Test(description = "Create project")
    public void createProject() {
        var createProjectMenu = new BasePage().goToAdministration().createProject();
        var createProjectFromUrlSetup =  createProjectMenu
                .fillFormAndSubmit(repositoryUrl, githubUser, githubToken());

       var projectName = createProjectFromUrlSetup.setRandomProjectName();

       var autoDetectedBuildSteps = createProjectFromUrlSetup
                .checkTheForm()
                .checkProjectNameIsFilled(projectName)
                .checkBuildTypeNameIsFilled(buildTypeName)
                .checkBranchIsFilled("refs/heads/master")
                .checkBranchSpecIsFilled("refs/heads/*")
                .submit();

        var buildSteps = autoDetectedBuildSteps
                .subTitleShouldEqual("Auto-detected Build Steps")
                .checkDiscoveredRunnersContain(runner)
                .selectAllSteps()
                .useSelected();

        buildSteps
                .subTitleShouldEqual("Build Steps")
                .successMessageIsShown()
                .buildStepsCountShouldEqual(1)
                .buildStepsShouldContain(runner)
                .vcsRootsCounterShouldBe(1)
                .buildTriggersCounterShouldBe(1);

        var projectId = buildSteps.getProjectId();

        var editProjectSettings = new BasePage().openProjectsTab().openProject(projectId).editProjectSettings();
        editProjectSettings
                .projectNameShouldBeEqual(projectName)
                .projectIdShouldBeEqual(projectId)
                .buildConfigurationNameShouldEqual(buildTypeName)
                .buildConfigurationStepsShouldEqual(runner);
        editProjectSettings.openVcsRoots().vcsRootShouldContain(repositoryUrl);
    }

    @Test(description = "Run build")
    public void runBuild() {
        var projectId = createDefaultProject();
        new BasePage().openProjectsTab().openProject(projectId).openBuild().clickRunBtn();
        var build = new Build();
        var buildResults = build
                .runningStatusShouldEqual("1 build running")
                .openFirstBuildResults();
        buildResults
                .titleShouldContainTexts("#1")
                .openChangesTab()
                .vcsRootShouldContain(repositoryUrl)
                .revisionBranchShouldContain("refs/heads/master")
                .openResultsTab()
                .passedTestBlockShouldEqual("1 test passed");
    }

    @Test(description = "Run build from vcs trigger")
    public void runBuildFromVcsTrigger() throws NullPointerException, IOException {
        var projectId = createDefaultProject();
        var generalSettings = new BasePage()
                .openProjectsTab()
                .openProject(projectId)
                .editProjectSettings().openVcsRoots().editVcsRoot()
                .editBranchSpec("+:refs/pull/*")
                .setCustomPollingInterval(2)
                .save().openGeneralSettings();
        generalSettings
                .editBuildConfiguration()
                .openBuildTriggersTab()
                .addVcsTrigger("+:*/merge");

        VcsMethods.closePreviousPullRequest(githubAuthToken);
        var url = VcsMethods.createPullRequest(githubAuthToken);

        new BasePage().openProjectsTab().openProject(projectId)
                .openBuild().openOverviewTab()
                .runningStatusShouldEqual("1 build running")
                .openFirstBuildResults()
                .titleShouldContainTexts("#1");

        VcsMethods.closePullRequest(url, githubAuthToken);
    }
}
