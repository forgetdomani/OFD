package nvision.ofd.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
	public WebDriver driver;

	public MainPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	@FindBy(xpath = "//span[text()='Управление']")
	private WebElement tabManagement;

	public void goToManagementTab() {
		try {
			new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(tabManagement));
			tabManagement.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
