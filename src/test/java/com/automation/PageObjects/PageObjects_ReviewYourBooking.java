package com.automation.PageObjects;

import org.apache.poi.hslf.dev.UserEditAndPersistListing;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.automation.StepDefinitions.SetUp;
import com.automation.utilities.ActionMethods;

public class PageObjects_ReviewYourBooking {
	
	WebDriver driver = SetUp.driver;
	ActionMethods user = new ActionMethods();
	
	@FindBy(how = How.XPATH, using = "//p[text()='Itinerary']")
	public WebElement ReviewBookingPageVerification;
	
	@FindBy(how = How.XPATH, using = "//span[text()='Other Services']/parent::p/following-sibling::span")
	public WebElement OtherServicePrice;
	
	@FindBy(how = How.XPATH, using = "(//span[text()='Total Amount:']/ancestor::span)[1]/following-sibling::span")
	public WebElement TotalPrice;
	
	
	
	public PageObjects_ReviewYourBooking() {
		PageFactory.initElements(driver, this);
	}
	public Boolean ValidInItenerayPage() {
		try {
			Boolean check =user.isWebElementPresent(ReviewBookingPageVerification, driver, 30);
			return check;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	public String ExtractPrice(WebElement Price) {
		try {
			String price1[]=Price.getText().split(" ");
			return price1[1].trim().replace(",", "");
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return "0";
		}
	}
	public int AddPriceandMatch() {
		try {
			int otherprice = Integer.parseInt(ExtractPrice(OtherServicePrice));
			System.out.println(otherprice);
			user.EmbedText(SetUp.Sc, "The other services Price :: "+otherprice);
			int TotalPriceBook = Integer.parseInt(ExtractPrice(TotalPrice));
			user.EmbedText(SetUp.Sc, "The Total services Price :: "+TotalPriceBook);
			System.out.println(TotalPriceBook);
			
			int priceDecidedfirst = TotalPriceBook-otherprice;
			user.EmbedText(SetUp.Sc, "Substracting the other price from the totla Price:: "+priceDecidedfirst);
			
			return priceDecidedfirst;
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}
	

}
