/**
 * Ti.Gigya Module
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

package ti.gigya.listeners;

import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.KrollFunction;

import ti.gigya.Constants;
import java.util.HashMap;

public class GigyaUIListener extends GigyaListener 
{
	private final KrollFunction _loadCallback;
	private final KrollFunction _closeCallback;
	private final KrollProxy _proxy;
	
	public GigyaUIListener(final KrollProxy proxy, final HashMap args)
	{
		super(proxy, args);
		
		_loadCallback = (KrollFunction)args.get(Constants.kLoad);
		_closeCallback = (KrollFunction)args.get(Constants.kClose);
		_proxy = proxy;
	}

	public void handleLoad()
	{
		if (_loadCallback != null) {
			_loadCallback.callAsync(_proxy.getKrollObject(), new Object[] {});
		}
	}
	
	public void handleClose(boolean canceled)
	{
		if (_closeCallback != null) {
			HashMap event = new HashMap();
			event.put(Constants.kCanceled, canceled);
			_closeCallback.callAsync(_proxy.getKrollObject(), event);
		}
	}
}
