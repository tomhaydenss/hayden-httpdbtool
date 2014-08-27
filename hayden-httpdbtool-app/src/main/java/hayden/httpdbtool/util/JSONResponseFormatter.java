package hayden.httpdbtool.util;

import hayden.httpdbtool.service.util.ResponseFormatter;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONResponseFormatter implements ResponseFormatter {

	private static Gson gson = null;
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
		gson = gsonBuilder.enableComplexMapKeySerialization().setPrettyPrinting().create();
	}

	public String format(List<Map<String, Object>> result) {
		return gson.toJson(result);
	}

}
