package com.example.demo.selenium;

import com.example.demo.model.SocialInsuranceType;
import com.example.demo.model.User;
import com.example.demo.repository.SocialInsuranceTypeRepository;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigControllerTest {

    @Autowired
    SocialInsuranceTypeRepository socialInsuranceTypeRepository;
    private WebDriver driver;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", UrlConfig.driverUrl);
        driver = new ChromeDriver();
        driver.get(UrlConfig.baseUrl);
    }

    @Test
    public void configSuccess() throws IOException {
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("hieu@gmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@type='submit']")).submit();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//button/span[text()='Thay đổi cấu hình']")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//table/tbody/tr[2]/td[4]/div/img")).click();
        WebElement col = driver.findElement(By.xpath("//table/tbody/tr[2]/td[1]"));
        int idCol = Integer.parseInt(col.getText());
        SocialInsuranceType socialInsuranceType = socialInsuranceTypeRepository.findById(idCol).get();
        driver.findElement(By.xpath("//input[@class='ant-input-number-input']")).sendKeys(Keys.CONTROL + "a");
        driver.findElement(By.xpath("//input[@class='ant-input-number-input']")).sendKeys(Keys.DELETE);
        driver.findElement(By.xpath("//input[@class='ant-input-number-input']")).sendKeys("2.3");
        driver.findElement(By.xpath("//button/span[text()='Lưu']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Sửa bảo hiểm thành công']")));
        takeScreenshot(UrlConfig.imageUrl + "configSuccess.jpg");
        //roll back
        socialInsuranceTypeRepository.save(socialInsuranceType);
    }

    @Test
    public void configFail() throws IOException {
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("hieu@gmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@type='submit']")).submit();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//button/span[text()='Thay đổi cấu hình']")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//table/tbody/tr[2]/td[4]/div/img")).click();
        driver.findElement(By.xpath("//input[@class='ant-input-number-input']")).sendKeys(Keys.CONTROL + "a");
        driver.findElement(By.xpath("//input[@class='ant-input-number-input']")).sendKeys(Keys.DELETE);
        driver.findElement(By.xpath("//input[@class='ant-input-number-input']")).sendKeys("20");
        driver.findElement(By.xpath("//button/span[text()='Lưu']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='price must greater than 0 and lower than 10']")));
        takeScreenshot(UrlConfig.imageUrl + "configFail.jpg");
    }

    @After
    public void closeBrowser() {
        driver.close();
    }

    public void takeScreenshot(String pathname) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File(pathname));
    }

}
