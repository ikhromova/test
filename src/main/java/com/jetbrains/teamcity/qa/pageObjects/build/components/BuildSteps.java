package com.jetbrains.teamcity.qa.pageObjects.build.components;

import com.codeborne.selenide.ElementsCollection;
import com.jetbrains.teamcity.qa.pageObjects.build.EditBuildConfiguration;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.containExactTextsCaseSensitive;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BuildSteps extends EditBuildConfiguration<BuildSteps> {
    private ElementsCollection stepNames = $$(".stepName .stepName");

    @Step("Build steps should contain {buildStepName}")
    public BuildSteps buildStepsShouldContain(String... buildStepName) {
        stepNames.should(containExactTextsCaseSensitive(buildStepName));
        return this;
    }

    @Step("Build steps count should be equal {count}")
    public BuildSteps buildStepsCountShouldEqual(int count) {
       stepNames.shouldHave(sizeGreaterThanOrEqual(count));
       return this;
    }

    @Step("Update message is shown")
    public BuildSteps successMessageIsShown() {
       $("#unprocessed_buildRunnerSettingsUpdated").should(exist);
       return this;
    }
}
