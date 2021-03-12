package com.automation.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.StepDefinitions.SetUp;
import com.automation.utilities.ActionMethods;

public class PageObjects_FlightSearchedPage {

	WebDriver driver = SetUp.driver;
	ActionMethods user = new ActionMethods();
	public PageObjects_ReviewYourBooking POreview;

	@FindBy(how = How.XPATH, using = "//span[@class='logoContainer']//img")
	public WebElement FlightSearchedLogoVerifcation;

	@FindBy(how = How.XPATH, using = "//div[@class='hsw_inner']/div/label[text()='TRIP TYPE']//following-sibling::div")
	public WebElement Verifythetripstatus;

	@FindBy(how = How.XPATH, using = "//div[@class='filtersOuter']")
	public WebElement AirlinesFilter;

	@FindBy(how = How.XPATH, using = "//button[text()='Book Now']")
	public WebElement ButtonToBOOKNOW;

	@FindBy(how = How.XPATH, using = "//div[@class='multifareOuter']/div[contains(@class,'Footer')]/div/p[1]")
	public WebElement FlightFarefromFloatingMenu;

	@FindBy(how = How.XPATH, using = "//button[text()='Continue']")
	public WebElement FootermenuContinue;

	public PageObjects_FlightSearchedPage() {
		PageFactory.initElements(driver, this);
	}

	public Boolean PresenceofImage() {
		try {
			user.isWebElementPresent(FlightSearchedLogoVerifcation, driver, 2);
			user.justClickable(driver, FlightSearchedLogoVerifcation, 2);
			user.embedScreenshot(driver, SetUp.Sc, "We are at the flight Searched page");
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public String Validate_tripdetails_tripstyle() {
		try {
			user.isWebElementPresent(Verifythetripstatus, driver, 2);
			String getTripstyle = Verifythetripstatus.getText();
			user.HighlighterOnElem(driver, Verifythetripstatus);
			user.embedScreenshot(driver, SetUp.Sc, "");
			return getTripstyle;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	public String Validate_tripdetails(String Data) {
		try {
			WebElement Header = driver.findElement(
					By.xpath("//div[@class='hsw_inner']/div/span[text()='" + Data + "']/following-sibling::input"));
			user.isWebElementPresent(Header, driver, 2);
			user.HighlighterOnElem(driver, Header);
			user.embedScreenshot(driver, SetUp.Sc, "");
			return Header.getAttribute("value");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public String FilterFlightsbasedonAirlines(String data) {
		try {
			String PriceOfFlight[] = null;
			Boolean check = user.ScrollIntoView(driver, AirlinesFilter);
			if (check) {
				By Airlinefilteredname_locator = By
						.xpath("//p[text()='Airlines']/following-sibling::div//span[@title='" + data + "']");
				WebElement Airlinefilteredname = user.fluentWaitonElem(Airlinefilteredname_locator, driver, 10, 05);
				if (Airlinefilteredname != null) {
					user.HighlighterOnElem(driver, Airlinefilteredname);
					user.EmbedText(SetUp.Sc, "Located the sent in Airline :: " + data);
					WebElement AirlinefiltercheckBox = Airlinefilteredname
							.findElement(By.xpath("./parent::span/preceding-sibling::span"));
					user.HighlighterOnElem(driver, AirlinefiltercheckBox);
					user.embedScreenshot(driver, SetUp.Sc, "hightlighted on the selected AIrline");

					WebElement AirlineFlightPrice = Airlinefilteredname
							.findElement(By.xpath("./parent::span/parent::div/following-sibling::span"));
					user.isWebElementPresent(AirlineFlightPrice, driver, 4);
					PriceOfFlight = (AirlineFlightPrice.getText()).split(" ");
					System.out.println(PriceOfFlight[1]);
					user.EmbedText(SetUp.Sc, "Got the price :: " + PriceOfFlight[1]);
				} else {
					PriceOfFlight = new String[] { "a", "0" };
				}

			}
			return PriceOfFlight[1].trim().replace(",", "");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return "Got Error";
		}
	}

	public Boolean Select_flight_filter(String data) {
		try {

			Boolean check = user.ScrollIntoView(driver, AirlinesFilter);
			By Airlinefilteredname_locator = By
					.xpath("//p[text()='Airlines']/following-sibling::div//span[@title='" + data + "']");
			WebElement Airlinefilteredname = user.fluentWaitonElem(Airlinefilteredname_locator, driver, 10, 05);
			if (Airlinefilteredname != null) {
				user.ScrollIntoView(driver, AirlinesFilter);
				WebElement AirlinefiltercheckBox = Airlinefilteredname
						.findElement(By.xpath("./parent::span/ancestor::label"));
				AirlinefiltercheckBox.click();
				user.EmbedText(SetUp.Sc, "Selected the flight in filter with lowest fare");
				Thread.sleep(3000);
				user.embedScreenshot(driver, SetUp.Sc, "Screenshot for fligt Selected");
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public Boolean CliCkOnBook() {
		try {
			user.isWebElementPresent(ButtonToBOOKNOW, driver, 2);
			user.embedScreenshot(driver, SetUp.Sc, null);
			Boolean booknow = user.isClickable(driver, ButtonToBOOKNOW, 5);
			if (booknow) {
				user.EmbedText(SetUp.Sc, "Clicked on the Book Now");
				return true;
			} else {
				throw new Exception("It is not clickable");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public String verifyPricefromPopUp() {
		try {
			Boolean check = user.isWebElementPresent(FlightFarefromFloatingMenu, driver, 10);
			String price[] = null;
			if (check) {
				user.embedScreenshot(driver, SetUp.Sc, null);
				user.EmbedText(SetUp.Sc, "got the price");
				price = FlightFarefromFloatingMenu.getText().split(" ");

			} else {
				price = new String[] { "", "0" };
			}
			return price[1].trim().replace(",", "");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return "0";
		}
	}

	public PageObjects_ReviewYourBooking clickOnContinue() {
		try {
			user.isClickable(driver, FootermenuContinue, 2);
			user.embedScreenshot(driver, SetUp.Sc, "Clicked on ");
			user.EmbedText(SetUp.Sc, "Clicked on COntinue");
			POreview = new PageObjects_ReviewYourBooking();
			return POreview;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}
