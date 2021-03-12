package com.automation.driver;

import org.openqa.selenium.WebDriver;

public class DriverManager {
	// Logger
	// Logger log = Logger.getLogger(DriverManager.class);

	public static Browsers browser = null;
	public static IDriver DriverService = null;

	// Constructors
	public DriverManager() {
	};

	// To start the driver
	public static WebDriver startDriver(String browsertype) {
		browser = Browsers.get(browsertype);

		if (null == DriverService) {
			switch (browser) {

			/*
			 * case FIREFOX: DriverService = new FFDriver(); break;
			 */
			case CHROME:
				DriverService = new CHDriver();
				break;

			default:
				break;
			}
		}

		return DriverService.get();
	}

	// To stop the driver
	public static void stopDriver() {
		DriverService.stopDriver();
	}

}