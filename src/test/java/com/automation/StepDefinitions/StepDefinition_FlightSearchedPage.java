package com.automation.StepDefinitions;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JComboBox.KeySelectionManager;

import org.junit.Assert;

import com.automation.PageObjects.PageObjects_FlightSearchedPage;
import com.automation.PageObjects.PageObjects_ReviewYourBooking;
import com.automation.utilities.ActionMethods;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;

public class StepDefinition_FlightSearchedPage {
	
	public ActionMethods user = new ActionMethods();
	public static HashMap<String,Integer> priceofFlights ;
	public static String Flightnamewithlowest;
	public int FetchedPrice;
	public int Supposedtobeprice;

	
	@Then("Validate the following details after flight has been searched {string}")
	public void Validate_the_following_details_after_flight_has_been_searched(String Country,DataTable dataset) {
		try {
			PageObjects_FlightSearchedPage a = new PageObjects_FlightSearchedPage();
			List<Map<String, String>> data = dataset.asMaps(String.class, String.class);
			for(int i =0;i<data.size();i++) {
				if(data.get(i).get("Attribute").equalsIgnoreCase("Trip Style")) {
					String tripstyle = a.Validate_tripdetails_tripstyle();
					Assert.assertTrue(tripstyle.equalsIgnoreCase(data.get(i).get("Values")));
					
				}else if(data.get(i).get("Attribute").equalsIgnoreCase("FROM") ||data.get(i).get("Attribute").equalsIgnoreCase("TO") ) {
					String datacheck = a.Validate_tripdetails(data.get(i).get("Attribute"));
					String datacheckwithcountry = data.get(i).get("Values")+", "+Country;
					assertTrue(datacheckwithcountry.equalsIgnoreCase(datacheck));
					
				}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Then("filter and select the flights based on following flight companies")
	public void filter_and_select_the_flights_based_on_following_flight_companies(DataTable dataset) {
		try {
			priceofFlights = new HashMap<String, Integer>();
			PageObjects_FlightSearchedPage a = new PageObjects_FlightSearchedPage();
			List<Map<String, String>> data = dataset.asMaps(String.class, String.class);
			for(int i =0;i<data.size();i++) {
				System.out.println(data.get(i).get("Flight Companies"));
				String price=a.FilterFlightsbasedonAirlines(data.get(i).get("Flight Companies"));
				if(Integer.parseInt(price)!=0) {
					user.EmbedText(SetUp.Sc, "For the flight :: "+data.get(i).get("Flight Companies")+"the price is :: "+Integer.parseInt(price));
				priceofFlights.put(data.get(i).get("Flight Companies"), Integer.parseInt(price));
				}else {
					user.EmbedText(SetUp.Sc, "For flight :: "+data.get(i).get("Flight Companies")+" there are no option");
				}
			}
			System.out.println(priceofFlights);
			user.EmbedText(SetUp.Sc, "Got all the flight Prices:: "+priceofFlights);
			
			//Filter out the lowest fair::
			int k=0;
			Set<String> flightkeys = priceofFlights.keySet();
			k = priceofFlights.get(flightkeys.toArray()[0]);
			for(String flightname:flightkeys) {
				if(priceofFlights.get(flightname)<=k) {
					Flightnamewithlowest =flightname;
					k=priceofFlights.get(flightname);
					System.out.println(Flightnamewithlowest+" "+priceofFlights.get(flightname));
				}
			}
			
			Boolean checkFlightbooked = a.Select_flight_filter(Flightnamewithlowest);
			Assert.assertTrue(checkFlightbooked);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			Assert.fail();
		}
	}
	@Then("Fetch the flight fare from the floating menu and compare")
	public void Fetch_the_flight_fare_from_the_floating_menu_and_compare() {
		try {
			//click on the Book Now Button
			PageObjects_FlightSearchedPage a = new PageObjects_FlightSearchedPage();
			Assert.assertTrue(a.CliCkOnBook());
			//Will verify the price from the pop up
			FetchedPrice = Integer.parseInt(a.verifyPricefromPopUp());
			//Will do an assertion
			Supposedtobeprice = priceofFlights.get(Flightnamewithlowest);
			
			user.EmbedText(SetUp.Sc, "Flight price from filters:: "+Supposedtobeprice+ " and flight price fetched when Continuing for final checkout::"+FetchedPrice);
			Assert.assertTrue(Supposedtobeprice==FetchedPrice);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			Assert.fail();
		}
	}
	@Then("Go to Review your booking page and verify final flight fare")
	public void Go_to_Review_your_booking_page_and_verify_final_flight_fare() {
		try {
			String currentWindow = SetUp.driver.getWindowHandle();
			PageObjects_FlightSearchedPage a = new PageObjects_FlightSearchedPage();
			PageObjects_ReviewYourBooking b = a.clickOnContinue();
			Assert.assertNotNull(b);
			Set<String> windows = SetUp.driver.getWindowHandles();
			Iterator<String> it = windows.iterator();
			
			while(it.hasNext()){
				String handle = it.next();
		
				if(!handle.equals(currentWindow)) {
					SetUp.driver.switchTo().window(handle);
					int check = b.AddPriceandMatch();
					user.embedScreenshot(SetUp.driver, SetUp.Sc, null);
					if(check<0) {
						throw new Exception("there is issue with flight");
					}else {
						user.EmbedText(SetUp.Sc, "The price returned after deductiing Others:: "+check+" and the Price that was selected by the user:: "+Supposedtobeprice);
						Assert.assertTrue(check==Supposedtobeprice);
					}
					SetUp.driver.close();
					SetUp.driver.switchTo().window(currentWindow);
					
					break;
				}
			}
			
			

		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			Assert.fail();
		}
		
	}

}
