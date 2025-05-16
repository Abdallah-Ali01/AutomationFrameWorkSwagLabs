import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.*;
import org.testng.annotations.*;
import java.util.HashMap;
import java.util.Map;

public class ProductsTest {

    WebDriver auto;

    @BeforeMethod
    public void setup()  {
        ChromeOptions options = new ChromeOptions();

        // إطفاء تحذيرات Chrome الخاصة بكلمات السر
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.password_manager_leak_detection", false);
        prefs.put("credentials_enable_service", false);

        options.setExperimentalOption("prefs", prefs);
        auto = new ChromeDriver(options);
        auto.manage().window().maximize();
        auto.get("https://www.saucedemo.com");
    }

    @AfterMethod
    public void teardown() {
        if (auto != null) {
            auto.quit();

        }
    }
    public void Login(String username){
        auto.findElement(By.id("user-name")).sendKeys(username);
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();
    }

    @Test(priority = 1)
    public void AddToCart() {
        Login("standard_user");
        auto.findElement(By.name("add-to-cart-sauce-labs-backpack")).click();
    }

    @Test(priority = 2)
    public void remove() {
        Login("standard_user");
        auto.findElement(By.name("add-to-cart-sauce-labs-backpack")).click();
        auto.findElement(By.name("remove-sauce-labs-backpack")).click();
    }

    @Test(priority = 3)
    public void Add2ProductsToCart() {
        Login("standard_user");
        auto.findElement(By.name("add-to-cart-sauce-labs-backpack")).click();
        auto.findElement(By.name("add-to-cart-sauce-labs-bike-light")).click();
    }

    @Test(priority =4)
    public void chooseFromTheFilter() {
        Login("standard_user");
        WebElement dropdown = auto.findElement(By.className("product_sort_container"));
        Select select = new Select(dropdown);
        select.selectByValue("hilo"); //high to low filter
    }

    @Test(priority =5)
    public void testLogout() throws InterruptedException {
        Login("standard_user");
        auto.findElement(By.id("react-burger-menu-btn")).click();
        Thread.sleep(1500);
        auto.findElement(By.id("logout_sidebar_link")).click();
        Thread.sleep(1000);
        String currentUrl = auto.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("saucedemo.com"), "Logout failed - not redirected properly.");
    }
    @Test(priority = 0)
    public void removeErrorUser() {
        Login("error_user");
        auto.findElement(By.name("add-to-cart-sauce-labs-backpack")).click();
        auto.findElement(By.name("remove-sauce-labs-backpack")).click();
        WebElement delete =auto.findElement(By.name("remove-sauce-labs-backpack"));
        Assert.assertFalse(delete.isDisplayed(),"Can't Remove The Product");
    }


}