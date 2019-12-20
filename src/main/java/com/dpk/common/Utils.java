package com.dpk.common;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class Utils {
	public static JSONObject parseToJsonObject(String jsonRawString) {
//		log.info("mapping data to ClaimDetails: " + jsonRawString);

		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(jsonRawString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	public static String parseToDateString(String timeInString) {
		Long parsedCreatedDate = Long.parseLong(timeInString);
		Date date = new Date(parsedCreatedDate);
		DateFormat formater = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
		String formattedDate = formater.format(date);
		return formattedDate;
	}
}
