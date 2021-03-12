package com.automation.driver;

import java.awt.AWTException;
import java.awt.Robot;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class WebBrowser

{

	//Logger
	//Logger log = Logger.getLogger(WebBrowser.class);	

	public WebBrowser() {};


	private static WebDriver driver = null;


	// To set the browser type	
	public WebDriver setBrowserType(String BrowserType)
	{
		driver = DriverManager.startDriver(BrowserType);
		return driver;
	}
	public void NavigateTo(String URL)
	{
		driver.get(URL);
	}
	// To stop the driver
	public void Quit()
	{
		DriverManager.stopDriver();
	}

}