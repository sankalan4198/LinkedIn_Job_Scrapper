package org.example.pages;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.List;

public class LinkedIn_LoginPage {
    private WebDriver driver;

    //Locator
    private By signIn=By.xpath("//a[contains(text(),'Sign in')]");
    private By userName=By.xpath("//input[@id='username']");
    private By password=By.xpath("//input[@id='password']");
    private By signInButton=By.xpath("//button[@type='submit']");
    private By JobTitle=By.xpath("//a[contains(@class,'disabled ember-view job-card-container__link')]//span//strong");
    private By comapny=By.xpath("//input[@role='switch']//following::ul//following::div[@class='artdeco-entity-lockup__subtitle ember-view']//span");
    private By leftPane=By.xpath("//div[@data-results-list-top-scroll-sentinel]");
    private By targetElement=By.xpath("(//*[text()='Promoted'])[last()]");
    private By id_verification=By.xpath("//a[contains(@href,'sankalan-paul-chowdhury-778a4a13a')]");
    private By jobs=By.xpath("//a[contains(@href,'.com/jobs')]");
    private By jobTitle_searchBox=By.xpath("//input[contains(@id,'jobs-search-box')]");
    private By location_searchBox=By.xpath("(//input[@aria-label='City, state, or zip code'])[last()]");
    private By clearLocaion=By.xpath("//button[contains(@class,'clear-location-input')]");
    private By jobLocation_result=By.xpath("//ul[contains(@aria-label,'location searches')]//following::div");
    private By datePosted=By.xpath("//button[text()='Date posted']");
    private By past24hours_radioButton=By.xpath("(//span[text()='Past 24 hours']//preceding::input[@type='radio'])[last()]");
    private By showResults=By.xpath("(//button[@id='searchFilter_timePostedRange']//preceding::button)[last()]");


    //Constructor
    public LinkedIn_LoginPage(WebDriver driver)
    {
        this.driver=driver;
    }

    //Methods
    public void LogIn() throws InterruptedException {
        WebElement setSignIn=driver.findElement(signIn);
        setSignIn.click();

    }

    public void setCredentials() throws InterruptedException {
        WebElement setUname=driver.findElement(userName);
        setUname.sendKeys("paulsankalan@gmail.com");
        WebElement pwd=driver.findElement(password);
        pwd.sendKeys("****");
        WebElement signIn_Button=driver.findElement(signInButton);
        signIn_Button.click();
        Thread.sleep(8000);
    }

    public void verifySuccessfulLogin()
    {
        WebElement id=driver.findElement(id_verification);
        if(id.isDisplayed())
        {
            System.out.println("SUCCESSFUL LOGIN!!!");
        }
        else {
            System.out.println("UN-SUCCESSFUL LOGIN ATTEMPT!!!");
        }
    }

    public  void navigateToJobs() throws InterruptedException {
        WebElement Jobs=driver.findElement(jobs);
        Jobs.click();
        Thread.sleep(5000);
        driver.navigate().refresh();
        Thread.sleep(3000);
    }

    public void set_jobTitle_jobLocation() throws InterruptedException {
        WebElement JobTitle=driver.findElement(jobTitle_searchBox);
        WebElement JobLocation=driver.findElement(location_searchBox);
        WebElement clearJobLocation=driver.findElement(clearLocaion);
        WebElement locationSearchResult=driver.findElement(jobLocation_result);
        JobTitle.click();
        JobTitle.sendKeys("Software QA Engineer");
        Thread.sleep(2000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", JobLocation);
        JobLocation.click();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clearJobLocation);
        //clearJobLocation.click();
        JobLocation.sendKeys("Bengaluru");
        locationSearchResult.click();

    }

    public void filterDate() throws InterruptedException {
        WebElement date_posted=driver.findElement(datePosted);
        WebElement past24Hrs=driver.findElement(past24hours_radioButton);
        WebElement showResults_button=driver.findElement(showResults);

        date_posted.click();
        past24Hrs.click();
        showResults_button.click();
        Thread.sleep(2000);
    }


    public void deleteExistingExcelFile() {
        String filePath = "C://GitHub//IRCTC_Automation//JobData.xlsx";

        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Existing Excel file deleted: " + filePath);
            } else {
                System.out.println("Failed to delete the file: " + filePath);
            }
        } else {
            System.out.println("No existing Excel file found. Proceeding...");
        }
    }

    public int getJobTitles(Sheet sheet, int startRow) throws IOException {
        deleteExistingExcelFile();
        List<WebElement> jobTitles = driver.findElements(JobTitle);
        List<WebElement> companyNames = driver.findElements(comapny);

        int rowCount = startRow;
        int total = Math.min(jobTitles.size(), companyNames.size());
        for (int i = 0; i < total; i++)
        {
            String title = jobTitles.get(i).getText();
            String company = companyNames.get(i).getText();

            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(title);
            row.createCell(1).setCellValue(company);
        }

        return rowCount;

    }

    public void extractFromThreePages() throws Exception
    {
        String filePath = "C://GitHub//IRCTC_Automation//JobData.xlsx";
        Workbook workbook;
        Sheet sheet;
        int startRow;
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);
            startRow = sheet.getLastRowNum() + 1;
            fis.close();
        } else {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("JobData");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Job Role Name");
            header.createCell(1).setCellValue("Company Name");
            startRow = 1;
        }

        for (int page = 1; page <= 4; page++) {
            Thread.sleep(3000);

            WebElement leftScroll=driver.findElement(leftPane);
            WebElement target = driver.findElement(targetElement);
            //JavascriptExecutor js = (JavascriptExecutor) driver;
            //js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight;", leftScroll);
            /*
            for(int i=0;i<=4;i++)
            {
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", target);
                Thread.sleep(2000);
            }

             */


            if (page < 4) {
                String nextPageXpath = "//button[@aria-label='Page " + (page + 1) + "']";
                WebElement nextButton = driver.findElement(By.xpath(nextPageXpath));
                if (nextButton.isDisplayed() && nextButton.isEnabled()) {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", nextButton);
                    Thread.sleep(3000);
                    startRow = getJobTitles(sheet, startRow);
                    nextButton.click();
                    Thread.sleep(3000);
                    //startRow = getJobTitles(sheet, startRow);
                }
            }

        }
        FileOutputStream fileOut = new FileOutputStream(filePath);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();

    }

    public void scrollLeftPane() throws InterruptedException {
        WebElement target=driver.findElement(targetElement);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", target);
        Thread.sleep(3000);


    }

}
