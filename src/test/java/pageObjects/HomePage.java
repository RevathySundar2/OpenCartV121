package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[@class='list-group-item'][normalize-space()='Login']")
    WebElement loginScr;

    @FindBy(xpath = "//a[@class='list-group-item'][normalize-space()='Register']")
    WebElement registerScr;

    public void clickLoginScr() {
        loginScr.click();
    }

    public void clickRegisterScr() {
        registerScr.click();
    }

}
