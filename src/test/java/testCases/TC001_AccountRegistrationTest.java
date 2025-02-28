package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.RegisterPage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

    @Test(groups = {"Regression","Master"})
    public void verify_account_registration() throws InterruptedException {

        try {
            logger.info("******* Starting TC001_AccountRegistrationTest *********");
            HomePage homePage = new HomePage(driver.get());

           logger.info("Clicked Register account...");
            homePage.clickRegisterScr();

            RegisterPage registerPage = new RegisterPage(driver.get());

            logger.info("Providing account registration details......");
            registerPage.enterFirstname(randomString(10).toUpperCase());
            Thread.sleep(1000);
            registerPage.enterLastname(randomString(10).toUpperCase());
            Thread.sleep(1000);
            registerPage.enterEmail(randomString(10).toUpperCase() + "@gmail.com");
            Thread.sleep(1000);
            registerPage.enterTelephone(randomNumeric(10));
            Thread.sleep(1000);
            String pwd = randomAlphanumeric(7);
            registerPage.enterPassword(pwd);
            Thread.sleep(1000);
            registerPage.enterConfirm(pwd);
            Thread.sleep(1000);
            registerPage.enterSubscribeNo("No");
            Thread.sleep(1000);
            registerPage.enterAgree("checked");
            Thread.sleep(1000);

            registerPage.clickContinue();
            Thread.sleep(1000);

            logger.info("Validating expected message...");

            String msg = registerPage.getConfirmationMessage();
            if(msg.equals("Your Account Has Been Created!")) {
                Assert.assertTrue(true);
            }
            else
                Assert.fail();

        }
        catch (Exception e) {
           // logger.info(e.getMessage());
            Assert.fail(e.getMessage());
        }
    }
}
