package nvision.ofd.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import nvision.ofd.testData.User;

public class LoginPage {
	public WebDriver driver;

	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	@FindBy(className = "mts16-other-sites__btn")
	private WebElement btLogin;

	@FindBy(id = "login")
	private WebElement tbLogin;

	@FindBy(id = "password")
	private WebElement tbPassword;

	@FindBy(xpath = "//button[@type='submit' and text()=' Войти']")
	private WebElement btSubmitLogin;

	public void login(User curUser) {
		try {
			new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(btLogin));
			btLogin.click();

			WebElement curCity = driver.findElement(By.xpath("//a[text()='Москва']"));
			new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(curCity));
			curCity.click();

			new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(tbLogin));
			tbLogin.sendKeys(curUser.getEmail());
			tbPassword.sendKeys(curUser.getPassword());

			new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(btSubmitLogin));
			btSubmitLogin.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
