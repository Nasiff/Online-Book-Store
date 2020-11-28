package util;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RestApiHelper {
	public static String prepareResultJson(JSONObject content) {
		JSONObject result = new JSONObject();
		result.put("result", content);
		
		return result.toJSONString();
	}
	
	public static String prepareResultJson(JSONArray content) {
		JSONObject result = new JSONObject();
		result.put("result", content);
		
		return result.toJSONString();
	}
	
	// helps prepare ERROR JSON response body
	public static String prepareErrorJson(String errorMsg) {
		JSONObject errorContent = new JSONObject();
		errorContent.put("successful", "false");
		errorContent.put("error", errorMsg);
		
		return prepareResultJson(errorContent);
	}
	
	// helps prepare ERROR JSON response body with an additional parameter field in JSON
	public static String prepareErrorJson(String errorMsg, String paramName, String paramValue) {
		JSONObject errorContent = new JSONObject();
		errorContent.put("successful", "false");
		errorContent.put("error", errorMsg);
		errorContent.put(paramName, paramValue);
		
		return prepareResultJson(errorContent);
	}
	
	public static Response responseHelper(String content) {
		System.out.println(content);
		try {
			JSONParser parser = new JSONParser();
			JSONObject jsonResult = (JSONObject) parser.parse(content);
			JSONObject json = (JSONObject) jsonResult.get("result");
			// if JSON resp body has "successful": "false"
			if (json.get("successful").toString().equals("false")) {
				return Response
						.status(Response.Status.BAD_REQUEST)
						.entity(content)
						.header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
						.type(MediaType.APPLICATION_JSON)
						.build();
			} else {
				return Response
						.status(Response.Status.OK)
						.entity(content)
						.header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
						.type(MediaType.APPLICATION_JSON)
						.build();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(content)
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		
	}
	
}