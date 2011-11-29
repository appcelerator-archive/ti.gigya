/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package ti.gigya;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollInvocation;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;

import org.appcelerator.titanium.TiContext;
import org.appcelerator.titanium.util.Log;
import org.appcelerator.titanium.util.TiConvert;

import com.gigya.socialize.android.GSAPI;

import ti.gigya.calls.Login;
import ti.gigya.calls.Logout;
import ti.gigya.calls.SendRequest;
import ti.gigya.calls.ShowAddConnectionsUI;
import ti.gigya.calls.ShowLoginUI;
import ti.gigya.calls.AddConnection;
import ti.gigya.calls.RemoveConnection;

@Kroll.module(name="Gigya", id="ti.gigya")
public class GigyaModule extends KrollModule
{
	public GigyaModule(TiContext tiContext) {
		super(tiContext);
	}

	// GSAPI singleton -- allocated on first use -- don't use static on objects in Android
	private GSAPI _gsAPI = null;
	public GSAPI getGSAPI(KrollInvocation invocation) 
	{
		if (_gsAPI == null) {
			String apiKey = TiConvert.toString(getProperty("apiKey"));
			if (apiKey.length() == 0) {
				Log.e(Constants.LCAT, "[ERROR] apiKey property is  not set");
				return null;
			}

			// NOTE (from the Gigya documentation):
			// "You should create only one GSAPI object and retain it for the lifetime of your application."
			_gsAPI = new GSAPI(apiKey, invocation.getActivity());
			_gsAPI.setEventListener(new GlobalEventListener(this));
		}
		return _gsAPI;
	}
	
/* ---------------------------------------------------------------------------------
   Gigya field support
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
	public void showLoginUI(KrollInvocation invocation, Object args)
	{
		ShowLoginUI.call(this, getGSAPI(invocation), args);
	}

/* ---------------------------------------------------------------------------------
   showAddConnectionsUI method
   --------------------------------------------------------------------------------- */

	@Kroll.method(runOnUiThread=true)
	public void showAddConnectionsUI(KrollInvocation invocation, Object args)
	{
		ShowAddConnectionsUI.call(this, getGSAPI(invocation), args);
	}

/* ---------------------------------------------------------------------------------
   login / logout methods
   --------------------------------------------------------------------------------- */
	
	@Kroll.method(runOnUiThread=true)
	public void login(KrollInvocation invocation, Object args)
	{
		Login.call(this, getGSAPI(invocation), args);
	}
	
	@Kroll.method(runOnUiThread=true)
	public void logout(KrollInvocation invocation)
	{
		Logout.call(this, getGSAPI(invocation), null);
	}
	
	@Kroll.getProperty @Kroll.method
	public boolean loggedIn(KrollInvocation invocation)
	{
		return getGSAPI(invocation).getSession() != null;
	}

/* ---------------------------------------------------------------------------------
   addConnection / removeConnection methods
   --------------------------------------------------------------------------------- */
	
	@Kroll.method(runOnUiThread=true)
	public void AddConnection(KrollInvocation invocation, Object args)
	{
		AddConnection.call(this, getGSAPI(invocation), args);
	}
	
	@Kroll.method(runOnUiThread=true)
	public void RemoveConnection(KrollInvocation invocation, Object args)
	{
		RemoveConnection.call(this, getGSAPI(invocation), args);
	}

/* ---------------------------------------------------------------------------------
   sendRequest method
   --------------------------------------------------------------------------------- */

	@Kroll.method(runOnUiThread=true)
	public void sendRequest(KrollInvocation invocation, KrollDict args)
	{
	    // NOTE: This must be called on the UI thread, even though it doesn't perform any UI
		SendRequest.call(this, getGSAPI(invocation), args);
	}

/* ---------------------------------------------------------------------------------
   Public Event Names
   --------------------------------------------------------------------------------- */
	@Kroll.constant public static final String LOGINUI_DID_LOGIN = Constants.LOGINUI_DID_LOGIN;
	@Kroll.constant public static final String LOGINUI_DID_CLOSE = Constants.LOGINUI_DID_CLOSE;
	@Kroll.constant public static final String LOGINUI_DID_FAIL = Constants.LOGINUI_DID_FAIL;
	@Kroll.constant public static final String LOGINUI_DID_LOAD = Constants.LOGINUI_DID_LOAD;
	@Kroll.constant public static final String ADDCONNECTIONSUI_DID_CONNECT = Constants.ADDCONNECTIONSUI_DID_CONNECT;
	@Kroll.constant public static final String ADDCONNECTIONSUI_DID_CLOSE = Constants.ADDCONNECTIONSUI_DID_CLOSE;
	@Kroll.constant public static final String ADDCONNECTIONSUI_DID_FAIL = Constants.ADDCONNECTIONSUI_DID_FAIL;
	@Kroll.constant public static final String ADDCONNECTIONSUI_DID_LOAD = Constants.ADDCONNECTIONSUI_DID_LOAD;
	@Kroll.constant public static final String RESPONSE = Constants.RESPONSE;
	@Kroll.constant public static final String DID_LOGIN = Constants.DID_LOGIN;
	@Kroll.constant public static final String DID_LOGOUT = Constants.DID_LOGOUT;
	@Kroll.constant public static final String DID_ADD_CONNECTION = Constants.DID_ADD_CONNECTION;
	@Kroll.constant public static final String DID_REMOVE_CONNECTION = Constants.DID_REMOVE_CONNECTION;
}
