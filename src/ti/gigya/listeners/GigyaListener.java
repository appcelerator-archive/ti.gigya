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
import org.appcelerator.titanium.kroll.KrollCallback;

import ti.gigya.Constants;
import ti.gigya.Util;

import com.gigya.socialize.GSObject;

public class GigyaListener 
{
	private final KrollCallback _successCallback;
	private final KrollCallback _errorCallback;
	protected final KrollProxy _proxy;
	
	public GigyaListener(final KrollProxy proxy, final KrollDict args)
	{
		_successCallback = (KrollCallback)args.get(Constants.kSuccess);
		_errorCallback = (KrollCallback)args.get(Constants.kError);
		_proxy = proxy;
	}

	public void handleSuccess(Object obj, String tag, GSObject data)
	{
		if (_successCallback != null) {
			KrollDict event = new KrollDict();
			event.put(tag, obj);
			event.put(Constants.kData, Util.dataFromGSObject(data));
			_proxy.fireSingleEvent(Constants.kSuccess, _successCallback, event, true);
		}
	}
	
	public void handleError(int errorCode, String errorMessage)
	{
		if (_errorCallback != null) {
			KrollDict event = new KrollDict();
			event.put(Constants.kErrorCode, errorCode);
			event.put(Constants.kErrorMessage, errorMessage);
			_proxy.fireSingleEvent(Constants.kError, _errorCallback, event, true);
		}
	}
	
	public void handleError(int errorCode, String errorMessage, String method)
	{
		if (_errorCallback != null) {
			KrollDict event = new KrollDict();
			event.put(Constants.kMethod, method);
			event.put(Constants.kErrorCode, errorCode);
			event.put(Constants.kErrorMessage, errorMessage);
			_proxy.fireSingleEvent(Constants.kError, _errorCallback, event, true);
		}
	}
}
