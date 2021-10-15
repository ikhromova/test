package com.jetbrains.teamcity.qa.pageObjects.build;

import com.jetbrains.teamcity.qa.pageObjects.build.components.Triggers;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byTagName;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class EditBuildConfiguration<T extends EditBuildConfiguration<T>> extends Build {

    @Step("Get project id")
    public String getProjectId() {
        return $(".last").shouldBe(visible).attr("data-projectid");
    }

    @Step("VCS roots counter should be equal = {count}")
    public EditBuildConfiguration vcsRootsCounterShouldBe(Integer count) {
        $("#vcsRoots_Tab .tabCounter").shouldBe(text(count.toString()));
        return this;
    }

    @Step("Build triggers counter should equal {count}")
    public EditBuildConfiguration buildTriggersCounterShouldBe(Integer count) {
        $("#buildTriggers_Tab .tabCounter").shouldBe(text(count.toString()));
        return this;
    }

    @Step("Open build triggers tab")
    public Triggers openBuildTriggersTab() {
        $("#buildTriggers_Tab").click();
        return page(Triggers.class);
    }

    @Step("Subtitle should be equal {text}")
    public T subTitleShouldEqual(String text) {
        $(byTagName("h2")).shouldBe(exactText(text));
        return (T) this;
    }
}
