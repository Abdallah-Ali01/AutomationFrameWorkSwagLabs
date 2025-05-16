import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.*;
import org.testng.annotations.*;
import java.util.HashMap;
import java.util.Map;

public class LoginTest {
    WebDriver auto;
    @BeforeMethod
    public void setup() {
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
    @Test (priority = 0)
    public void validLogin() {
        auto.findElement(By.id("user-name")).sendKeys("standard_user");
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();
        Assert.assertTrue(auto.getCurrentUrl().contains("inventory"));
    }
    @Test(priority = 2)
    public void invalidLoginUser() {
        auto.findElement(By.id("user-name")).sendKeys("invalid_user");
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();
        Assert.assertFalse(auto.getCurrentUrl().contains("inventory"));
    }
    @Test(priority = 3)
    public void invalidLoginPassword() {
        auto.findElement(By.id("user-name")).sendKeys("standard_user");
        auto.findElement(By.id("password")).sendKeys("invalid_password");
        auto.findElement(By.id("login-button")).click();
        Assert.assertTrue(!auto.getCurrentUrl().contains("inventory"));
    }
    @Test(priority = 4)
    public void blankLogin() {
        auto.findElement(By.id("login-button")).click();
        Assert.assertTrue(!auto.getCurrentUrl().contains("inventory"));
    }
    @Test(priority = 5)
    public void blankUserNameLogin() {
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();
        Assert.assertTrue(!auto.getCurrentUrl().contains("inventory"));
    }
    @Test(priority = 6)
    public void blankPasswordLogin() {
        auto.findElement(By.id("user-name")).sendKeys("standard_user");
        auto.findElement(By.id("login-button")).click();
        Assert.assertTrue(!auto.getCurrentUrl().contains("inventory"));
    }
    @Test(priority = 1)
    public void lockedUserLogin(){
        auto.findElement(By.id("user-name")).sendKeys("locked_out_user");
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();
        Assert.assertTrue(auto.getCurrentUrl().contains("inventory"),"User Should Login");
    }




}