package com.nimap.crm.signup;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginTest {

	public static void main(String[] args) throws IOException, InterruptedException {

		// Read the data from properties file
		// Step1 - Create the object or create java respresentation of physical file
		FileInputStream fis = new FileInputStream("./src/test/resources/commondata.properties");
		// step 2-Create object of properties file
		Properties pr = new Properties();
		// Step 3- load all the keys
		pr.load(fis);
		String BROWSER = pr.getProperty("browser");
		String URL = pr.getProperty("url");
		String USERNAME = pr.getProperty("username");
		String PASSWORD = pr.getProperty("password");

		WebDriver driver = null;

		// Launch the browser
		if (BROWSER.equals("chrome")) {
			driver = new ChromeDriver();
		} else if (BROWSER.equals("firefox")) {
			driver = new FirefoxDriver();
		} else {
			driver = new EdgeDriver();
		}

		// Maximize the browser
		driver.manage().window().maximize();
		// implicitly wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		// Navigate to the application
		driver.get(URL);

		// Log in
		logIn(driver, USERNAME, PASSWORD);

		// Punch in
		try {
			WebElement punchInButton = driver.findElement(By.xpath("//span[text()='Punch In']"));
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			wait.until(ExpectedConditions.elementToBeClickable(punchInButton));
			punchInButton.click();
		} catch (Exception e) {
			System.out.println("Already Punch-in");
		}

		// click on My Customers
		driver.findElement(By.xpath("//span[text()='My Customers']")).click();
		Thread.sleep(4000);

		// add New Customer
		// Read the data from excel
		// Create the object of physical file
		FileInputStream fs = new FileInputStream("./src/test/resources/testdata.xlsx");
		// Open excel in read mode
		Workbook wb = WorkbookFactory.create(fs);
		String leadName = wb.getSheet("AddCustomer").getRow(1).getCell(1).getStringCellValue();
		String personName = wb.getSheet("AddCustomer").getRow(1).getCell(2).getStringCellValue();
		String mobileNum = wb.getSheet("AddCustomer").getRow(1).getCell(3).getStringCellValue();
		String designation = wb.getSheet("AddCustomer").getRow(1).getCell(4).getStringCellValue();
		String email = wb.getSheet("AddCustomer").getRow(1).getCell(5).getStringCellValue();

		addNewCustomer(driver, leadName, personName, mobileNum, designation, email);

		WebElement createdMsg = driver.findElement(By.xpath("//div[@role='alert']"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.elementToBeClickable(createdMsg));
		if ((createdMsg.getText()).contains("been created")) {
			System.out.println("Customer has been created");
		}
		else
		{
			System.out.println("Customer not has been created");
		}
	
		
		driver.quit();
	}

	public static void logIn(WebDriver driver, String USERNAME, String PASSWORD) {
		driver.findElement(By.xpath("//input[@formcontrolname='username']")).sendKeys(USERNAME);
		driver.findElement(By.xpath("//input[@data-placeholder='Password']")).sendKeys(PASSWORD);
		driver.findElement(By.xpath("//button[text()='Sign In']")).click();
	}

	public static void addNewCustomer(WebDriver driver, String leadName, String personName, String mobileNum,
			String designation, String email) {
		driver.findElement(By.xpath("//span[contains(text(),'New Customer')]")).click();
		driver.findElement(By.xpath("//input[@formcontrolname='LeadName']")).sendKeys(leadName);
		driver.findElement(By.xpath("//input[@formcontrolname='PersonName']")).sendKeys(personName);
		driver.findElement(By.xpath("//input[@formcontrolname='MobileNo']")).sendKeys(mobileNum);
		driver.findElement(By.xpath("//input[@formcontrolname='PersonDesignation']")).sendKeys(designation);
//		driver.findElement(By.xpath("//input[@formcontrolname='Email']")).sendKeys(email);
		driver.findElement(By.xpath("//span[contains(text(),'Save')]")).click();
		
		
	}

}
