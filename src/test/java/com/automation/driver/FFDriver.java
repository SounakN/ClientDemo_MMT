

package com.automation.driver;

import org.openqa.selenium.WebDriver;


import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;




@SuppressWarnings("unused")
class FFDriver implements IDriver 
{
	//Logger
	//Logger log = Logger.getLogger(FFDriver.class);
	public static String pass = "Pass";
	public static String fail = "Fail";
	private static int PageLoadTimeOut =0; 
	private static int ImlicitWaitTimeout =0; 
	private static WebDriver driver = null;

	// For deciding page load timeout

	FFDriver()
	{
		PageLoadTimeOut = BasicConstants.PAGE_LOAD_TIME_OUT;
		ImlicitWaitTimeout=BasicConstants.IMPLICIT_WAIT_TIMEOUT;
	}




	// To start the driver
	public void startDriver()
	{
		try{
			//File pathBinary = new File("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
			//FirefoxBinary Binary = new FirefoxBinary(pathBinary);
			//FirefoxProfile firefoxPro = new FirefoxProfile();      
			//driver = new FirefoxDriver(Binary,firefoxPro); 	
			//driver = new FirefoxDriver();
			//driver = new RemoteWebDriver(new URL("http://10.6.6.228:4444/wd/hub"), DesiredCapabilities.firefox())
			//http://10.6.6.228:4444/wd/hub
			ProfilesIni firProfiles = new ProfilesIni();
			DesiredCapabilities dc=DesiredCapabilities.firefox();
			FirefoxProfile wbdrverprofile = firProfiles.getProfile("Selenium");
			dc.setCapability(FirefoxDriver.PROFILE, wbdrverprofile);
			driver = new FirefoxDriver();
			//driver = new RemoteWebDriver(new URL("http://192.168.56.47:4444/wd/hub"),DesiredCapabilities.firefox());
			//	driver = new RemoteWebDriver(new URL("http://192.168.56.47:4444/wd/hub"),dc.firefox());



			// Create Firefox Profile  
			/*FirefoxProfile profile = new FirefoxProfile();  
		ProfilesIni profile = new ProfilesIni();
		FirefoxProfile myprofile = profile.getProfile("default");
        // Accept Untrusted Certificates  
		profile.setAcceptUntrustedCertificates(true);
		//myprofile.setAssumeUntrustedCertificateIssuer(true);
		driver = new FirefoxDriver(profile);
		String msg = "Firefox Driver setAcceptUntrustedCertificates(true)";	
		log.info(msg);
		CommonUtil.report(msg, pass);
		System.out.println(msg);*/

			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(PageLoadTimeOut, TimeUnit.MILLISECONDS);
			driver.manage().timeouts().implicitlyWait(ImlicitWaitTimeout, TimeUnit.SECONDS);
		}catch(Exception e){e.printStackTrace();
		}
	}


	// To stop the driver

	public void stopDriver() 

	{
		try
		{
			driver.close();
			driver.quit();
		}
		catch(Exception e)
		{
			//log.fatal("Exception while stopping the driver\n" + e.getMessage());
		}
		finally
		{
			driver = null;
		}

	}


	// To start the driver
	public WebDriver get() 

	{
		if(null==driver)
			this.startDriver();
		return driver;
	}

	// T check whether service is running or not
	public boolean isServiceRunning() 

	{
		// TODO Auto-generated method stub
		return true;
	}



}