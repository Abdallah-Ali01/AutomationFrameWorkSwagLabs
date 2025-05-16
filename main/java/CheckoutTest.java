import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.*;
import org.testng.annotations.*;
import java.util.HashMap;
import java.util.Map;


public class CheckoutTest {

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

    public void login(String username) {
        auto.findElement(By.id("user-name")).sendKeys(username);
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        auto.findElement(By.id("login-button")).click();
    }

    public void addItemToCart() {
        auto.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        auto.findElement(By.className("shopping_cart_link")).click();
    }

    public void fillCheckoutForm(String firstName, String lastName, String postalCode) {
        auto.findElement(By.id("checkout")).click();
        auto.findElement(By.id("first-name")).sendKeys(firstName);
        auto.findElement(By.id("last-name")).sendKeys(lastName);
        auto.findElement(By.id("postal-code")).sendKeys(postalCode);
        auto.findElement(By.id("continue")).click();
    }

    @Test(priority = 0)
    public void testValidCheckout() throws InterruptedException {
        login("standard_user");
        addItemToCart();
        fillCheckoutForm("Abdallah", "Ali", "12345");
        Thread.sleep(500);
        auto.findElement(By.id("finish")).click();

        WebElement confirmation = auto.findElement(By.className("complete-header"));
        Assert.assertEquals(confirmation.getText(), "Thank you for your order!", "Order confirmation failed");
    }

    @Test(priority = 4)
    public void testCheckoutWithEmptyFirstName() {
        login("standard_user");
        addItemToCart();
        fillCheckoutForm("", "Ali", "12345");

        WebElement titleElement = auto.findElement(By.className("title"));
        Assert.assertEquals(titleElement.getText(), "Checkout: Your Information","Shouldn't redirect to Checkout : Overview");
    }

    @Test(priority = 5)
    public void testCheckoutWithSpacesInFields() {
        login("standard_user");
        addItemToCart();
        fillCheckoutForm(" ", " ", " ");

        WebElement titleElement = auto.findElement(By.className("title"));
        Assert.assertEquals(titleElement.getText(), "Checkout: Your Information","Accept Spaces Error: Shouldn't redirect to Checkout : Overview");
    }

    @Test(priority = 6)
    public void testCheckoutWithNumericName() {
        login("standard_user");
        addItemToCart();
        fillCheckoutForm("123", "456", "78910");

        WebElement titleElement = auto.findElement(By.className("title"));
        Assert.assertEquals(titleElement.getText(), "Checkout: Your Information","Shouldn't redirect to Checkout : Overview");
    }

    @Test(priority = 7)
    public void testCheckoutWithZeroPostalCode() {
        login("standard_user");
        addItemToCart();
        fillCheckoutForm("Abdallah", "Ali", "0");

        WebElement titleElement = auto.findElement(By.className("title"));
        Assert.assertEquals(titleElement.getText(), "Checkout: Your Information","Shouldn't redirect to Checkout : Overview");
    }

    @Test(priority = 8)
    public void testCheckoutWithNonNumericPostalCode() {
        login("standard_user");
        addItemToCart();
        fillCheckoutForm("Abdallah", "Ali", "abcde");

        WebElement titleElement = auto.findElement(By.className("title"));
        Assert.assertEquals(titleElement.getText(), "Checkout: Your Information","Shouldn't redirect to Checkout : Overview");
    }

    @Test (priority = 3)
    public void testContinueWithoutCartItems() {
        login("standard_user");
        auto.findElement(By.className("shopping_cart_link")).click();
        Assert.assertTrue(auto.findElements(By.id("checkout")).isEmpty(), "Checkout button should not be present if cart is empty");
    }

    @Test(priority = 1)
    public void testCheckoutProcessProblemUser() {
        login("problem_user");
        addItemToCart();


        try {
            fillCheckoutForm("Abdallah", "Ali", "123");
            auto.findElement(By.id("finish")).click();
            WebElement confirmation = auto.findElement(By.className("complete-header"));
            Assert.assertEquals(confirmation.getText(), "Thank you for your order!", "Order confirmation failed for problem_user");
        } catch (Exception e) {
            Assert.fail("Checkout process failed for problem_user");
        }
    }

    @Test(priority = 2)
    public void testCheckoutProcessErrorUser() {
        login("error_user");
        addItemToCart();
        try {
            fillCheckoutForm("Abdallah", "Ali", "123");
            auto.findElement(By.id("finish")).click();
            WebElement confirmation = auto.findElement(By.className("complete-header"));
            Assert.assertEquals(confirmation.getText(), "Thank you for your order!", "Order confirmation failed for problem_user");
        } catch (Exception e) {
            Assert.fail("Checkout process failed for problem_user: " + e.getMessage());
        }
    }

}