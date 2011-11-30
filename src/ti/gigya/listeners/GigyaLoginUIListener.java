/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package ti.gigya.listeners;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;

import ti.gigya.Constants;

import com.gigya.socialize.GSObject;
import com.gigya.socialize.android.event.GSLoginUIListener;

public class GigyaLoginUIListener extends GigyaUIListener implements GSLoginUIListener 
{
	public GigyaLoginUIListener(final KrollProxy proxy, final KrollDict args)
	{
		super(proxy, args);
	}
	
	public void onLogin(String provider, GSObject user, Object context)
	{
		handleSuccess(provider, Constants.kProvider, user);
	}
	
	public void onLoad(Object context)
	{
		handleLoad();
	}
	
	public void onError(int errorCode, String errorMessage, String trace, Object context)
	{
		handleError(errorCode, errorMessage);
	}
	
	public void onClose(boolean canceled, Object context)
	{
		handleClose(canceled);
	}
}
