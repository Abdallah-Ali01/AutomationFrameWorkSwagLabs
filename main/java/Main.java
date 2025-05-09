import org.testng.TestNG;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.*;
import org.testng.annotations.*;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.time.Duration;
public class Main {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\TAKO\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG testng = new TestNG();
        testng.setTestClasses(new Class[] {
                LoginTest.class,
                ProductsTest.class,
                CartTest.class,
                CheckoutTest.class
        });
        testng.addListener(tla);
        testng.run();
        try {
            File reportFile = new File("C:\\Users\\TAKO\\Desktop\\New folder (4)\\test-output\\Command line suite\\Command line test.html");
            if (reportFile.exists()) {
                Desktop.getDesktop().browse(reportFile.toURI());
            } else {
                System.out.println("Report file not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}