package coinstack.document.util;

import io.blocko.json.JSONException;
import io.blocko.json.JSONObject;

public class Json {
	public static JSONObject parse(String stringData) throws JSONException {
		return new JSONObject(stringData);
	}

	public static String stringify(JSONObject json) {
		return json.toString();
	}
}
