package ru.mmk.scriptmanager.client.util;

import ru.mmk.scriptmanager.server.domain.Source;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

public class JsonRequestBuilder {

	public static String buildSourceRequest(Source source) {
		JSONObject object = buildCodeJsonWithNodeId(source.getId(), source.getName(), source.getCode(),
				source.getNodeId());
		object.put("master", JSONBoolean.getInstance(source.isMaster()));
		return object.toString();
	}

	public static String buildExecuteCodeRequest(String code) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", new JSONString(code));
		return jsonObject.toString();
	}

	private static JSONObject buildCodeJson(Integer id, String name, String code) {
		JSONObject jsonObject = new JSONObject();
		if (id == null) {
			id = 0;
		}
		jsonObject.put("id", new JSONNumber(id));
		jsonObject.put("name", new JSONString(name));
		jsonObject.put("code", new JSONString(code));

		return jsonObject;
	}

	private static JSONObject buildCodeJsonWithNodeId(Integer id, String name, String code, Integer nodeId) {
		JSONObject jsonObject = buildCodeJson(id, name, code);
		jsonObject.put("nodeId", new JSONNumber(nodeId));

		return jsonObject;
	}
}
