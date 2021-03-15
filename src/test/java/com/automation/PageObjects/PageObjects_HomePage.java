package com.automation.PageObjects;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections4.map.HashedMap;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.automation.StepDefinitions.SetUp;
import com.automation.utilities.ActionMethods;

public class PageObjects_HomePage {

	protected WebDriver driver = SetUp.driver;
	public ActionMethods user = new ActionMethods();
	public HashMap<String, Integer> DatewisePrice = new HashMap<String, Integer>();
	public PageObjects_FlightSearchedPage POfS;

	@FindBy(how = How.XPATH, using = "//a[@data-cy='mmtLogo']")
	public WebElement HomepageLogoVerifcation;

	@FindBy(how = How.XPATH, using = "//div[@class='autopop__wrap makeFlex column defaultCursor']/preceding-sibling::div[@data-cy='outsideModal']")
	public WebElement PopUpOuterModalClickable;

	@FindBy(how = How.XPATH, using = "//ul[@class='fswTabs latoBlack greyText']/li")
	public List<WebElement> TripConfigurator;
	@FindBy(how = How.XPATH, using = "// div[@class='fsw_inner ']")
	public WebElement detailsOfTrip;

	@FindBy(how = How.XPATH, using = "//div[@class='DayPicker-NavBar']/span[contains(@class,'next')]")
	public WebElement DayPickerNavBarnext;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'datePickerContainer')]//div[@class='DayPicker-Month']")
	public List<WebElement> CalenderEntireBody;

	@FindBy(how = How.XPATH, using = "//a[text()='Search']")
	public WebElement SearchButtonHomepage;
	//a[text()='Search']
	
	//--------------------------------------------------------------------------------String Locators --------------------------------------------------------------------------
	
	
	public PageObjects_HomePage() {
		PageFactory.initElements(driver, this);
	}

	public Boolean verifypresenceofLogo() {
		try {
			Boolean check = user.isWebElementPresent(HomepageLogoVerifcation, driver, 5);
			user.EmbedText(SetUp.Sc, "We have found the Homepage logo");
			return check;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public void CheckLoginPopUpPresenceandHandleit() {
		try {
				user.turnOffImplicitWaits(driver);
				By PopUpforLogin_locator=By.xpath("//div[@class='autopop__wrap makeFlex column defaultCursor']");
				WebElement PopUpforLogin = user.fluentWaitonElem(PopUpforLogin_locator,driver,05,01);
				if(PopUpforLogin!=null) {
				user.embedScreenshot(driver, SetUp.Sc, "The Pop Up for Login is present");
				PopUpOuterModalClickable.click();
				Thread.sleep(2000);
				user.embedScreenshot(driver, SetUp.Sc, "The Pop Up for Login is Removed");
				}else {
					user.embedScreenshot(driver, SetUp.Sc, "The Pop Up for Login is Not present");
				}
				user.turnOnImplicitWaits(driver);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			Assert.fail();
		}
	}

	public Boolean ChooseTripType(String triptype) {
		try {
			Boolean check = false;
			for (int i = 0; i < TripConfigurator.size(); i++) {
				String tripextracted = TripConfigurator.get(i).getAttribute("data-cy");
				
				System.out.println(tripextracted);
				if (tripextracted.equalsIgnoreCase(triptype)) {
					user.isWebElementPresent(TripConfigurator.get(i).findElement(By.xpath("./span")), driver, 10);
					TripConfigurator.get(i).findElement(By.xpath("./span")).click();
					user.embedScreenshot(driver, SetUp.Sc, "Have choosen the sent in trip:: " + triptype);
					check = true;
					break;
				}
			}
			return check;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public Boolean SelectTheValueofToandFromCity(String data, String CityType) {
		try {

			WebElement ClickOnCity = detailsOfTrip.findElement(By.xpath("./div/label[@for='" + CityType + "']"));
			user.isWebElementPresent(ClickOnCity, driver, 3);
			user.embedScreenshot(driver, SetUp.Sc, "Found the picker for :: " + CityType);

			Boolean checkclickable = user.isClickable(driver, ClickOnCity, 2);
			if (!checkclickable) {
				System.out.println("Not clickable");
			}
			WebElement citychoicedropdown = detailsOfTrip
					.findElement(By.xpath("./div/label[@for='" + CityType + "']/following-sibling::div[1]//input"));
			user.isWebElementPresent(citychoicedropdown, driver, 3);
			citychoicedropdown.sendKeys(data);
			
			user.EmbedText(SetUp.Sc, "Sent in Data for : "+CityType+" and the value is :: "+data);
			
			By choosefromSuggestion_locator = By.xpath("//p[text()='SUGGESTIONS ']/parent::div/following-sibling::ul/li//div[text()='" + data + "']");
			WebElement choosefromSuggestion = user.fluentWaitonElem(choosefromSuggestion_locator,driver,30,05);
			Actions a = new Actions(driver);
			a.moveToElement(choosefromSuggestion).click().build().perform();
			user.EmbedText(SetUp.Sc, "Choose the given city");
			return true;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public HashMap<String, Integer> SelectValueofMonthandDay(String data, String DateType) {
		try {
			int l = 0;
			Boolean clickable = false;
			WebElement price = null;
			WebElement ClickOnDate = detailsOfTrip.findElement(By.xpath("./div/label[@for='" + DateType + "']"));

			user.isWebElementPresent(ClickOnDate, driver, 3);
			user.embedScreenshot(driver, SetUp.Sc, "Found the picker for :: " + DateType);
			Boolean checkclickable = user.isClickable(driver, ClickOnDate, 2);
			if (!checkclickable) {
				System.out.println("Not clickable");
			}
			// Spliting DATE
			String a[] = data.split("-");
			String Month = a[0];
			String Year = a[1];
			String Day = a[2];
			String Month_inshort = a[3];
			while (l == 0) {

				for (WebElement e : CalenderEntireBody) {
					WebElement monthAndYear = e.findElement(By.xpath("./div[@class='DayPicker-Caption']"));
					WebElement DatetobeChoosen = null;
					String monthYear = monthAndYear.getText();
					System.out.println(monthYear);
					String checkYear = Month + " " + Year;
					String checkyear2 = Month + Year;
					/*
					 * System.out.println(checkYear); System.out.println(checkyear2);
					 */
					int i = 0;
					if (monthYear.equalsIgnoreCase(checkYear) || monthYear.equalsIgnoreCase(checkyear2)) {
						l++;
						System.out.println("Got the month as :: " + monthYear);
						JavascriptExecutor js = (JavascriptExecutor) driver;
						js.executeScript(
								"arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
								monthAndYear);
						user.embedScreenshot(driver, SetUp.Sc, "Found the Month and Highlighted it");
						Thread.sleep(1000);
						// Multiple combinations leading to all selectable dates from a month
						// irrespective of knowing which month -- can help in finding dates, Total no of
						// days in the month dynamically etc
						List<WebElement> DaysSelectable = e.findElements(By.xpath(
								"./div[@class='DayPicker-Body']/div[@class='DayPicker-Week']/div[@class='DayPicker-Day DayPicker-Day--disabled' or @class='DayPicker-Day' or @class='DayPicker-Day DayPicker-Day--selected' or @class='DayPicker-Day DayPicker-Day--today' or @class='DayPicker-Day DayPicker-Day--selected DayPicker-Day--today' ]"));

						String checkDate = Month_inshort + " " + Day + " " + Year;
						user.EmbedText(SetUp.Sc, "Date to be searched for :: "+checkDate);
						DatetobeChoosen = e.findElement(By.xpath(
								"./div[@class='DayPicker-Body']//div[contains(@aria-label,'" + checkDate + "')]"));
						String AttrAriadisabled = DatetobeChoosen.getAttribute("aria-disabled");
						if (AttrAriadisabled.equalsIgnoreCase("true")) {
							//Check for Past dates
							String parsabelDate = Day+"-"+Month_inshort+"-"+Year;
							System.out.println(parsabelDate);
							Date currentDate = new Date();
							Date OutPutDate = user.getDate(parsabelDate);
							int compareDate = currentDate.compareTo(OutPutDate);
							if(compareDate >0) {
								user.EmbedText(SetUp.Sc, "Date is not clickable as it is in Past Date");
								throw new Exception("It is not clickable at this sent in date So no proceeding with Testing");
							}else {
								user.EmbedText(SetUp.Sc, "Date is not clickable as it is in Past Date or previous to To Date");
								throw new Exception("It is not clickable at this sent in date although it is not in past");
							}
							
						} else {
							user.isWebElementPresent(DatetobeChoosen, driver, 3);
							try {
								user.turnOffImplicitWaits(driver);
							price = DatetobeChoosen.findElement(By.xpath(".//p[@class=' todayPrice']"));
							if (price.getText() != null) {
								DatewisePrice.put(DateType, Integer.parseInt(price.getText()));
								DatetobeChoosen.click();

								user.embedScreenshot(driver, SetUp.Sc, "Date has been choosen as per user selection");
							} 
							}catch(NoSuchElementException ex) {
								user.embedScreenshot(driver, SetUp.Sc, "No Price mentioned");
								DatetobeChoosen.click();
								
							}
							user.turnOnImplicitWaits(driver);
							
						}
						break;
					}
				}
				if (l == 0) {
					user.isWebElementPresent(DayPickerNavBarnext, driver, 3);
					DayPickerNavBarnext.click();
				}
			}
			return DatewisePrice;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			DatewisePrice = new HashMap<String, Integer>();
			if(e.getMessage().equalsIgnoreCase("It is not clickable at this sent in date So no proceeding with Testing")) {
				if(DateType.equalsIgnoreCase("departure")) {
					DatewisePrice.put("departure", 0);
				}else {
					DatewisePrice.put("return", 0);
				}
				return DatewisePrice;
			}else {
				return null;
			}
			
		} 
	}
	public PageObjects_FlightSearchedPage clickOnSearch() {
		try {
			user.isWebElementPresent(SearchButtonHomepage, driver, 2);
			user.isClickable(driver, SearchButtonHomepage, 2);
			user.EmbedText(SetUp.Sc, "Clicked on the Search button");
			POfS =new PageObjects_FlightSearchedPage();
			return POfS;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}
