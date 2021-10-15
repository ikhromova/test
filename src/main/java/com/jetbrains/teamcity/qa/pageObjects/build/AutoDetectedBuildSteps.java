package com.jetbrains.teamcity.qa.pageObjects.build;

import com.codeborne.selenide.SelenideElement;
import com.jetbrains.teamcity.qa.pageObjects.build.components.BuildSteps;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.page;

public class AutoDetectedBuildSteps extends EditBuildConfiguration<AutoDetectedBuildSteps> {
    private SelenideElement discoveredRunners = $("#discoveredRunners").shouldBe(visible, longTimeout);

    @Step("Discovered runners should contain {buildSteps}")
    public AutoDetectedBuildSteps checkDiscoveredRunnersContain(String buildSteps) {
        $$(byXpath("//*[@id=\"discoveredRunners\"]/table/tbody/*/td[2]")).findBy(text(buildSteps)).shouldBe(visible, longTimeout).should(exist);
        return this;
    }

    @Step("Select all detected steps")
    public AutoDetectedBuildSteps selectAllSteps() {
        discoveredRunners.$$(" #runnerId").forEach(SelenideElement::click);
        return this;
    }

    @Step("Submit use selected steps")
    public BuildSteps useSelected() {
        discoveredRunners.$(" .btn.btn_hint").click();
        return page(BuildSteps.class);
    }

    @Step("Use all autodetected steps")
    public BuildSteps useAllAutodetectedSteps() {
        return checkDiscoveredRunnersContain("Gradle").selectAllSteps().useSelected();
    }
}
