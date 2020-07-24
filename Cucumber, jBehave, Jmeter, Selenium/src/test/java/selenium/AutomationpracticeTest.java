package selenium;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class AutomationpracticeTest {

	private String URL = "http://automationpractice.com/index.php";
	private WebDriver driver;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeAll
	static void setDriver() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\selenium\\chromedriver.exe");
	}

	@BeforeEach
	void setUp() {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(URL);

		driver.manage().deleteAllCookies();
	}

	@Test
	void shouldOpenRegistrationForm_When_AddressEmailIsCorrect() throws IOException {
		//given
		String email = "ytidizybi-589781@yopmail.com";

		//when
		signInNewAccount(email);

		takeSnapShot("1");

		//then
		assertEquals("http://automationpractice.com/index.php?controller=authentication&back=my-account",
				driver.getCurrentUrl()
		);
		driver.quit();
	}

	@Test
	public void shouldSetColorInputToRed_When_FiledIsEmpty() throws Exception {
		//given
		String email = "testWrongUser@test.com";
		List<String> stream = Arrays.asList("customer_firstname","customer_lastname","email","passwd");

		//when
		signInNewAccount("ytidizybi-58781@yopmail.com");

		takeSnapShot("2");

		//then
		stream.forEach(ele -> {
			driver.findElement(By.id(ele)).click();
			driver.findElement(By.id(ele)).clear();
			driver.findElement(By.id("uniform-newsletter")).click();
			inputColorIsRed(ele);
		});
	}

	@Test
	void  shouldDisplayErrorWith8Elements_When_InputWereEmptyOrValid() throws InterruptedException, IOException {
		signInNewAccount("ytidizybi-588451@yopmail.com");

		driver.findElement(
				By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='DNI / NIF / NIE'])[1]/following::span[1]")
		).click();

		takeSnapShot("3");

		int countError = driver.findElement(By.className("alert")).findElements(By.tagName("li")).size();

		assertEquals(8,countError);
	}



	@Test
	void shouldCreateAccount_When_RequiredInputsAreCorrect() throws InterruptedException, IOException {

		//when
		createAccount("ytidizybi-582681@yopmail.com","Name ", "Surname","Password","Adddre 123","City",
				"00000","123123123");
		//then
		assertEquals("http://automationpractice.com/index.php?controller=my-account",
				driver.getCurrentUrl()
		);
		takeSnapShot("4");
		assertFalse(driver.manage().getCookies().isEmpty());
	}

	@Test
	void shouldDisplayError_When_UserDataWereWrong() throws IOException {

		createAccount("ytidizybi-5881@yopmail.com","Name ", "Surname","Password","Adddre 123","City",
				"00000","123123123");

		driver.findElement(By.linkText("Sign out")).click();
		driver.findElement(By.id("email")).click();
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("wrong@wp.pl");
		driver.findElement(By.id("passwd")).click();
		driver.findElement(By.id("passwd")).clear();
		driver.findElement(By.id("passwd")).sendKeys("wrong");
		driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Forgot your password?'])[1]/following::span[1]")).click();

		takeSnapShot("5");

		assertEquals(1,
				driver.findElement(By.className("alert")).findElements(By.tagName("li")).size()
		);


	}

	@Test
	void shouldLogIn_When_UserDataWereCorrect() throws IOException {

		createAccount("ytidizybi-582851@yopmail.com","Name ", "Surname","Password","Adddre 123","City",
				"00000","123123123");

		driver.findElement(By.linkText("Sign out")).click();
		driver.findElement(By.id("email")).click();
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("ytidizybi-582851@yopmail.com");
		driver.findElement(By.id("passwd")).click();
		driver.findElement(By.id("passwd")).clear();
		driver.findElement(By.id("passwd")).sendKeys("Password");
		driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Forgot your password?'])[1]/following::span[1]")).click();

		takeSnapShot("6");

		assertEquals("http://automationpractice.com/index.php?controller=my-account",
				driver.getCurrentUrl()
		);


	}

	@Test
	void shouldDisplaySite_When_WindowsSizeIsSet() throws IOException {
		resolutionTest(800,600,"123@wp.pl","800x600");
		resolutionTest(1024,768,"1231@wp.pl","1024x768");
		resolutionTest(1920,1080,"1235@wp.pl","1920x1080");
	}



	@AfterEach
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private void resolutionTest(int width,int height,String email, String nameScreen) throws IOException {
		driver.manage().window().setSize(new Dimension(width, height));
		signInNewAccount(email);
		takeSnapShot(nameScreen);
	}

	private void takeSnapShot(String name) throws IOException {
		String resourcesDirectory = System.getProperty("user.dir") + "\\src\\test\\resources\\selenium\\";
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(resourcesDirectory + name + ".jpg"));
	}

	private void createAccount(String email, String name,String surname,String password,String address, String city, String postCode,
	                           String phoneNumber) {
		signInNewAccount(email);

		driver.findElement(By.id("customer_firstname")).click();
		driver.findElement(By.id("customer_firstname")).clear();
		driver.findElement(By.id("customer_firstname")).sendKeys(name);
		driver.findElement(By.id("customer_lastname")).click();
		driver.findElement(By.id("customer_lastname")).clear();
		driver.findElement(By.id("customer_lastname")).sendKeys(surname);
		driver.findElement(By.id("email")).click();
		driver.findElement(By.id("passwd")).click();
		driver.findElement(By.id("passwd")).clear();
		driver.findElement(By.id("passwd")).sendKeys(password);
		driver.findElement(By.id("firstname")).click();
		driver.findElement(By.id("lastname")).click();
		driver.findElement(By.id("address1")).click();
		driver.findElement(By.id("address1")).clear();
		driver.findElement(By.id("address1")).sendKeys(address);
		driver.findElement(By.id("city")).click();
		driver.findElement(By.id("city")).clear();
		driver.findElement(By.id("city")).sendKeys(city);
		driver.findElement(By.id("id_state")).click();
		new Select(driver.findElement(By.id("id_state"))).selectByVisibleText("California");
		driver.findElement(By.id("id_state")).click();
		driver.findElement(By.id("postcode")).click();
		driver.findElement(By.id("postcode")).clear();
		driver.findElement(By.id("postcode")).sendKeys(postCode);
		driver.findElement(By.id("id_country")).click();
		driver.findElement(By.id("id_country")).click();
		driver.findElement(By.id("phone_mobile")).click();
		driver.findElement(By.id("phone_mobile")).clear();
		driver.findElement(By.id("phone_mobile")).sendKeys(phoneNumber);
		driver.findElement(By.xpath("(.//*[normalize-space(text())" +
				" and normalize-space(.)='DNI / NIF / NIE'])[1]/following::span[1]")).click();
	}

	private void inputColorIsRed(String element ) {
		String selectorValue = driver.findElement(By.id(element))
				.getCssValue("color");
		assertEquals("rgba(241, 51, 64, 1)",selectorValue);
	}

	private void signInNewAccount(String email) {
		driver.findElement(By.linkText("Sign in")).click();
		driver.findElement(By.id("email_create")).click();
		driver.findElement(By.id("email_create")).clear();
		driver.findElement(By.id("email_create")).sendKeys(email);
		driver.findElement(By.id("create-account_form")).submit();
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
