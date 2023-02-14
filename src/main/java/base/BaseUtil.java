package base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class BaseUtil {

    private DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    protected ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    protected WebDriverWait wait;

    private String ACCESS_KEY = "";
    private String CLOUD_URL = "https://uscloud.experitest.com/wd/hub";

    public AppiumDriver getDriver() {
        return driver.get();
    }

    @BeforeMethod
    @Parameters({"platform"})
    public void setUp(String platform, @Optional Method method) throws MalformedURLException {

        desiredCapabilities.setCapability("accessKey", ACCESS_KEY);
        desiredCapabilities.setCapability("Jenkins_Build_Number", System.getenv("BUILD_NUMBER"));
        desiredCapabilities.setCapability("testName", method.getName() + " - " + platform);

        if (platform.equalsIgnoreCase("iOS")) {

            desiredCapabilities.setCapability("deviceQuery", "@os='ios'");
            desiredCapabilities.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank");
            desiredCapabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBank");
            driver.set(new IOSDriver(new URL(CLOUD_URL), desiredCapabilities));

        } else if (platform.equalsIgnoreCase("Android")) {

            desiredCapabilities.setCapability("deviceQuery", "@os='android'");
            desiredCapabilities.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank/.LoginActivity");
            desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
            desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
            driver.set(new AndroidDriver(new URL(CLOUD_URL), desiredCapabilities));

        }

        wait = new WebDriverWait(getDriver(), 10);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            getDriver().quit();
            driver.remove();
        }
    }

}
