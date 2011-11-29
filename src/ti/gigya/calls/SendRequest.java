/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package ti.gigya.calls;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;

import ti.gigya.Constants;
import ti.gigya.Util;

import com.gigya.socialize.GSObject;
import com.gigya.socialize.GSResponse;
import com.gigya.socialize.GSResponseListener;
import com.gigya.socialize.android.GSAPI;

public class SendRequest 
{
	// Prevent instantiation
	private SendRequest() {}

	public static void call(final KrollProxy proxy, GSAPI gsAPI, KrollDict args)
	{
	    // NOTE: This must be called on the UI thread, even though it doesn't perform any UI
		String method = args.getString("method");
		GSObject gsObj = Util.GSObjectFromArgument(args.get("params"));
		boolean useHTTPS = args.optBoolean("useHTTPS", false);
			
		try {
			gsAPI.sendRequest(
				method, 
				gsObj, 
				useHTTPS, 
				new GSResponseListener() {
					public void onGSResponse(String method, GSResponse response, Object context)
					{
						if (proxy.hasListeners(Constants.RESPONSE)) {
							try {
								KrollDict event = new KrollDict();
								event.put("method", method);
								event.put("errorCode", response.getErrorCode());
								event.put("data", Util.dataFromGSObject(response.getData()));
								event.put("responseText", response.getResponseText());
								event.put("errorMessage", response.getErrorMessage());
								
								proxy.fireEvent(Constants.RESPONSE, event);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}, 
				null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
