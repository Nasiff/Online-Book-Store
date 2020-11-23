package util;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
	
	public static Response responseHelper(String content) {
		if (content.contains("ERROR:")) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(content)
					.type(MediaType.TEXT_PLAIN)
					.build();
		} else {
			return Response
					.status(Response.Status.OK)
					.entity(content)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
	}
	
}
