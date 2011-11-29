/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package ti.gigya;

import org.appcelerator.kroll.annotations.Kroll;

public class Constants {
	public static final String LCAT = "GigyaModule";
	
	// showLoginUI
	public static final String LOGINUI_DID_LOGIN = "loginui_did_login";
	public static final String LOGINUI_DID_CLOSE = "loginui_did_close";
	public static final String LOGINUI_DID_FAIL = "loginui_did_fail";
	public static final String LOGINUI_DID_LOAD = "loginui_did_load";
	
	// showAddConnectionsUI
	public static final String ADDCONNECTIONSUI_DID_CONNECT = "addconnectionsui_did_connect";
	public static final String ADDCONNECTIONSUI_DID_CLOSE = "addconnectionsui_did_close";
	public static final String ADDCONNECTIONSUI_DID_FAIL = "addconnectionsui_did_fail";
	public static final String ADDCONNECTIONSUI_DID_LOAD = "addconnectionsui_did_load";
	
	// sendRequest
	public static final String RESPONSE = "response";
	
	// global events
	public static final String DID_LOGIN = "did_login";
	public static final String DID_LOGOUT = "did_logout";
	public static final String DID_ADD_CONNECTION = "did_add_connection";
	public static final String DID_REMOVE_CONNECTION = "did_remove_connection";
}
