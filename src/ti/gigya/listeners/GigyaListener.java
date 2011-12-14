/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package ti.gigya.listeners;

import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.KrollFunction;

import ti.gigya.Constants;
import ti.gigya.Util;

import com.gigya.socialize.GSObject;

import java.util.HashMap;

public class GigyaListener 
{
	private final KrollFunction _successCallback;
	private final KrollFunction _errorCallback;
	private final KrollProxy _proxy;

	public GigyaListener(final KrollProxy proxy, final HashMap args)
	{
		_successCallback = (KrollFunction)args.get(Constants.kSuccess);
		_errorCallback = (KrollFunction)args.get(Constants.kError);
		_proxy = proxy;
	}

	public void handleSuccess(Object obj, String tag, GSObject data)
	{
		if (_successCallback != null) {
			HashMap event = new HashMap();
			event.put(tag, obj);
			event.put(Constants.kData, Util.dataFromGSObject(data));
			_successCallback.callAsync(_proxy.getKrollObject(), event);
		}
	}
	
	public void handleError(int errorCode, String errorMessage)
	{
		if (_errorCallback != null) {
			HashMap event = new HashMap();
			event.put(Constants.kErrorCode, errorCode);
			event.put(Constants.kErrorMessage, errorMessage);
			_errorCallback.callAsync(_proxy.getKrollObject(), event);
		}
	}
	
	public void handleError(int errorCode, String errorMessage, String method)
	{
		if (_errorCallback != null) {
			HashMap event = new HashMap();
			event.put(Constants.kMethod, method);
			event.put(Constants.kErrorCode, errorCode);
			event.put(Constants.kErrorMessage, errorMessage);
			_errorCallback.callAsync(_proxy.getKrollObject(), event);
		}
	}
}
