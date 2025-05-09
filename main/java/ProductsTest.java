
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class ProductsTest {

    WebDriver auto;

    @BeforeMethod
    public void setup() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();

        // إطفاء تحذيرات Chrome الخاصة بكلمات السر
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.password_manager_leak_detection", false);
        prefs.put("credentials_enable_service", false);

        options.setExperimentalOption("prefs", prefs);


        System.setProperty("webdriver.chrome.driver", "C:\\Users\\TAKO\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        auto = new ChromeDriver(options);

        auto.manage().window().maximize();
        auto.get("https://www.saucedemo.com");

        auto.findElement(By.id("user-name")).sendKeys("standard_user");
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();
        // انتظر تحميل الصفحة يدويًا
        Thread.sleep(2000);
    }
    @AfterMethod
    public void teardown(){
        if (auto != null){
            auto.quit();

        }
    }
    public void handleAlertIfPresent() {
        try {
            Alert alert = auto.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException ignored){}
    }

    @Test
    public void AddToCart() {
        auto.findElement(By.name("add-to-cart-sauce-labs-backpack")).click();
    }

    @Test
    public void remove(){
        auto.findElement(By.name("add-to-cart-sauce-labs-backpack")).click();
        auto.findElement(By.name("remove-sauce-labs-backpack")).click();
    }

    @Test
    public void Add2ProductsToCart(){
        auto.findElement(By.name("add-to-cart-sauce-labs-backpack")).click();
        auto.findElement(By.name("add-to-cart-sauce-labs-bike-light")).click();
    }

    @Test
    public void chooseFromTheFilter() {
        WebElement dropdown = auto .findElement(By.className("product_sort_container"));
        Select select = new Select(dropdown);
        select.selectByValue("hilo");
    }
    @Test
    public void testLogout() throws InterruptedException {
        auto.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(1500); // استنى المينيو تفتح
        auto.findElement(By.id("logout_sidebar_link")).click();
        Thread.sleep(1000); // استنى الصفحة ترجع للوجين

        String currentUrl = auto.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("saucedemo.com"), "Logout failed - not redirected properly.");
    }



}