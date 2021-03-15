package com.automation.utilities;

import java.awt.Robot;
//import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
//import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.StepDefinitions.SetUp;
import com.automation.driver.BasicConstants;

//import org.testng.AssertJUnit;
import org.junit.Assert;

import io.cucumber.java.Scenario;

//import junit.framework.Assert;

public class ActionMethods {

	public static WebDriverWait wait;
	
	public Date getDate(String date) {  
		try {
		    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");  
		    Date OutDate= formatter.parse(date);	   
		    System.out.println(OutDate);  
		    return OutDate;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}  
	public void sync_withStaleElem(WebDriver driver, WebElement element) {
		try {
			FluentWait<WebDriver> wait = new WebDriverWait(driver, 30).ignoring(StaleElementReferenceException.class)
					.pollingEvery(5, TimeUnit.SECONDS).withTimeout(30, TimeUnit.SECONDS);
			;
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Boolean sync_withNoSuchElemAndStateElement(WebDriver driver, WebElement element) {
		try {
			FluentWait<WebDriver> wait = new WebDriverWait(driver, 100)
					.ignoring(NoSuchElementException.class)
					.pollingEvery(5, TimeUnit.SECONDS).withTimeout(30, TimeUnit.SECONDS);
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (NoSuchElementException e) {
			System.out.println("No Suh Elem");
			return false;
		} catch (ElementNotVisibleException e) {
			System.out.println("Elem not visible");
			return false;
		} catch (TimeoutException e) {
			System.out.println("Timed Out");
			return false;
		}
	}

	public Boolean syncClickable(WebDriver driver, WebElement element) {
		try {
			FluentWait<WebDriver> wait = (WebDriverWait) new WebDriverWait(driver, 100)
					.ignoring(StaleElementReferenceException.class);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			return true;
		} catch (ElementNotInteractableException e) {
			System.out.println("Not interactable");
			return false;
		} catch (TimeoutException e) {
			System.out.println("Timeout");
			return false;
		} catch (WebDriverException e) {
			System.out.println("WebDriver couldnot click");
			return false;
		}

	}

	public Boolean isClickable(WebDriver driver, WebElement element, int time) {
		try {
			wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			return true;
		} catch (ElementNotInteractableException e) {
			System.out.println("Not interactable");
			return false;
		} catch (TimeoutException e) {
			System.out.println("Timeout");
			return false;
		} catch (WebDriverException e) {
			System.out.println("WebDriver couldnot click");
			return false;
		}
	}

	public void HighlighterOnElem(WebDriver driver, WebElement Element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
					Element);
		} catch (Exception e) {

		}
	}

	public Boolean justClickable(WebDriver driver, WebElement element, int time) {
		try {
			wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			return true;
		} catch (ElementNotInteractableException e) {
			System.out.println("Not interactable");
			return false;
		} catch (TimeoutException e) {
			System.out.println("Timeout");
			return false;
		} catch (WebDriverException e) {
			System.out.println("WebDriver couldnot click");
			return false;
		}
	}

	public void type(WebElement element, String str) {
		element.sendKeys(str);

	}

	public void clear(WebElement element) {
		element.clear();
	}

	public boolean verifyElement(Object element) {
		try {
			if (element instanceof WebElement) {
				if (((WebElement) element).isDisplayed()) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (NullPointerException e) {
			System.out.println("Object value null");
			e.printStackTrace();
			return false;
		} catch (ElementNotVisibleException e) {
			System.out.println("Object not visible");
			e.printStackTrace();
			return false;
		} catch (NoSuchElementException e) {
			System.out.println("Object Not exists");
			e.printStackTrace();
			return false;
		}

	}

	public boolean isWebElementPresent(WebElement element, WebDriver driver, int time) {
		try {
			wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (NoSuchElementException e) {
			System.out.println("No Suh Elem");
			return false;
		} catch (ElementNotVisibleException e) {
			System.out.println("Elem not visible");
			return false;
		} catch (TimeoutException e) {
			System.out.println("Timed Out");
			return false;
		}

	}
	
	public Boolean ScrollIntoView(WebDriver driver, WebElement Element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);",Element); 
			if(Element.isDisplayed()) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			return false;
		}
	}

	public void setClipBoardData(String str) {
		StringSelection stringselection = new StringSelection(str);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);
	}

	public void selectElementFromDropDown(WebDriver driver, WebElement element, String visibleText) {
		Select dropDown = new Select(element);
		dropDown.selectByVisibleText(visibleText);
	}

	public void scrolldown(WebDriver driver) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(200, document.body.scrollHeight)");

	}
	public void scrollUp(WebDriver driver) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, -350)");

	}

	public boolean checkTitle(WebDriver driver, String title) {
		try {

			if (driver.getTitle().equalsIgnoreCase(title)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void waiting(long a) {

		try {
			Thread.sleep(a);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void clickCheckbox(WebElement element) {
		System.out.println("Starting point");
		try {
			if (element.isSelected()) {
				System.out.println("ending point");
			} else {

				System.out.println("1st");
				element.click();
				Thread.sleep(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void scrollToElement(WebDriver driver, WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);

	}

	public boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}
	}

	public void jsClick(WebDriver driver, WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);

	}

	public void embedScreenshot(WebDriver driver, Scenario result, String message) {

		byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		result.attach(screenshot, "image/png", message);

	}

	public void EmbedText(Scenario result, String message) {

		result.log(message);

	}

	public WebElement fluentWaitonElem(By Locator, WebDriver driver, int timeout, int polltime) {
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeout, TimeUnit.SECONDS)

					.pollingEvery(polltime, TimeUnit.SECONDS).ignoring(org.openqa.selenium.StaleElementReferenceException.class).ignoring(org.openqa.selenium.NoSuchElementException.class)
					.ignoring(org.openqa.selenium.ElementNotVisibleException.class).ignoring(org.openqa.selenium.TimeoutException.class);
					;
			WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					return driver.findElement(Locator);
				}
			});
			return foo;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	public void turnOffImplicitWaits(WebDriver driver) {
	    driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}

	public void turnOnImplicitWaits(WebDriver driver) {
	    driver.manage().timeouts().implicitlyWait(BasicConstants.IMPLICIT_WAIT_TIMEOUT, TimeUnit.SECONDS);
	}

}
