package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {


    @Test(groups = {"Sanity","Master"})
    public void verifyLoginPage() {

        try {
            logger.info("******* Starting verifyLoginPage *******");

            HomePage homePage = new HomePage(driver.get());
            homePage.clickLoginScr();

            LoginPage loginPage = new LoginPage(driver.get());
            loginPage.setTxtemail(p.getProperty("email"));
            loginPage.setTxtpassword(p.getProperty("password"));
            loginPage.setBtnlogin();

            MyAccountPage myAccountPage = new MyAccountPage(driver.get());
            Assert.assertTrue(myAccountPage.getMsgHeading(), "Successfully logged in");

            myAccountPage.clickLogout();
        }
        catch (Exception e) {
            Assert.fail();
        }

        logger.info("******* Starting verifyLoginPage *******");
    }
}
