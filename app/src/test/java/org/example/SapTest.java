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
    private WebDriver webDriver;
    private WebDriver remoteWebDriver;
    private NetworkListener networkListener;
    private NetworkListener remoteNetworkListener;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        String server = "10.237.117.237";
        // String server = "localhost";
        // String server = "selenium-runner.tcc.c.eu-de-2.cloud.sap";
        String credentials = "btpuser:xyz1234@";
        remoteWebDriver = new RemoteWebDriver(new URL("http://" + credentials + server + ":4444"), options);
        remoteNetworkListener = new NetworkListener(remoteWebDriver, "remote_har.har");
        
        webDriver = new ChromeDriver(options);
        networkListener = new NetworkListener(webDriver, "har.har");
    }

    @Test
    public void testSapHomePage() {
        networkListener.start();
        webDriver.get("https://www.sap.com");
        String title = webDriver.getTitle();
        Assert.assertTrue(title.contains("SAP"), "Title does not contain 'SAP': " + title);
    }

    @Test
    public void testSapHomePageRemote() {
        remoteNetworkListener.start();
        remoteWebDriver.get("https://www.sap.com");
        String title = remoteWebDriver.getTitle();
        Assert.assertTrue(title.contains("SAP"), "Title does not contain 'SAP': " + title);
    }

    @AfterClass
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
        if (networkListener != null) {
            networkListener.createHarFile();
        }
    }
}
