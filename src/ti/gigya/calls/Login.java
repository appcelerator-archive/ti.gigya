/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package ti.gigya.calls;

import org.appcelerator.kroll.KrollProxy;

import ti.gigya.Util;

import com.gigya.socialize.GSObject;
import com.gigya.socialize.android.GSAPI;

public class Login 
{
	// Prevent instantiation
	private Login() {}

	public static void call(final KrollProxy proxy, GSAPI gsAPI, Object args)
	{
		try {
			GSObject gsObj = Util.GSObjectFromArgument(args);
			
			gsAPI.login(gsObj, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
