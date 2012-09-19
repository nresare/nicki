package com.resare.nicki;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Tools for transforming various data structures to and from JSON.
 */
public class JSONTools {
  public static Alert parseAlert(String jsonData) {
    try {
      JSONObject o = new JSONObject(jsonData);
      Alert alert = new Alert();
      String s = o.optString("id");
      if (!"".equals(s)) {
        alert.setId(s);
      }
      s = o.optString("severity");
      if (!"".equals(s)) {
        alert.setSeverity(Severity.valueOf(s.toUpperCase()));
      }
      s = o.optString("message");
      if (!"".equals(s)) {
        alert.setMessage(s);
      }
      s = o.optString("type");
      if (!"".equals(s)) {
        alert.setType(AlertType.valueOf(s));
      }
      s = o.optString("entity");
      if (!"".equals(s)) {
        alert.setEntity(s);
      }
      s = o.optString("detail");
      if (!"".equals(s)) {
        alert.setDetail(s);
      }
      JSONObject extra = o.optJSONObject("extra_fields");
      if (extra != null) {
        Map<String, String> m = new HashMap<String, String>();
        for (Iterator iter = extra.keys(); iter.hasNext(); ) {
          String key = (String)iter.next();
          m.put(key, (String)extra.get(key));
        }
        alert.setExtraFields(m);
      }
      return alert;
    } catch (JSONException e) {
      throw new Error(e);
    }
  }
}
