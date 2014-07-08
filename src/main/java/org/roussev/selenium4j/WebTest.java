package org.roussev.selenium4j;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class WebTest {

    private static final Logger log = Logger.getLogger(WebTest.class);

    private WebDriverBackedSelenium selenium;
    private String driver = null;

    @Before
    public void before() {
        if (System.getProperty("smoke") == null) {
            log.info("skipping test, smoke not set");
        }
        org.junit.Assume.assumeTrue(null != System.getProperty("smoke"));
    }

    protected void startSelenium(String pDriver, String pWebsite) {
        WebDriver webDriver = null;
        driver = pDriver;
        if (FirefoxDriver.class.getSimpleName().equals(pDriver)) {
            webDriver = new FirefoxDriver();

        } else if (ChromeDriver.class.getSimpleName().equals(pDriver)) {
            webDriver = new ChromeDriver();

        } else if (HtmlUnitDriver.class.getSimpleName().equals(pDriver)) {
            webDriver = new HtmlUnitDriver();

        } else if (InternetExplorerDriver.class.getSimpleName().equals(pDriver)) {
            webDriver = new InternetExplorerDriver();

        } else {
            throw new UnsupportedOperationException("Driver '" + pDriver
                    + "' is not supported.");
        }

        selenium = new WebDriverBackedSelenium(webDriver, pWebsite);
    }

    public WebDriverBackedSelenium session() {
        return selenium;
    }

    public void verifyTrue(boolean b) {
        if (!b) {
            throw new IllegalStateException("test failed");
        }
    }

    public void verifyNotTrue(boolean b) {
        if (b) {
            throw new IllegalStateException("test failed");
        }
    }

    public void verifyEquals(Object a, Object b) {
        if(a!=b && (a==null || b==null || !a.equals(b))) {
            throw new IllegalStateException(String.format("verifyEquals Failed: \"%s\" does not equal \"%s\"", a, b));
        }
    }

    @After
    public void tearDown() {
        try {
            List<String> browserExes = getBrowserExe();
            if (browserExes != null) {
                for (String browserExe : browserExes) {
                    if (SystemUtils.IS_OS_WINDOWS){
                        Runtime.getRuntime().exec(new String[]{"c:\\windows\\system32\\cmd.exe", "/c" ,"C:\\windows\\System32\\pskill.exe", browserExe});
                    } else {
                        Runtime.getRuntime().exec("killall " + browserExe);
                    }
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private List<String> getBrowserExe() {
        if (FirefoxDriver.class.getSimpleName().equals(driver)) {
            return Arrays.asList("firefox.exe", "firefox", "firefox-bin");
        } else if (ChromeDriver.class.getSimpleName().equals(driver)) {
            return Arrays.asList("chromedriver", "chrome.exe");
        } else if (InternetExplorerDriver.class.getSimpleName().equals(driver)) {
            return Arrays.asList("IEDriverServer.exe", "iexplore.exe");
        }
        return null;
    }

}
