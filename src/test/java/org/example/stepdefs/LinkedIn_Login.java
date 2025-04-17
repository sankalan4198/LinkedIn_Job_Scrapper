package org.example.stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.example.pages.LinkedIn_LoginPage;
import org.example.stepdefs.Hooks;

import java.io.IOException;

public class LinkedIn_Login {

    private LinkedIn_LoginPage linkedIn_loginPage;
    WebDriver driver=Hooks.driver;

    @Given("I am on the LinkedIn Home page")
    public void setDriver()
    {
        driver.get("https://www.linkedin.com/home");
        linkedIn_loginPage=new LinkedIn_LoginPage(driver);
    }

    @Given("I click on the signIN button")
    public void set_SignIn() throws InterruptedException {
        linkedIn_loginPage.LogIn();
        //driver.get("https://www.linkedin.com/jobs/search/?currentJobId=4183925136&f_TPR=r86400&geoId=105214831&keywords=Software%20QA%20engineer&origin=JOB_SEARCH_PAGE_JOB_FILTER&refresh=true");
    }

    @When("I enter my user name and password")
    public void setCreds() throws InterruptedException{
        linkedIn_loginPage.setCredentials();
    }

    @Then("I should be logged in successfully")
    public void loggedIn() throws Exception {
        linkedIn_loginPage.verifySuccessfulLogin();
    }

    @Then("I navigate to jobs")
    public void navigate_to_jobs() throws InterruptedException {
        linkedIn_loginPage.navigateToJobs();
        Thread.sleep(3000);
    }

    @Then("I set the Job Title and Job Location")
    public void set_title_location() throws InterruptedException {
        linkedIn_loginPage.set_jobTitle_jobLocation();
    }

    @Then("I filter out the date posted as past 24hrs")
    public void filter_date_past_24hrs() throws InterruptedException {
        linkedIn_loginPage.filterDate();
    }

    @Then("I extract the Job Data as per Company Name and Job Role")
    public void extractData() throws Exception {
        linkedIn_loginPage.extractFromThreePages();
    }

}
