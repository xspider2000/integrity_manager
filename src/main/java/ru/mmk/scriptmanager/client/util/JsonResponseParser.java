package ru.mmk.scriptmanager.client.util;

import java.util.ArrayList;
import java.util.List;

import ru.mmk.scriptmanager.server.domain.Source;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;

public class JsonResponseParser {

	public static List<Source> parseSourceListResponse(String responsePayload) {
		List<JSONObject> sourceListJsonObject = parseJsonArray(responsePayload);
		List<Source> result = new ArrayList<Source>();

		for (JSONObject sourceJsonObject : sourceListJsonObject) {
			result.add(parseSourceResponse(sourceJsonObject.toString()));
		}
		return result;
	}

	public static Source parseSourceResponse(String jsonString) {
		JSONObject jsonObject = parseJsonObject(jsonString);

		Source source = new Source();
		source.setName(jsonObject.get("name").isString().stringValue());
		source.setCode(jsonObject.get("code").isString().stringValue());
		source.setId(Integer.parseInt(jsonObject.get("id").isNumber().toString()));
		source.setNodeId(Integer.parseInt(jsonObject.get("nodeId").isNumber().toString()));
		source.setMaster(jsonObject.get("master").isBoolean().booleanValue());

		return source;
	}

	public static String parseOutputFromExecuteResponse(String jsonString) {
		JSONObject jsonObject = JSONParser.parseStrict(jsonString).isObject();
		return jsonObject.get("output").isString().stringValue();
	}

	private static List<JSONObject> parseJsonArray(String jsonString) {
		JSONArray jsonArray = JSONParser.parseStrict(jsonString).isArray();
		List<JSONObject> result = new ArrayList<JSONObject>();
		if (jsonArray != null) {
			int len = jsonArray.size();
			for (int i = 0; i < len; i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				result.add(jsonObject);
			}
		}
		return result;
	}

	private static JSONObject parseJsonObject(String jsonString) {
		JSONObject result = JSONParser.parseStrict(jsonString).isObject();
		return result;
	}
}
