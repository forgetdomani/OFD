package nvision.ofd.tests;

import java.util.Random;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import nvision.ofd.pages.DepartmentsPage;
import nvision.ofd.pages.LoginPage;
import nvision.ofd.pages.MainPage;
import nvision.ofd.pages.ManagementPage;
import nvision.ofd.testData.Department;
import nvision.ofd.testData.User;

public class DepartmentsTests {
	private static WebDriver driver;

	private static LoginPage loginPage;
	private static MainPage mainPage;
	private static ManagementPage managementPage;
	private static DepartmentsPage departmentsPage;

	private static User curUser;

	@BeforeClass
	public static void setup() {
		// TODO: исправить ссылку на драйвер
		System.setProperty("webdriver.gecko.driver", "c:\\gecodriver\\geckodriver.exe");

		driver = new FirefoxDriver();
		loginPage = new LoginPage(driver);
		mainPage = new MainPage(driver);
		managementPage = new ManagementPage(driver);
		departmentsPage = new DepartmentsPage(driver);

		driver.manage().window().maximize();
		driver.navigate().to("https://89.175.31.254:8068");
	}

	@Test
	public void createDepartmentTopLevel() {
		createUserData();
		login();

		goToManagementTab();
		goToDepartments();

		int departmentsBefore = getDepartmentsCount();

		Department depData = createDepartmentData();
		createDepartment(depData);

		int departmentsAfter = getDepartmentsCount();

		Assert.assertTrue(departmentsAfter == departmentsBefore + 1);

		Department lastAddedDept = getLastDept();
		Assert.assertTrue(lastAddedDept.equals(depData));
	}

	@Test
	public void createSubDepartment() {
		createUserData();
		login();

		goToManagementTab();
		goToDepartments();

		Department parentDept = getRandomDepartment();

		Department depData = createDepartmentData();
		depData.setParentDept(parentDept);

		int subDepsBefore = getSubdepartmensCount(parentDept);

		createDepartment(depData);

		int subDepsAfter = getSubdepartmensCount(parentDept);

		Assert.assertTrue(subDepsAfter == subDepsBefore + 1);

		Department lastAddedDeptSubdept = getLastSubDept(parentDept);
		Assert.assertTrue(lastAddedDeptSubdept.equals(depData));
	}

	private int getSubdepartmensCount(Department parentDept) {
		return departmentsPage.getSubDepartmentsCount(parentDept);
	}

	private Department getLastSubDept(Department parentDept) {
		return departmentsPage.getLastSubDept(parentDept);
	}

	private Department getRandomDepartment() {
		return departmentsPage.getRandomDepartment();
	}

	private Department getLastDept() {
		return departmentsPage.getLastDepartment();
	}

	private int getDepartmentsCount() {
		return departmentsPage.getDepartmentsCount();
	}

	private void createDepartment(Department depData) {
		departmentsPage.createDepartment(depData);
	}

	private void goToDepartments() {
		managementPage.goToDepartments();

	}

	private void goToManagementTab() {
		mainPage.goToManagementTab();
	}

	private void login() {
		loginPage.login(curUser);

	}

	private Department createDepartmentData() {
		return new Department(generateRandomString("DC", 3), generateRandomString("DN", 10));
	}

	private void createUserData() {
		// TODO: заменить логин и пароль реальными пользовательскими данными
		curUser = new User("login", "password", "Москва");
	}

	private String generateRandomString(String startWtih, int lght) {
		String strAllowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < lght; i++) {
			sb.append(strAllowedCharacters.charAt(random.nextInt(strAllowedCharacters.length())));
		}
		return startWtih + sb.toString();
	}

	@AfterClass
	public static void tearDown() {
		driver.close();
	}
}
