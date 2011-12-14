/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package ti.gigya.listeners;

import org.appcelerator.kroll.KrollProxy;

import ti.gigya.Constants;

import com.gigya.socialize.GSResponse;
import com.gigya.socialize.GSResponseListener;
import java.util.HashMap;

public class GigyaResponseListener extends GigyaListener implements GSResponseListener 
{
	public GigyaResponseListener(final KrollProxy proxy, final HashMap args)
	{
		super(proxy, args);
	}
	
	public void onGSResponse(String method, GSResponse response, Object context)
	{
		if (response.getErrorCode() == 0) {
			handleSuccess(method, Constants.kMethod, response.getData());
		} else {
			handleError(response.getErrorCode(), response.getErrorMessage(), method);
		}
	}

}
