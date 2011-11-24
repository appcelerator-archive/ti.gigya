// This is a test harness for your module
// You should do something interesting in this harness 
// to test out the module and to provide instructions 
// to users on how to use it by example.


// open a single window
var window = Ti.UI.createWindow({
	backgroundColor:'white',
	layout: 'vertical'
});
var label = Ti.UI.createLabel();
window.add(label);
window.open();

function showEvent(e) 
{
	Ti.API.info(JSON.stringify(e));
}

function showResponse(e)
{
    Ti.API.info("Method: " + e.method);
	Ti.API.info("Error code: " + e.errorCode);
	Ti.API.info("Error message: " + e.errorMessage);
	Ti.API.info("Response Text: " + e.responseText);
	Ti.API.info("Data: " + JSON.stringify(e.data));
}

// TODO: write your module tests here
var gigya = require('ti.gigya');
Ti.API.info("module is => " + gigya);

// gigya.apiKey = << YOUR_API_KEY >>>
gigya.apiKey = "2_CKUNP-SKP_62hnPDaka5WPym4t3LltDtz2OgwU_KnmdnxL1VQAYkZ9tcKbYhNlIN";

gigya.addEventListener(gigya.DID_LOGIN, showEvent);
gigya.addEventListener(gigya.DID_LOGOUT, showEvent);
gigya.addEventListener(gigya.DID_ADD_CONNECTION, showEvent);
gigya.addEventListener(gigya.DID_REMOVE_CONNECTION, showEvent);

var loginBtn = Ti.UI.createButton({
	title: 'Login',
	width: 200,
	height: 40,
	top: 4, left: 4
});
window.add(loginBtn);

loginBtn.addEventListener('click', function(e){
	//gigya.showLoginUI(JSON.stringify(options));
	gigya.showLoginUI({
		enabledProviders: "facebook, twitter, yahoo",
		captionText: "My Connections",
		forceAuthentication: true
	});
});
gigya.addEventListener(gigya.LOGINUI_DID_LOGIN, showEvent);
gigya.addEventListener(gigya.LOGINUI_DID_CLOSE, showEvent);
gigya.addEventListener(gigya.LOGINUI_DID_FAIL, showEvent);
gigya.addEventListener(gigya.LOGINUI_DID_LOAD, showEvent);

var addConnectionsBtn = Ti.UI.createButton({
	title: 'Add Connections',
	width: 200,
	height: 40,
	top: 4, left: 4
});
window.add(addConnectionsBtn);

addConnectionsBtn.addEventListener('click', function(e){
	gigya.showAddConnectionsUI({
		disabledProviders: 'myspace',
		captionText: "Add My Connection"
	});
});
gigya.addEventListener(gigya.ADDCONNECTIONSUI_DID_CONNECT, showEvent);
gigya.addEventListener(gigya.ADDCONNECTIONSUI_DID_CLOSE, showEvent);
gigya.addEventListener(gigya.ADDCONNECTIONSUI_DID_FAIL, showEvent);
gigya.addEventListener(gigya.ADDCONNECTIONSUI_DID_LOAD, showEvent);

var sendRequestBtn = Ti.UI.createButton({
	title: 'sendRequest',
	width: 200,
	height: 40,
	top: 4, left: 4
});
window.add(sendRequestBtn);

sendRequestBtn.addEventListener('click', function(e){
	gigya.sendRequest({
		method: 'socialize.getFriendsInfo',
		//method: 'socialize.setStatus',
		params: {
			//status: 'Test status sent from the Gigya module'
			detailLevel: "basic"
		},
		useHTTPS: false
	});
})
gigya.addEventListener(gigya.RESPONSE, showResponse);

var loginToTwitterBtn = Ti.UI.createButton({
	title: 'Login to Twitter',
	width: 300,
	height: 40,
	top: 4, left: 4
});
window.add(loginToTwitterBtn);

loginToTwitterBtn.addEventListener('click', function(e){
	gigya.login({
		provider: 'twitter'
	});
});

var logoutBtn = Ti.UI.createButton({
	title: 'Logout',
	width: 300,
	height: 40,
	top: 4, left: 4
});
window.add(logoutBtn);

logoutBtn.addEventListener('click', function(e){
	gigya.logout();
});


