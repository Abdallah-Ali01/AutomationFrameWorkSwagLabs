
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CartTest{
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
    public void close(){
        if (auto!= null)
            auto.quit();}

    public void login() {
        auto.findElement(By.id("user-name")).sendKeys("standard_user");
        auto.findElement(By.id("password")).sendKeys("secret_sauce");
        handleAlertIfPresent();
        auto.findElement(By.id("login-button")).click();
        handleAlertIfPresent();
    }

    public void checkAddtocart() {
        handleAlertIfPresent();
        auto.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        auto.findElement(By.className("shopping_cart_container")).click();
    }
    private void handleAlertIfPresent() {
        try {
            Alert alert = auto.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException ignored){}
    }
    public void addAllItemsToCart()  {
        List<WebElement> addButtons = auto.findElements(By.cssSelector("button[id^='add-to-cart']"));
        for (WebElement button : addButtons) {
            button.click();}}
    public void removeAllItemsFromCart()  {
        List<WebElement> removeButtons = auto.findElements(By.cssSelector("button[id^='remove']"));
        for (WebElement button : removeButtons) {
            button.click();}}
    @Test
    public void testAddAndRemoveAllItems() {
        login();
        addAllItemsToCart();
        auto.findElement(By.className("shopping_cart_link")).click();
        removeAllItemsFromCart();
        int itemsCount = auto.findElements(By.className("cart_item")).size();
        Assert.assertEquals(itemsCount, 0, "Not all items were removed from the cart");
    }

    @Test(priority = 2)
    public void checkCheckoutButton() {
        login(); checkAddtocart();
        auto.findElement(By.id("checkout")).click();
        String currentURL = auto.getCurrentUrl();
        Assert.assertEquals(currentURL, "https://www.saucedemo.com/checkout-step-one.html", "Checkout did not redirect properly");
    }
    @Test(priority = 1)
    public void checkContinueShoppingButton() {
        login(); checkAddtocart();
        auto.findElement(By.id("continue-shopping")).click();
        String currentURL = auto.getCurrentUrl();
        Assert.assertEquals(currentURL, "https://www.saucedemo.com/inventory.html", "Continue shopping button failed");
    }
    @Test(priority = 3)
    public void checkItemInCart(){
        login(); checkAddtocart();
        WebElement item = auto.findElement(By.className("cart_item"));
        Assert.assertTrue(item.isDisplayed(), "Item not found in cart");
    }
    @Test(priority = 4)
    public void checkRemoveItemFromCart(){
        login();
        checkAddtocart();
        auto.findElement(By.id("remove-sauce-labs-backpack")).click();
        int itemsCount = auto.findElements(By.className("cart_item")).size();
        Assert.assertEquals(itemsCount, 0, "Item was not removed from cart");
    }

    @Test(priority = 5)
    public void cartPersistsAfterRefresh() {
        login(); checkAddtocart();
        auto.navigate().refresh();
        int itemsAfterRefresh = auto.findElements(By.className("cart_item")).size();
        Assert.assertEquals(itemsAfterRefresh, 1, "Cart did not persist after page refresh");
    }
    @Test(priority = 6)
    public void cartDisplaysCorrectItemDetails() {
        login(); checkAddtocart();
        String expectedName  = "Sauce Labs Backpack";
        String expectedPrice = "$29.99";

        WebElement itemName  = auto.findElement(By.className("inventory_item_name"));
        WebElement itemPrice = auto.findElement(By.className("inventory_item_price"));
        Assert.assertEquals(itemName.getText(), expectedName,  "Item name in cart is incorrect");
        Assert.assertEquals(itemPrice.getText(), expectedPrice, "Item price in cart is incorrect");
    }

    //Status:Fail
    @Test(priority = 7)
    public void checkEmptyCartBehavior() {
        login();checkAddtocart();
        auto.findElement(By.className("shopping_cart_link")).click(); // Open cart with no items
        int itemsCount = auto.findElements(By.className("cart_item")).size();
        Assert.assertEquals(itemsCount, 0, "Cart is not empty when it should be");}
    @Test(priority = 8)
    public void checkoutDisabledWhenCartEmpty()  {
        login();
        auto.findElement(By.className("shopping_cart_link")).click();
        WebElement checkoutBtn = auto.findElement(By.id("checkout"));
        Assert.assertFalse(checkoutBtn.isEnabled(), "Checkout button should be disabled when cart is empty");
}
}
