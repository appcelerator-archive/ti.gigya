/**
 * Ti.Gigya Module
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

package ti.gigya;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.util.TiConvert;
import org.json.JSONException;
import org.json.JSONObject;

import com.gigya.socialize.GSObject;

public final class Util 
{	
	// Prevent instantiation
	private Util() {}
	
	public static GSObject GSObjectFromArgument(Object args)
	{
		GSObject gsObj = null;
		
	    // We support passing the parameters as a dictionary or a JSON string.
	    // Based on the class of the parameter we will then convert to a GSObject
	    // that can be used for the Gigya APIs.
	
		if (args != null) {
			if (args instanceof KrollDict) {
				try {
					gsObj = new GSObject(TiConvert.toJSONString((KrollDict)args));
				} catch (Exception e) {
					Log.e(Constants.LCAT, "Unable to convert dictionary to Gigya object");
				}
			} else if (args instanceof String) {
				try {
					gsObj = new GSObject((String)args);
				} catch (Exception e) {
					Log.e(Constants.LCAT, "Unable to convert string to Gigya object");
				}
			} else {
				throw new IllegalArgumentException("Expected dictionary or JSON string");
			}
		}
		
		return gsObj;
	}
	
	public static KrollDict dataFromGSObject(GSObject obj)
	{
		KrollDict data = null;
		
	    // A GSObject is returned from many of the Gigya APIs. We need to
	    // convert that object to an NSDictionary that can be passed back
	    // to the JavaScript event handler.
	    
	    if (obj != null) {
	    	try {
	    		data = new KrollDict(new JSONObject(obj.toJsonString()));
	    	} catch (JSONException e) {
	    		Log.e(Constants.LCAT, "Error converting JSON string to KrollDict");
	    	}
	    }
		
		return data;
	}
}
