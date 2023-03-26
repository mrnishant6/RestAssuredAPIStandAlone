package files;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {
	
	
	public  static JsonPath rawToJson(String jsonPath) {
		JsonPath js1 = new JsonPath(jsonPath);
		return js1;
	}

}
