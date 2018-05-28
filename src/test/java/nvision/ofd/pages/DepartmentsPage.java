package nvision.ofd.pages;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import nvision.ofd.testData.Department;

public class DepartmentsPage {
	public WebDriver driver;

	public DepartmentsPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	@FindBy(xpath = "//button[text()='Добавить подразделение']")
	private WebElement btAddDep;
	@FindBy(xpath = "//input[@formcontrolname='code']")
	private WebElement tbDepCode;
	@FindBy(xpath = "//input[@formcontrolname='name']")
	private WebElement tbDepName;
	@FindBy(xpath = "//button/span[text()='Создать']")
	private WebElement btSubmitDepData;
	@FindBy(xpath = "//button[text()='Без подразделения']")
	private WebElement lsDept;
	@FindBy(xpath = "//div[@class=\"select-button\"]/input[@placeholder='Поиск подразделения']")
	private WebElement tbParDept;

	public void createDepartment(Department depData) {

		try {
			new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(btAddDep));
			btAddDep.click();

			new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(tbDepCode));
			tbDepCode.sendKeys(depData.getDepartmentCode());
			tbDepName.sendKeys(depData.getDepartmentName());
			if (depData.getParentDept() != null) {
				lsDept.click();
				tbParDept.sendKeys(depData.getParentDept().getDepartmentName());
				WebElement parDepName = driver
						.findElement(By.xpath("//div[@class='select-menu__item']/b[text()='"
								+ depData.getParentDept().getDepartmentName() + "']/following-sibling::div"))
						.findElement(By.xpath(".."));
				new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(parDepName));

				Actions act = new Actions(driver);
				act.moveToElement(parDepName).click().build().perform();
			}
			new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(btSubmitDepData));
			btSubmitDepData.click();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int getDepartmentsCount() {
		try {
			return getDepts().size();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public Department getLastDepartment() {
		Department dept = new Department();
		try {
			List<WebElement> depts = getDepts();
			dept = getDepartmentByIndex(depts, depts.size() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dept;
	}

	public Department getRandomDepartment() {
		Department dept = new Department();
		try {
			List<WebElement> depts = getDepts();
			dept = getDepartmentByIndex(depts, new Random().nextInt(depts.size() - 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dept;
	}

	private Department getDepartmentByIndex(List<WebElement> depts, int index) {
		Department dept = new Department();
		try {
			List<WebElement> depInfo = depts.get(index).findElements(By.xpath("div[@class='flex']/div/span"));

			dept.setDepartmentName(depInfo.get(0).getText());
			dept.setDepartmentCode(depInfo.get(1).getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dept;
	}

	private List<WebElement> getDepts() {
		try {
			WebElement treeView = driver.findElement(By.xpath("//app-tree-view"));
			List<WebElement> deptsL1 = treeView.findElements(By.xpath("div[@class='hierarchy__body']"));
			return deptsL1;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getSubDepartmentsCount(Department parentDept) {
		try {
			List<WebElement> sDepts = getSubDepts(parentDept);
			if (sDepts == null)
				return 0;
			else
				return sDepts.size();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	private List<WebElement> getSubDepts(Department parentDept) {
		try {
			WebElement dContainer = driver.findElement(
					By.xpath("//app-tree-view/div[@class='hierarchy__body']/div[@class='flex']/div/span[text()='"
							+ parentDept.getDepartmentName() + "']/ancestor::div[@class='hierarchy__body']"));
			if (dContainer.findElements(By.className("children")).size() != 0) {
				List<WebElement> subDeps = dContainer
						.findElements(By.xpath("div[@class='children']/app-tree-view/div[@class='hierarchy__body']"));
				return subDeps;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Department getLastSubDept(Department parentDept) {
		Department dept = new Department();
		try {
			List<WebElement> depts = getSubDepts(parentDept);
			dept = getDepartmentByIndex(depts, depts.size() - 1);
			dept.setParentDept(parentDept);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dept;
	}
}
