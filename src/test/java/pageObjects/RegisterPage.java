package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegisterPage extends BasePage {

    public RegisterPage(WebDriver driver) {
        super(driver);
    }


    @FindBy(xpath="//input[@id='input-firstname']")
    WebElement txtfirstName;

    @FindBy(xpath="//input[@id='input-lastname']")
    WebElement txtlastName;

    @FindBy(xpath="//input[@id='input-email']")
    WebElement txtEmail;

    @FindBy(xpath="//input[@id='input-telephone']")
    WebElement txtTelephone;

    @FindBy(xpath="//input[@id='input-password']")
    WebElement txtPassword;

    @FindBy(xpath="//input[@id='input-confirm']")
    WebElement txtConfirm;

    @FindBy(xpath="//label[@class='radio-inline']/input[@value='0']")
    WebElement txtSubscribeNo;

    @FindBy(xpath="//input[@name='agree']")
    WebElement chkAgree;

    @FindBy(xpath="//input[@value='Continue']")
    WebElement btnContinue;

    @FindBy(xpath = "//div[@id='content']//h1")
    WebElement msgRegisterConfirmation;


    public void enterFirstname(String firstname) {
        this.txtfirstName.sendKeys(firstname);
    }

    public void enterLastname(String lastname) {
        this.txtlastName.sendKeys(lastname);
    }

    public void enterEmail(String email) {
        this.txtEmail.sendKeys(email);
    }

    public void enterTelephone(String telephone) {
        this.txtTelephone.sendKeys(telephone);
    }

    public void enterPassword(String password) {
        this.txtPassword.sendKeys(password);
    }

    public void enterConfirm(String confirm) {
        this.txtConfirm.sendKeys(confirm);
    }
    public void enterSubscribeNo(String subscribeNo) {
        this.txtSubscribeNo.sendKeys(subscribeNo);
    }
    public void enterAgree(String agree) {
        this.chkAgree.click();
    }

    public void clickContinue() {
        this.btnContinue.click();
    }





    public String getConfirmationMessage() {
        try {
            return (msgRegisterConfirmation.getText());
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

}
