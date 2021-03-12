package com.automation.driver;


import org.openqa.selenium.WebDriver;

public interface IDriver 
{

	
	void startDriver();
	void stopDriver();
	WebDriver get();

}