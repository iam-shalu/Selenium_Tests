package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import utils.ExcelUtils;
import utils.ExtentReportManager;
import utils.Log;

public class LoginTest extends BaseTest {

    @DataProvider(name = "LoginData")
    public Object[][] getLoginData() throws IOException {
        String filePath = System.getProperty("user.dir") + "/testdata/TestData.xlsx";
        ExcelUtils.loadExcel(filePath, "Sheet1");
        int rowCount = ExcelUtils.getRowCount();
        Object[][] data = new Object[rowCount - 1][2];

        for (int i = 1; i < rowCount; i++) {
            data[i - 1][0] = ExcelUtils.getCellData(i, 0); // Username
            data[i - 1][1] = ExcelUtils.getCellData(i, 1); // Password
        }
        ExcelUtils.closeExcel();
        return data;
    }

    @Test(dataProvider = "LoginData")
    public void testValidLogin(String username, String password) {
        Log.info("Starting login test...");
        test = ExtentReportManager.createTest("Login Test - Valid Login");

        test.info("Navigating to URL");
        LoginPage loginPage = new LoginPage(driver);

        Log.info("Adding credentials");
        test.info("Adding Credentials");
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);

        test.info("Clicking on Login button");
        loginPage.clickLogin();

        String actualTitle = driver.getTitle();
        System.out.println("Title of the page is : " + actualTitle);
        Log.info("Verifying page title");
        test.info("Verifying page title");

        // ← Updated expected title
        Assert.assertEquals(actualTitle,
            "Dashboard / nopCommerce administration",
            "Page title did not match after login");

        test.pass("Login Successful");
    }
}
