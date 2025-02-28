package testCases;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

/*
  Data is valid -- login success -- test passed -- logout
  Data is valid -- login failed -- test failed

  Data is invalid -- login success -- test failed -- logout
  Data is invalid -- login failed -- test passed
*/

public class TC003_LoginDDT extends BaseClass {
    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "DatadrivenTCs") //getting dataprovider from different class
    public void verify_loginDDT(String email, String password, String expectedResult) // parameters are read from the dataprovider
    {
        try {

            logger.info("***********Starting Login with DDT TC003_LoginDDT**********");

            HomePage homePage = new HomePage(driver.get());
            homePage.clickLoginScr();

            //Login page
            LoginPage loginPage = new LoginPage(driver.get());
            loginPage.setTxtemail(email);
            loginPage.setTxtpassword(password);
            loginPage.setBtnlogin();

            //MyAccount page
            MyAccountPage myAccountPage = new MyAccountPage(driver.get());
            boolean targetPage = myAccountPage.getMsgHeading();

            if (expectedResult.equalsIgnoreCase("Valid")) // Data is valid
            {
                if (targetPage) // login is successful
                {
                    myAccountPage.clickLogout();
                    Assert.assertTrue(true);
                } else {
                    Assert.fail(); //login is unsuccessful
                }
            }
            if (expectedResult.equalsIgnoreCase("Invalid")) //Data is invalid
            {
                if (targetPage) // login is successful
                {
                    myAccountPage.clickLogout();
                    Assert.fail();
                } else
                    Assert.assertTrue(true); //login is unsuccessful

            }
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage());
        }
        logger.info("********* Login test with DDT TC003_LoginDDT *******");

    }
}
