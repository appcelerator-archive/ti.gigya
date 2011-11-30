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

public class GigyaUIListener extends GigyaListener 
{
	private final KrollCallback _loadCallback;
	private final KrollCallback _closeCallback;
	
	public GigyaUIListener(final KrollProxy proxy, final KrollDict args)
	{
		super(proxy, args);
		
		_loadCallback = (KrollCallback)args.get(Constants.kLoad);
		_closeCallback = (KrollCallback)args.get(Constants.kClose);
	}

	public void handleLoad()
	{
		if (_loadCallback != null) {
			_proxy.fireSingleEvent(Constants.kLoad, _loadCallback, null, true);
		}
	}
	
	public void handleClose(boolean canceled)
	{
		if (_closeCallback != null) {
			KrollDict event = new KrollDict();
			event.put(Constants.kCanceled, canceled);
			_proxy.fireSingleEvent(Constants.kClose, _closeCallback, event, true);
		}
	}
}
