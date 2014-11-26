/**
 * Ti.Gigya Module
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

package ti.gigya.listeners;

import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.kroll.KrollDict;

import ti.gigya.Constants;
import ti.gigya.Util;

import com.gigya.socialize.GSObject;
import com.gigya.socialize.android.event.GSEventListener;
import org.appcelerator.kroll.common.Log;

import java.util.HashMap;

public class GigyaEventListener implements GSEventListener 
{
	private final KrollProxy _proxy;

	public GigyaEventListener(final KrollProxy proxy)
	{
		_proxy = proxy;
	}
	
	public void onLogin(String provider, GSObject user, Object context)
	{
		if (_proxy.hasListeners(Constants.kDidLogin)) {
			HashMap event = new HashMap();
			event.put(Constants.kProvider, provider);
			event.put(Constants.kData, Util.dataFromGSObject(user));

			_proxy.fireEvent (Constants.kDidLogin, event);
		}
	}
	
	public void onLogout(Object context)
	{
		if (_proxy.hasListeners(Constants.kDidLogout)) {
			HashMap event = new HashMap();

			_proxy.fireEvent (Constants.kDidLogout, event);
		}
	}
	
	public void onConnectionAdded(String provider, GSObject user, Object context) 
	{
		if (_proxy.hasListeners(Constants.kDidAddConnection)) {
			HashMap event = new HashMap();
			event.put(Constants.kProvider, provider);
			event.put(Constants.kData, Util.dataFromGSObject(user));
			
			_proxy.fireEvent (Constants.kDidAddConnection, event);
		}
	}
	
	public void onConnectionRemoved(String provider, Object context)
	{
		if (_proxy.hasListeners(Constants.kDidRemoveConnection)) {
			HashMap event = new HashMap();
			event.put(Constants.kProvider, provider);
			
			_proxy.fireEvent (Constants.kDidRemoveConnection, event);
		}
	}
}
