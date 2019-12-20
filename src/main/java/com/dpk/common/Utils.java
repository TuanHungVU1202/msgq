package com.dpk.common;

import java.nio.charset.Charset;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class Utils {

	public static JSONObject parseToJsonObject(String jsonRawString) {
		// log.info("mapping data to ClaimDetails: " + jsonRawString);

		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(jsonRawString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

}
