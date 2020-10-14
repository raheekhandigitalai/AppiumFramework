package tests;

import base.BaseUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class LoginScenarios extends BaseUtil {

    @Test
    public void verifyUserLoggedIn() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameTextField")));
        getDriver().findElement(By.id("usernameTextField")).sendKeys("company");
        getDriver().findElement(By.id("passwordTextField")).sendKeys("company");
        getDriver().findElement(By.id("loginButton")).click();

        Boolean isLogoutButtonPresent = getDriver().findElement(By.id("logoutButton")).isDisplayed();
        assertTrue(isLogoutButtonPresent);

        getDriver().findElement(By.id("cancelButton")).click();
        getDriver().findElement(By.id("logoutButton")).click();

        Boolean isLoginButtonPresent = getDriver().findElement(By.id("loginButton")).isDisplayed();
        assertTrue(isLoginButtonPresent);
    }

    @Test
    public void verifyInvalidCredentialsInput() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("usernameTextField")));
        getDriver().findElement(By.id("usernameTextField")).sendKeys("admin");
        getDriver().findElement(By.id("passwordTextField")).sendKeys("admin");
        getDriver().findElement(By.id("loginButton")).click();

        Boolean isInvalidCredentialsMessagePresent = getDriver().findElement(By.xpath("//*[contains(text(), 'Invalid username')]")).isDisplayed();
        assertTrue(isInvalidCredentialsMessagePresent);
    }

}
