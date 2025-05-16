import org.testng.TestNG;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException
    {
        TestNG testng = new TestNG();
        testng.setTestClasses(new Class[]
        {
            LoginTest.class,
            ProductsTest.class,
            CartTest.class,
            CheckoutTest.class,
            EndToEndTest.class
        }
        );
        testng.run();

        String reportPath = "test-output\\Command line suite\\Command line test.html";
        File reportFile = new File(reportPath);
        Desktop.getDesktop().open(reportFile);


    }
}