package com.automation.StepDefinitions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import com.automation.PageObjects.PageObjects_FlightSearchedPage;
import com.automation.PageObjects.PageObjects_HomePage;
import com.automation.utilities.ActionMethods;
import com.shapesecurity.salvation2.Values.Hash;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class StepDefinition_HomePage {

	public ActionMethods user = new ActionMethods();
	public static HashMap<String, Integer> OneWayTrip = new HashMap<String, Integer>();
	public static HashMap<String, Integer> RoundTrip = new HashMap<String, Integer>();
	@Given("That user is in Homepage")
	public void That_user_is_in_Homepage() {
		try {
			PageObjects_HomePage a = new PageObjects_HomePage();
			// Asserting presence of Homepage Logo
			Boolean check = a.verifypresenceofLogo();
			Assert.assertTrue(check);
			user.embedScreenshot(SetUp.driver, SetUp.Sc, "The Homepage logo is available");
			// Asserting presence of Pop Up to be handled
			a.CheckLoginPopUpPresenceandHandleit();
			System.out.println("The homepage Logo is available");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();
		}
	}

	@Then("select the trip to {string}")
	public void select_the_trip_to(String tripType) {
		try {
			PageObjects_HomePage a = new PageObjects_HomePage();
			Boolean check = a.ChooseTripType(tripType);
			Assert.assertTrue(check);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();
		}
	}

	@Then("Select the Following values for your flight of return type {string}")
	public void Select_the_Following_values_for_your_flight_of_return_type(String tripType, DataTable dataset) {
		try {
			PageObjects_HomePage a = new PageObjects_HomePage();
			OneWayTrip=new HashMap<String, Integer>();
			RoundTrip = new HashMap<String, Integer>();
			List<Map<String, String>> data = dataset.asMaps(String.class, String.class);
			if (tripType.equalsIgnoreCase("oneWayTrip")) {
				for (int i = 0; i < data.size(); i++) {
					if (data.get(i).get("Attribute").equalsIgnoreCase("fromCity")
							|| data.get(i).get("Attribute").equalsIgnoreCase("toCity")) {
						Assert.assertTrue(a.SelectTheValueofToandFromCity(data.get(i).get("Values"), data.get(i).get("Attribute")));
					}else if(data.get(i).get("Attribute").equalsIgnoreCase("departure")){
						OneWayTrip=a.SelectValueofMonthandDay(data.get(i).get("Values"), data.get(i).get("Attribute"));
						Assert.assertNotNull(OneWayTrip);
						System.out.println(OneWayTrip);
						if(OneWayTrip.containsValue(-1)) {
							user.EmbedText(SetUp.Sc, "The sent in Date in in Past cannot proceed with Testing");
							Assert.fail("The sent date is in Past Date so failed the case");
							break;
						}
					}
				}

			}else if(tripType.equalsIgnoreCase("roundTrip")) {
				for (int i = 0; i < data.size(); i++) {
					if (data.get(i).get("Attribute").equalsIgnoreCase("fromCity")
							|| data.get(i).get("Attribute").equalsIgnoreCase("toCity")) {
						Assert.assertTrue(a.SelectTheValueofToandFromCity(data.get(i).get("Values"), data.get(i).get("Attribute")));
					}else if(data.get(i).get("Attribute").equalsIgnoreCase("departure") || data.get(i).get("Attribute").equalsIgnoreCase("return")){
						OneWayTrip=a.SelectValueofMonthandDay(data.get(i).get("Values"), data.get(i).get("Attribute"));
						Assert.assertNotNull(RoundTrip);
						RoundTrip.putAll(OneWayTrip);
						System.out.println(RoundTrip);
						if(RoundTrip.containsValue(-1)) {
							user.EmbedText(SetUp.Sc, "The sent in Date in in Past cannot proceed with Testing");
							Assert.fail("The sent date is in Past Date so failed the case");
							break;
						}else if(RoundTrip.containsValue(-2)) {
							user.EmbedText(SetUp.Sc, "The sent in Date for Return is before the selected TO date");
							Assert.fail("The sent in Date for Return is before the selected TO date");
							break;
						}
					}
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();
		}
	}
	@Then("Click on the Search Button and Verify that you are on Flight Search page")
	public void Click_on_the_Search_Button_and_Verify_that_you_are_on_Flight_Search_page() {
		try {
				PageObjects_HomePage a = new PageObjects_HomePage();
				PageObjects_FlightSearchedPage b = a.clickOnSearch();
				Assert.assertNotNull(b);
				Boolean check = b.PresenceofImage();
				Assert.assertTrue(check);
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();
		}
	}

}
