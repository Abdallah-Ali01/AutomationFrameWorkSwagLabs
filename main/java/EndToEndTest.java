import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;

public class EndToEndTest {

    WebDriver auto;

    @BeforeMethod
    public void setUp()  {
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

    @Test(priority = 0)
    public void standardE2EPurchase()  {

        auto.findElement(By.id("user-name")).sendKeys("standard_user");
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();


        auto.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        auto.findElement(By.className("shopping_cart_link")).click();

        auto.findElement(By.id("checkout")).click();

        auto.findElement(By.id("first-name")).sendKeys("Ahmed");
        auto.findElement(By.id("last-name")).sendKeys("Hassan");
        auto.findElement(By.id("postal-code")).sendKeys("12345");

        auto.findElement(By.id("continue")).click();

        auto.findElement(By.id("finish")).click();

        WebElement thankYouMsg = auto.findElement(By.className("complete-header"));
        Assert.assertEquals(thankYouMsg.getText(), "Thank you for your order!", "Order completion message not found.");
}
    @Test(priority = 1)
    public void locked_outE2EPurchase()  {

        auto.findElement(By.id("user-name")).sendKeys("locked_out_user");
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();


        auto.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        auto.findElement(By.className("shopping_cart_link")).click();

        auto.findElement(By.id("checkout")).click();

        auto.findElement(By.id("first-name")).sendKeys("Ahmed");
        auto.findElement(By.id("last-name")).sendKeys("Hassan");
        auto.findElement(By.id("postal-code")).sendKeys("12345");

        auto.findElement(By.id("continue")).click();

        auto.findElement(By.id("finish")).click();

        WebElement thankYouMsg = auto.findElement(By.className("complete-header"));
        Assert.assertEquals(thankYouMsg.getText(), "Thank you for your order!", "Order completion message not found.");
    }
    @Test(priority = 2)
    public void problem_userE2EPurchase()  {

        auto.findElement(By.id("user-name")).sendKeys("problem_user");
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();


        auto.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        auto.findElement(By.className("shopping_cart_link")).click();

        auto.findElement(By.id("checkout")).click();

        auto.findElement(By.id("first-name")).sendKeys("Ahmed");
        auto.findElement(By.id("last-name")).sendKeys("Hassan");
        auto.findElement(By.id("postal-code")).sendKeys("12345");

        auto.findElement(By.id("continue")).click();

        auto.findElement(By.id("finish")).click();

        WebElement thankYouMsg = auto.findElement(By.className("complete-header"));
        Assert.assertEquals(thankYouMsg.getText(), "Thank you for your order!", "Order completion message not found.");
    }
    @Test(priority = 3)
    public void performanceE2EPurchase()  {

        auto.findElement(By.id("user-name")).sendKeys("performance_glitch_user");
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();


        auto.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        auto.findElement(By.className("shopping_cart_link")).click();

        auto.findElement(By.id("checkout")).click();

        auto.findElement(By.id("first-name")).sendKeys("Ahmed");
        auto.findElement(By.id("last-name")).sendKeys("Hassan");
        auto.findElement(By.id("postal-code")).sendKeys("12345");

        auto.findElement(By.id("continue")).click();

        auto.findElement(By.id("finish")).click();

        WebElement thankYouMsg = auto.findElement(By.className("complete-header"));
        Assert.assertEquals(thankYouMsg.getText(), "Thank you for your order!", "Order completion message not found.");
    }
    @Test(priority = 4)
    public void errorE2EPurchase()  {

        auto.findElement(By.id("user-name")).sendKeys("error_user");
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();


        auto.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        auto.findElement(By.className("shopping_cart_link")).click();

        auto.findElement(By.id("checkout")).click();

        auto.findElement(By.id("first-name")).sendKeys("Ahmed");
        auto.findElement(By.id("last-name")).sendKeys("Hassan");
        auto.findElement(By.id("postal-code")).sendKeys("12345");

        auto.findElement(By.id("continue")).click();

        auto.findElement(By.id("finish")).click();

        WebElement thankYouMsg = auto.findElement(By.className("complete-header"));
        Assert.assertEquals(thankYouMsg.getText(), "Thank you for your order!", "Order completion message not found.");
    }
    @Test(priority = 5)
    public void visualE2EPurchase()  {

        auto.findElement(By.id("user-name")).sendKeys("visual_user");
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();


        auto.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        auto.findElement(By.className("shopping_cart_link")).click();

        auto.findElement(By.id("checkout")).click();

        auto.findElement(By.id("first-name")).sendKeys("Ahmed");
        auto.findElement(By.id("last-name")).sendKeys("Hassan");
        auto.findElement(By.id("postal-code")).sendKeys("12345");

        auto.findElement(By.id("continue")).click();

        auto.findElement(By.id("finish")).click();

        WebElement thankYouMsg = auto.findElement(By.className("complete-header"));
        Assert.assertEquals(thankYouMsg.getText(), "Thank you for your order!", "Order completion message not found.");
    }

}