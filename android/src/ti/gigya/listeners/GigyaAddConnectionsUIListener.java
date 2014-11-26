/**
 * Ti.Gigya Module
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

package ti.gigya.listeners;

import org.appcelerator.kroll.KrollProxy;

import ti.gigya.Constants;

import com.gigya.socialize.GSObject;
import com.gigya.socialize.android.event.GSConnectUIListener;

import java.util.HashMap;

public class GigyaAddConnectionsUIListener extends GigyaUIListener implements GSConnectUIListener 
{
	public GigyaAddConnectionsUIListener(final KrollProxy proxy, final HashMap args)
	{
		super(proxy, args);
	}

	public void onConnectionAdded(String provider, GSObject user, Object context)
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
