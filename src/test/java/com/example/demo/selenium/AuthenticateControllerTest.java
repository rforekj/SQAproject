package com.example.demo.selenium;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
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

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticateControllerTest {

    @Autowired
    UserRepository userRepository;

    private WebDriver driver;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", UrlConfig.driverUrl);
        driver = new ChromeDriver();
        driver.get(UrlConfig.baseUrl);
    }

    @Test
    public void testLoginSuccess() throws IOException {
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("hieu@gmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@type='submit']")).submit();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        boolean isLogged = driver.findElement(By.xpath("//*[text()='hieu']")).isDisplayed();
        Assert.assertEquals(true, isLogged);
        takeScreenshot(UrlConfig.imageUrl + "testLoginSuccess.jpg");
    }

    @Test
    public void testLoginWithWrongEmail() throws IOException {
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("hieucdas@gmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@type='submit']")).submit();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Invalid email or password']")));
        takeScreenshot(UrlConfig.imageUrl + "testLoginWithWrongEmail.jpg");
    }

    @Test
    public void testLoginWithWrongPassword() throws IOException {
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("hieu@gmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("x123456");
        driver.findElement(By.xpath("//button[@type='submit']")).submit();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Invalid email or password']")));
        takeScreenshot(UrlConfig.imageUrl + "testLoginWithWrongPassword.jpg");
    }

    @Test
    public void addUserSuccess() throws IOException {
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("hieu@gmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@type='submit']")).submit();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//button/span[text()='Quản lý nhân viên']")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//button/span[text()='Thêm nhân viên']")).click();
        driver.findElement(By.xpath("//input[@id='name']")).sendKeys("hieu");
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("hieu200@gmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//input[@id='confirmPassword']")).sendKeys("123456");
        driver.findElement(By.xpath("//input[@id='address']")).sendKeys("Nam Dinh");
        driver.findElement(By.xpath("//input[@id='dob']")).sendKeys("1999-07-12");
        driver.findElement(By.xpath("//input[@id='dob']")).sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//button/span[text()='Create']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Tạo nhân viên thành công']")));
        takeScreenshot(UrlConfig.imageUrl + "addUserSuccess.jpg");
        //Role back
        userRepository.deleteByEmail("hieu200@gmail.com");
    }

    @Test
    public void addUserDuplicateEmail() throws IOException {
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("hieu@gmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@type='submit']")).submit();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//button/span[text()='Quản lý nhân viên']")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//button/span[text()='Thêm nhân viên']")).click();
        driver.findElement(By.xpath("//input[@id='name']")).sendKeys("hieu");
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("hieu11@gmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//input[@id='confirmPassword']")).sendKeys("123456");
        driver.findElement(By.xpath("//input[@id='address']")).sendKeys("Nam Dinh");
        driver.findElement(By.xpath("//input[@id='dob']")).sendKeys("1999-07-12");
        driver.findElement(By.xpath("//input[@id='dob']")).sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//button/span[text()='Create']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='There is already an account registered with that email']")));
        takeScreenshot(UrlConfig.imageUrl + "addUserDuplicateEmail.jpg");
    }

    @Test
    public void deleteUserWithRoleAdmin() throws IOException {
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("hieu@gmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@type='submit']")).submit();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//button/span[text()='Quản lý nhân viên']")).click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//table/tbody/tr[2]/td[7]/div/img")).click();
        WebElement col = driver.findElement(By.xpath("//table/tbody/tr[2]/td[1]"));
        Long idCol = Long.parseLong(col.getText());
        User user = userRepository.findById(idCol).get();
        driver.findElement(By.xpath("//button/span[text()='Đồng ý']")).click();
        takeScreenshot(UrlConfig.imageUrl + "deleteUser.jpg");
        //roll back
        userRepository.save(user);
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
