/**
 * Ti.Gigya Module
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
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
