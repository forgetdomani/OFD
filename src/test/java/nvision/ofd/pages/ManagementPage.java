package nvision.ofd.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ManagementPage {
	public WebDriver driver;

	public ManagementPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	@FindBy(xpath = "//span[text()='Подразделения']")
	private WebElement tabDepartments;

	public void goToDepartments() {
		try {
			new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(tabDepartments));
			tabDepartments.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
