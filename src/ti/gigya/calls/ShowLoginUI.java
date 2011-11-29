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
import org.appcelerator.titanium.util.Log;

import ti.gigya.Constants;
import ti.gigya.Util;

import com.gigya.socialize.GSObject;
import com.gigya.socialize.android.GSAPI;
import com.gigya.socialize.android.event.GSLoginUIListener;

public class ShowLoginUI 
{
	// Prevent instantiation
	private ShowLoginUI() {}
	
	public static void call(final KrollProxy proxy, GSAPI gsAPI, Object args)
	{
		GSObject gsObj = Util.GSObjectFromArgument(args);
		
		gsAPI.showLoginUI(
			gsObj,
			new GSLoginUIListener() {
				public void onLogin(String provider, GSObject user, Object context)
				{
					if (proxy.hasListeners(Constants.LOGINUI_DID_LOGIN)) {
						KrollDict event = new KrollDict();
						event.put("provider", provider);
						event.put("user", Util.dataFromGSObject(user));
						
						proxy.fireEvent(Constants.LOGINUI_DID_LOGIN, event);
					}
				}
				
				public void onLoad(Object context)
				{
					if (proxy.hasListeners(Constants.LOGINUI_DID_LOAD)) {
						KrollDict event = new KrollDict();
						
						proxy.fireEvent(Constants.LOGINUI_DID_LOAD, event);
					}
				}
				
				public void onError(int errorCode, String errorMessage, String trace, Object context)
				{
					Log.d(Constants.LCAT, trace);
					
					if (proxy.hasListeners(Constants.LOGINUI_DID_FAIL)) {
						KrollDict event = new KrollDict();
						event.put("code", errorCode);
						event.put("message", errorMessage);
						
						proxy.fireEvent(Constants.LOGINUI_DID_FAIL, event);
					}
				}
				
				public void onClose(boolean canceled, Object context)
				{
					if (proxy.hasListeners(Constants.LOGINUI_DID_CLOSE)) {
						KrollDict event = new KrollDict();
						event.put("canceled", canceled);
		
						proxy.fireEvent(Constants.LOGINUI_DID_CLOSE, event);
					}
				}
			},
			null );
	}
}
