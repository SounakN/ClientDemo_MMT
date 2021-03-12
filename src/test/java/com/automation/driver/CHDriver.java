package com.automation.driver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

@SuppressWarnings("unused")
class CHDriver implements IDriver

{

	private static int PageLoadTimeOut = 0;
	private static int ImlicitWaitTimeout = 0;
	private static ChromeDriverService chService = null;
	public static WebDriver driver = null;

	CHDriver() {
		PageLoadTimeOut = BasicConstants.PAGE_LOAD_TIME_OUT;
		ImlicitWaitTimeout = BasicConstants.IMPLICIT_WAIT_TIMEOUT;

	}

	public void startDriver() {
		try {
			String ChromeDriverServer = System.getProperty("user.dir") + "/browserserver/chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", ChromeDriverServer);

			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			
			options.addArguments("start-maximized");
			options.addArguments("disable-popup-blocking");
			// options.addArguments("--incognito");
			options.addArguments("--allow-running-insecure-content");
			options.addArguments("disable-extensions");
			options.addArguments("allow-running-insecure-content");
			options.addArguments("--start-maximized");
			DesiredCapabilities cap = new DesiredCapabilities();
			/* cap.setCapability(ChromeOptions.CAPABILITY, options); */
			options.merge(cap);
			driver = new ChromeDriver(options);
			// driver = new RemoteWebDriver(new
			// URL(),capabilities);
			driver.manage().timeouts().pageLoadTimeout(PageLoadTimeOut, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(ImlicitWaitTimeout, TimeUnit.SECONDS);
		}catch (Exception e)
		{

		}
	}

	// To stop the driver
	public void stopDriver() {
		try {
			driver.close();
			// driver.quit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			driver = null;
		}
	}

	// To start the driver

	public WebDriver get() {

		if (null == driver)
		{
			this.startDriver();
		}
		return driver;
	}

}