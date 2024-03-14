package org.example;

import com.blibli.oss.qa.util.services.NetworkListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class SapTest {
    private WebDriver driver;
    private NetworkListener networkListener;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        String server = "10.237.117.237";
        // String server = "selenium-runner.tcc.c.eu-de-2.cloud.sap"
        //driver = new RemoteWebDriver(new URL("http://btpuser:xyz1234@" + server + ":4444"), options);
        driver = new ChromeDriver(options);
        networkListener = new NetworkListener(driver, "har.har");
    }

    @Test
    public void testSapHomePage() {
        networkListener.start();
        driver.get("https://www.sap.com");
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("SAP"), "Title does not contain 'SAP': " + title);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            networkListener.createHarFile();
        }
    }
}
