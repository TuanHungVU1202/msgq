package com.dpk.common;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class Utils {
	
	public static JSONObject parseToJsonObject(String jsonRawString) {
		//log.info("mapping data to ClaimDetails: " + jsonRawString);

		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(jsonRawString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

}
