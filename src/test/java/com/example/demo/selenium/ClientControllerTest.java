package com.example.demo.selenium;

import com.example.demo.repository.ClientRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientControllerTest {

    @Autowired
    ClientRepository clientRepository;
    private WebDriver driver;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", UrlConfig.driverUrl);
        driver = new ChromeDriver();
        driver.get(UrlConfig.baseUrl);
    }

    @Test
    public void getAllClientLatePaid() throws IOException {
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("hieu@gmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@type='submit']")).submit();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//button/span[text()='Theo dõi danh sách']")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement selectStatus = driver.findElement(By.xpath("//span[@class='ant-select-selection-search']/input[@id='select-status']"));
        selectStatus.sendKeys(Keys.ENTER);
        selectStatus.sendKeys(Keys.ARROW_DOWN);
        selectStatus.sendKeys(Keys.ARROW_DOWN);
        selectStatus.sendKeys(Keys.ARROW_DOWN);
        selectStatus.sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//table/tbody/tr[1]/td[10]/div/img")).click();
        for (int j = 1; j <= 6; j++) {
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            String status = driver.findElement(By.xpath("//div[@id='table_detail']/div/div/table/tbody/tr[" + j + "]/td[7]")).getText();
            if (status.equalsIgnoreCase("Nộp muộn")) {
                Assert.assertEquals(status, "Nộp muộn");
                takeScreenshot(UrlConfig.imageUrl + "getClientLatePaid.jpg");
                driver.findElement(By.xpath("//button/span[text()='OK']")).click();
                break;
            }
        }
    }

    @Test
    public void getAllClientHaveNotPaid() throws IOException {
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("hieu@gmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@type='submit']")).submit();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//button/span[text()='Theo dõi danh sách']")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement selectStatus = driver.findElement(By.xpath("//span[@class='ant-select-selection-search']/input[@id='select-status']"));
        selectStatus.sendKeys(Keys.ENTER);
        selectStatus.sendKeys(Keys.ARROW_DOWN);
        selectStatus.sendKeys(Keys.ARROW_DOWN);
        selectStatus.sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//table/tbody/tr[1]/td[10]/div/img")).click();
        for (int j = 1; j <= 6; j++) {
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            String status = driver.findElement(By.xpath("//div[@id='table_detail']/div/div/table/tbody/tr[" + j + "]/td[7]")).getText();
            if (status.equalsIgnoreCase("Chưa nộp")) {
                Assert.assertEquals(status, "Chưa nộp");
                takeScreenshot(UrlConfig.imageUrl + "getClientHaveNotPaid.jpg");
                driver.findElement(By.xpath("//button/span[text()='OK']")).click();
                break;
            }
        }
    }
    @Test
    public void getAllClientHavePaid() throws IOException {
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("hieu@gmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@type='submit']")).submit();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//button/span[text()='Theo dõi danh sách']")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement selectStatus = driver.findElement(By.xpath("//span[@class='ant-select-selection-search']/input[@id='select-status']"));
        selectStatus.sendKeys(Keys.ENTER);
        selectStatus.sendKeys(Keys.ARROW_DOWN);
        selectStatus.sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//table/tbody/tr[1]/td[10]/div/img")).click();
        for (int j = 1; j <= 6; j++) {
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            String status = driver.findElement(By.xpath("//div[@id='table_detail']/div/div/table/tbody/tr[" + j + "]/td[7]")).getText();
            if (status.equalsIgnoreCase("Đã nộp")) {
                Assert.assertEquals(status, "Đã nộp");
                takeScreenshot(UrlConfig.imageUrl + "getClientHavePaid.jpg");
                driver.findElement(By.xpath("//button/span[text()='OK']")).click();
                break;
            }
        }
    }

    @Test
    public void getAllClientReceived() throws IOException {
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("hieu@gmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@type='submit']")).submit();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//button/span[text()='Theo dõi danh sách']")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement selectStatus = driver.findElement(By.xpath("//span[@class='ant-select-selection-search']/input[@id='select-status']"));
        selectStatus.sendKeys(Keys.ENTER);
        selectStatus.sendKeys(Keys.ARROW_DOWN);
        selectStatus.sendKeys(Keys.ARROW_DOWN);
        selectStatus.sendKeys(Keys.ARROW_DOWN);
        selectStatus.sendKeys(Keys.ARROW_DOWN);
        selectStatus.sendKeys(Keys.ENTER);
        driver.findElement(By.xpath("//table/tbody/tr[1]/td[10]/div/img")).click();
        for (int j = 1; j <= 6; j++) {
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            String status = driver.findElement(By.xpath("//div[@id='table_detail']/div/div/table/tbody/tr[" + j + "]/td[7]")).getText();
            if (status.equalsIgnoreCase("Đã nhận")) {
                Assert.assertEquals(status, "Đã nhận");
                takeScreenshot(UrlConfig.imageUrl + "getClientReceived.jpg");
                driver.findElement(By.xpath("//button/span[text()='OK']")).click();
                break;
            }
        }
    }

    @Test
    public void getAllClientByProvince() throws IOException {
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("hieu@gmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@type='submit']")).submit();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//button/span[text()='Theo dõi danh sách']")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement select = driver.findElement(By.xpath("//span[contains(@class, 'ant-select-selection-search')]/input"));
        select.sendKeys(Keys.ENTER);
        select.sendKeys(Keys.ARROW_DOWN);
        select.sendKeys(Keys.ENTER);
        Pageable paging = PageRequest.of(1, 10);
        Long sizeResult = clientRepository.findAllByAddress_Province("Hà Nội", paging).getTotalElements();
        for (int i = 1; i < sizeResult; i++) {
            String address = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[9]")).getText();
            Assert.assertEquals(true, address.contains("Hà Nội"));
        }
        takeScreenshot(UrlConfig.imageUrl + "getAllClientByProvince.jpg");
    }

    @Test
    public void getAllClientByDistrict() throws IOException {
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("hieu@gmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@type='submit']")).submit();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//button/span[text()='Theo dõi danh sách']")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement selectProvince = driver.findElement(By.xpath("//span[@class='ant-select-selection-search']/input[@id='select-province']"));
        selectProvince.sendKeys(Keys.ENTER);
        selectProvince.sendKeys(Keys.ARROW_DOWN);
        selectProvince.sendKeys(Keys.ENTER);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement selectDistrict = driver.findElement(By.xpath("//span[@class='ant-select-selection-search']/input[@id='select-district']"));
        selectDistrict.sendKeys(Keys.ENTER);
        selectDistrict.sendKeys(Keys.ARROW_DOWN);
        selectDistrict.sendKeys(Keys.ENTER);
        Pageable paging = PageRequest.of(1, 10);
        Long sizeResult = clientRepository.findAllByAddress_ProvinceAndAddress_District("Hà Nội", "Hai Bà Trưng", paging).getTotalElements();
        for (int i = 1; i < sizeResult; i++) {
            String address = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[9]")).getText();
            Assert.assertEquals(true, address.contains("Hà Nội"));
            Assert.assertEquals(true, address.contains("Hai Bà Trưng"));
        }
        takeScreenshot(UrlConfig.imageUrl + "getAllClientByDistrict.jpg");
    }

    @Test
    public void getAllClientByCommune() throws IOException {
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("hieu@gmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@type='submit']")).submit();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//button/span[text()='Theo dõi danh sách']")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement selectProvince = driver.findElement(By.xpath("//span[@class='ant-select-selection-search']/input[@id='select-province']"));
        selectProvince.sendKeys(Keys.ENTER);
        selectProvince.sendKeys(Keys.ARROW_DOWN);
        selectProvince.sendKeys(Keys.ENTER);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement selectDistrict = driver.findElement(By.xpath("//span[@class='ant-select-selection-search']/input[@id='select-district']"));
        selectDistrict.sendKeys(Keys.ENTER);
        selectDistrict.sendKeys(Keys.ARROW_DOWN);
        selectDistrict.sendKeys(Keys.ENTER);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement selectCommune = driver.findElement(By.xpath("//span[@class='ant-select-selection-search']/input[@id='select-commune']"));
        selectCommune.sendKeys(Keys.ENTER);
        selectCommune.sendKeys(Keys.ARROW_DOWN);
        selectCommune.sendKeys(Keys.ENTER);
        Pageable paging = PageRequest.of(1, 10);
        Long sizeResult = clientRepository.findAllByAddress_ProvinceAndAddress_DistrictAndAddress_Commune("Hà Nội", "Hai Bà Trưng", "Thanh Lương", paging).getTotalElements();
        for (int i = 1; i < sizeResult; i++) {
            String address = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[9]")).getText();
            Assert.assertEquals(true, address.contains("Hà Nội"));
            Assert.assertEquals(true, address.contains("Hai Bà Trưng"));
            Assert.assertEquals(true, address.contains("Thanh Lương"));
        }
        takeScreenshot(UrlConfig.imageUrl + "getAllClientByCommune.jpg");
    }

    @Test
    public void searchClientByIdentityNumber() throws IOException {
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("hieu@gmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@type='submit']")).submit();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//button/span[text()='Theo dõi danh sách']")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//span/input")).sendKeys("235034549900");
        driver.findElement(By.xpath("//button[@class='ant-btn ant-btn-icon-only ant-input-search-button']")).click();
        String identityNumber = driver.findElement(By.xpath("//table/tbody/tr[1]/td[4]")).getText();
        Assert.assertEquals("235034549900", identityNumber);
        takeScreenshot(UrlConfig.imageUrl + "searchClientByIdentityNumber.jpg");
    }

    @Test
    public void searchClientByInsuranceNumber() throws IOException {
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("hieu@gmail.com");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@type='submit']")).submit();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//button/span[text()='Theo dõi danh sách']")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//span/input")).sendKeys("10");
        driver.findElement(By.xpath("//button[@class='ant-btn ant-btn-icon-only ant-input-search-button']")).click();
        driver.findElement(By.xpath("//table/tbody/tr[1]/td[10]/div/img")).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='table_detail']")));
        String id = driver.findElement(By.xpath("//div[@id='table_detail']/div/div/table/tbody/tr[1]/td[1]")).getText();
        Assert.assertEquals("10", id);
        takeScreenshot(UrlConfig.imageUrl + "searchClientByInsuranceNumber.jpg");
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
