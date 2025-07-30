package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import utils.Log;

public class LoginTest extends BaseTest {

    /**
     * Invalid credentials data provider.
     * Uses your existing hard‑coded list.
     */
    @DataProvider(name = "LoginData2")
    public Object[][] getInvalidData() {
        return new Object[][] {
            { "user1", "pass1" },
            { "user2", "pass2" },
            { "user3", "pass3" }
        };
    }

    /**
     * 1️⃣ Valid login test (admin@yourstore.com / admin)
     */
    @Test(priority = 1)
    public void testValidLogin() {
        Log.info("Starting VALID login test...");
        test = ExtentReportManager.createTest("Login Test - Valid Credentials");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("admin@yourstore.com");
        loginPage.enterPassword("admin");
        loginPage.clickLogin();

        String title = driver.getTitle();
        Log.info("Verifying dashboard title after valid login");
        Assert.assertEquals(
            title,
            "Dashboard / nopCommerce administration",
            "Expected to land on the nopCommerce admin dashboard"
        );

        test.pass("Valid login succeeded and dashboard loaded");
    }

    /**
     * 2️⃣ Invalid login test (data‑driven). Expects to stay on the login page.
     */
    @Test(dataProvider = "LoginData2", priority = 2)
    public void testInvalidLogin(String username, String password) {
        Log.info("Starting INVALID login test with [" + username + "/" + password + "]...");
        test = ExtentReportManager.createTest("Login Test - Invalid Credentials: " + username);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        String title = driver.getTitle();
        Log.info("Verifying login page title after invalid login");
        Assert.assertEquals(
            title,
            "nopCommerce demo store. Login",
            "Expected to remain on the login page with invalid creds"
        );

        test.pass("Invalid login properly blocked for " + username);
    }
}
