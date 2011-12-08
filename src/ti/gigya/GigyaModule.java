/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package ti.gigya;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;

import org.appcelerator.titanium.util.Log;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.TiApplication;

import android.app.Activity;

import com.gigya.socialize.GSObject;
import com.gigya.socialize.android.GSAPI;

import ti.gigya.listeners.GigyaAddConnectionsUIListener;
import ti.gigya.listeners.GigyaEventListener;
import ti.gigya.listeners.GigyaLoginUIListener;
import ti.gigya.listeners.GigyaResponseListener;

@Kroll.module(name="Gigya", id="ti.gigya")
public class GigyaModule extends KrollModule
{
	// Standard Debugging variables
	private static final String LCAT = "GigyaModule";

	public GigyaModule() {
		super();
	}

	// GSAPI singleton -- allocated on first use -- don't use static on objects in Android
	private GSAPI _gsAPI = null;
	public synchronized GSAPI getGSAPI()
	{
		if (_gsAPI == null) {
				if ((apiKey == null) || (apiKey.length() == 0)) {
				Log.e(Constants.LCAT, "[ERROR] apiKey property is  not set");
				return null;
			}

			// NOTE (from the Gigya documentation):
			// "You should create only one GSAPI object and retain it for the lifetime of your application."
			_gsAPI = new GSAPI(apiKey, TiApplication.getAppRootOrCurrentActivity());
			_gsAPI.setEventListener(new GigyaEventListener(this));
		}

		return _gsAPI;
	}
	
	@Override
	public void onDestroy(Activity activity)
	{
		if (_gsAPI != null) {
			_gsAPI.logout();
		}
		
		super.onDestroy(activity);
	}

    // NOTE: Do ** NOT ** use getProperty from a proxy / module For some reason it won't get the
    // property that is set by the JS.

	private String apiKey = null;

	@Kroll.getProperty
	public String getApiKey() {
		return apiKey;
	}

    @Kroll.setProperty
	public void setApiKey(String value) {
	    apiKey = value;
	}

/* ---------------------------------------------------------------------------------
   Gigya static properties
--------------------------------------------------------------------------------- */
	
	@Kroll.getProperty
	public boolean getOption_Trace()
	{
		return GSAPI.OPTION_TRACE;
	}
	
	@Kroll.setProperty
	public void setOption_Trace(boolean value)
	{
		GSAPI.OPTION_TRACE = value;
	}
	
	@Kroll.getProperty
	public boolean getOption_ShowProgressOnRequest()
	{
		return GSAPI.OPTION_SHOW_PROGRESS_ON_REQUEST;
	}
	
	@Kroll.setProperty
	public void setOption_ShowProgressOnRequest(boolean value)
	{
		GSAPI.OPTION_SHOW_PROGRESS_ON_REQUEST = value;
	}
	
	@Kroll.getProperty
	public boolean getOption_ShowProgressOnNavigation()
	{
		return GSAPI.OPTION_SHOW_PROGRESS_ON_NAVIGATION;
	}
	
	@Kroll.setProperty
	public void setOption_ShowProgressOnNavigation(boolean value)
	{
		GSAPI.OPTION_SHOW_PROGRESS_ON_NAVIGATION = value;
	}
	
	@Kroll.getProperty
	public boolean getOption_CheckConnectivity()
	{
		return GSAPI.OPTION_CHECK_CONNECTIVITY;
	}
	
	@Kroll.setProperty
	public void setOption_CheckConnectivity(boolean value)
	{
		GSAPI.OPTION_CHECK_CONNECTIVITY = value;
	}
	
/* ---------------------------------------------------------------------------------
   showLoginUI method
   --------------------------------------------------------------------------------- */
	
	@Kroll.method(runOnUiThread=true)
	public void showLoginUI(KrollDict args)
	{
		GSAPI gsAPI = getGSAPI();
		GSObject gsObj = Util.GSObjectFromArgument(args.getKrollDict(Constants.kParams));
		
		try {
			gsAPI.showLoginUI(gsObj, new GigyaLoginUIListener(this, args), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

/* ---------------------------------------------------------------------------------
   showAddConnectionsUI method
   --------------------------------------------------------------------------------- */

	@Kroll.method(runOnUiThread=true)
	public void showAddConnectionsUI(KrollDict args)
	{
		GSAPI gsAPI = getGSAPI();
		GSObject gsObj = Util.GSObjectFromArgument(args.getKrollDict(Constants.kParams));

		try {
			gsAPI.showAddConnectionsUI(gsObj, new GigyaAddConnectionsUIListener(this, args), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

/* ---------------------------------------------------------------------------------
   login / logout methods
   --------------------------------------------------------------------------------- */
	
	@Kroll.method(runOnUiThread=true)
	public void login(KrollDict args)
	{
		GSAPI gsAPI = getGSAPI();
		GSObject gsObj = Util.GSObjectFromArgument(args.getKrollDict(Constants.kParams));
		
		try {
			gsAPI.login(gsObj, new GigyaResponseListener(this, args), null);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@Kroll.getProperty
	public boolean loggedIn()
	{
		return getGSAPI().getSession() != null;
	}
	
	@Kroll.method(runOnUiThread=true)
	public void logout()
	{
		GSAPI gsAPI = getGSAPI();
		
		try {
			gsAPI.logout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

/* ---------------------------------------------------------------------------------
   addConnection / removeConnection methods
   --------------------------------------------------------------------------------- */
	
	@Kroll.method(runOnUiThread=true)
	public void addConnection(KrollDict args)
	{
		GSAPI gsAPI = getGSAPI();
		GSObject gsObj = Util.GSObjectFromArgument(args.getKrollDict(Constants.kParams));
		
		try {
			gsAPI.addConnection(gsObj, new GigyaResponseListener(this, args), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Kroll.method(runOnUiThread=true)
	public void removeConnection(KrollDict args)
	{
		GSAPI gsAPI = getGSAPI();
		GSObject gsObj = Util.GSObjectFromArgument(args.getKrollDict(Constants.kParams));
		
		try {
			// NOTE: This method name is misspelled in the Gigya SDK
			gsAPI.removeConnetion(gsObj, new GigyaResponseListener(this, args), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

/* ---------------------------------------------------------------------------------
   sendRequest method
   --------------------------------------------------------------------------------- */

	@Kroll.method(runOnUiThread=true)
	public void sendRequest(KrollDict args)
	{
	    // NOTE: This must be called on the UI thread, even though it doesn't perform any UI
		GSAPI gsAPI = getGSAPI();
		GSObject gsObj = Util.GSObjectFromArgument(args.getKrollDict(Constants.kParams));
		
		String method = args.getString(Constants.kMethod);
		boolean useHTTPS = args.optBoolean(Constants.kUseHTTPS, false);
		
		try {
			gsAPI.sendRequest(method, gsObj, useHTTPS, new GigyaResponseListener(this, args), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

/* ---------------------------------------------------------------------------------
   Public Event Names
   --------------------------------------------------------------------------------- */

	@Kroll.constant public static final String DID_LOGIN = Constants.kDidLogin;
	@Kroll.constant public static final String DID_LOGOUT = Constants.kDidLogout;
	@Kroll.constant public static final String DID_ADD_CONNECTION = Constants.kDidAddConnection;
	@Kroll.constant public static final String DID_REMOVE_CONNECTION = Constants.kDidRemoveConnection;
}
