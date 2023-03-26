package demoProject;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Datafiles;
import files.ReusableMethods;


public class APIBasics {

	public static void main(String[] args) {


		//given- all Input Details
		//when - Submit resource- http methods
		//Then- verify the value
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(Datafiles.AddLocation()).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
		.body("scope", equalTo("APP")).header("Server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();
		
		System.out.println("Response is: " + response);
		
		JsonPath js = ReusableMethods.rawToJson(response);
		
		String place_id =js.getString("place_id");
		
		System.out.println(place_id);
		
		
		
		//Add place - update place with new Address and verify the address
		
		String new_address = "Summer Walk , Travels Anywhere";
		
		String put_Response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\n"
				+ "    \"place_id\":\"" + place_id + "\",\n"
				+ "    \"address\": \"" + new_address + "\",\n"
				+ "    \"key\" : \"qaclick123\"\n"
				+ "}")
		.when().put("maps/api/place/update/json").then().assertThat().statusCode(200)
		.body("msg", equalTo("Address successfully updated")).extract().response().asString();
		
		System.out.print("Address updated : " + put_Response);
		
		
		//Get Place - get the address that is updated
		
		String get_Response = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id)
							  .when().get("maps/api/place/get/json")
							  .then().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js1 = ReusableMethods.rawToJson(get_Response);
		String actualAddress = js1.getString("address");
		
		System.out.println(actualAddress);
		
		Assert.assertEquals(actualAddress, new_address);
	}
	
	
	
	

}
