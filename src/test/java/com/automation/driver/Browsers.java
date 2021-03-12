package com.automation.driver;


public enum Browsers 
{
	IE,
	FIREFOX,
	CHROME,
	HEADLESSHTMLUNIT;
	
	//Get the Enum
	public static Browsers get(String s)
	{
		return Browsers.valueOf(s.trim().toUpperCase());
	}
	
}