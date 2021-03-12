package com.automation.utilities;

import java.util.Properties;



public class EnvUtil 

{
	
	//Logger
	//static Logger log = Logger.getLogger(EnvUtil.class);
	
	private static Properties props = null;
	

	// To get and set all properties from property reader
	
	public static void loadProperties(String env)
	{
		//log.info("Passed Env: " + env);
		props = PropertyReader.loadAllProperties(env);
	}
	
	@SuppressWarnings("finally")
	public static String getProperty(String key)
	{
		//log.info("Env Value Request: Key=" + key);
		String keyvalue = key;
		try
		{
			keyvalue = props.getProperty(key);
			
		}
		catch(Exception e)
		{
			//log.fatal("Exception Occured while getting the property value\n" + e.getMessage());
		}
		finally
		{
			//log.info("Env Value Request: Value=" + keyvalue);
			return keyvalue;
		}
	}
	
	public static void SetProperty(String key, String value)
	{
		props.put(key, value);
	}
}