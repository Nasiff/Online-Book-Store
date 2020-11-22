package util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class RestApiHelper {
	public static String prepareResultJson(JSONArray content) {
		JSONObject result = new JSONObject();
		result.put("result", content);
		
		return result.toJSONString();
	}
}
