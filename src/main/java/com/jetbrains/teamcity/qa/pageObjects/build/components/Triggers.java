package com.jetbrains.teamcity.qa.pageObjects.build.components;

import com.jetbrains.teamcity.qa.pageObjects.build.EditBuildConfiguration;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class Triggers extends EditBuildConfiguration<Triggers> {

    @Step("Add VCS trigger")
    public Triggers addVcsTrigger(String branchFilter) {
        $(".section .btn .addNew").click();
        $("#-ufd-teamcity-ui-triggerNameSelector").click();
        $(".option[data-title='VCS Trigger']").click();
        $("#branchFilter").val(branchFilter);
        $("#editTriggerSubmit").click();
        return this;
    }

}
