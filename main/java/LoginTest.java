import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.Map;

public class LoginTest {
    WebDriver auto;
    @BeforeMethod
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\TAKO\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();

        // إطفاء تحذيرات Chrome الخاصة بكلمات السر
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.password_manager_leak_detection", false);
        prefs.put("credentials_enable_service", false);

        options.setExperimentalOption("prefs", prefs);
        auto = new ChromeDriver(options);
        auto.manage().window().maximize();
        auto.get("https://www.saucedemo.com/");
    }
    @AfterMethod
    public void tearDown() {
        if (auto != null) {
            auto.quit();
        }
    }
    @Test
    public void validLogin() {
        auto.findElement(By.id("user-name")).sendKeys("standard_user");
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();
        Assert.assertTrue(auto.getCurrentUrl().contains("inventory"));
    }
    @Test
    public void invalidLoginUser() {
        auto.findElement(By.id("user-name")).sendKeys("invalid_user");
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();
        Assert.assertTrue(!auto.getCurrentUrl().contains("inventory"));
    }
    @Test
    public void invalidLoginPassword() {
        auto.findElement(By.id("user-name")).sendKeys("standard_user");
        auto.findElement(By.id("password")).sendKeys("invalid_password");
        auto.findElement(By.id("login-button")).click();
        Assert.assertTrue(!auto.getCurrentUrl().contains("inventory"));
    }
    @Test
    public void blankLogin() {
        auto.findElement(By.id("login-button")).click();
        Assert.assertTrue(!auto.getCurrentUrl().contains("inventory"));
    }
    @Test
    public void blankUserNameLogin() {
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();
        Assert.assertTrue(!auto.getCurrentUrl().contains("inventory"));
    }
    @Test
    public void blankPasswordLogin() {
        auto.findElement(By.id("user-name")).sendKeys("standard_user");
        auto.findElement(By.id("login-button")).click();
        Assert.assertTrue(!auto.getCurrentUrl().contains("inventory"));
    }
    @Test
    public void lockedUserLogin(){
        auto.findElement(By.id("user-name")).sendKeys("standard_user");
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();
        Assert.assertTrue(auto.getCurrentUrl().contains("inventory"));
    }




}