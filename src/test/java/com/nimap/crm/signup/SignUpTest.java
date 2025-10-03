package com.nimap.crm.signup;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SignUpTest {

	public static void main(String[] args) throws InterruptedException {

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get("https://testffc.nimapinfotech.com/auth/login");
		driver.findElement(By.linkText("Sign Up")).click();
		driver.findElement(By.xpath("//input[@placeholder='Name']")).sendKeys("siddhant");
		driver.findElement(By.xpath("//input[@placeholder='Mobile No']")).sendKeys("9594663256");
		driver.findElement(By.xpath("//input[@placeholder='Email Id']")).sendKeys("pawarsiddhant123");
		driver.findElement(By.xpath("//button[text()='Submit']")).click();
		driver.findElement(By.xpath("//input[@data-placeholder='Password']")).sendKeys("Siddhant@123");
		driver.findElement(By.xpath("//input[@data-placeholder='Confirm Password']")).sendKeys("Siddhant@123");
		driver.findElement(By.xpath("Submit")).click();
	}

}
