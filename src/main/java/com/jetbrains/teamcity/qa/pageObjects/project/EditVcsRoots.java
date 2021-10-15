package com.jetbrains.teamcity.qa.pageObjects.project;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class EditVcsRoots extends EditProjectVcsRoots {

    @Step("Set custom polling interval = {seconds} seconds")
    public EditVcsRoots setCustomPollingInterval(Integer seconds) {
        $(".advancedSettingsToggle a").scrollTo().click();
        $("#mod-check-interval-specified").scrollTo().selectRadio("SPECIFIED");
        $("#modificationCheckInterval").val(seconds.toString());
        return this;
    }

    @Step("Set branch specification = {value}")
    public EditVcsRoots editBranchSpec(String value) {
        $("#teamcity\\:branchSpec").val(value);
        return this;
    }

    @Step("Save project VCS roots")
    public EditProjectVcsRoots save() {
        $(".saveButtonsBlock .submitButton").click();
        return page(EditProjectVcsRoots.class);
    }

}
