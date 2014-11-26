# Gigya Module

## Description

The Gigya service supports a wide range of social login, sharing and community features throughout your site.

Note that your application must have a unique Gigya API key. To retrieve this key, go to the Site Dashboard page on Gigya's website. Follow the instructions in Gigya's Setup guide to setup your application and obtain a Gigya API-Key.

**Important**: please make sure to check the Enable Mobile or Desktop Client Applications API Access checkbox under the 'Advanced' section in the Site Settings page.
 
After you have retrieved you application's API key, store it in the _Gigya.apiKey_ property. 

## Getting Started

View the [Using Titanium Modules](http://docs.appcelerator.com/titanium/latest/#!/guide/Using_Titanium_Modules) document for instructions on getting
started with using this module in your application.

## Accessing the Gigya Module

To access this module from JavaScript, you would do the following:

	var Gigya = require("ti.gigya");

The Gigya variable is a reference to the Module object.	

## Methods

### void showLoginUI(args)
Displays a "Login" UI for selecting a social network to login to. The UI includes all the available providers' logos as login options, enabling the user to login via his social network / webmail account. Args is a dictionary with the following:

* dictionary params [optional]: Fields as defined in the [Gigya showLoginUI documentation][Gigya.showLoginUI]
* function success(evt) [optional]: A callback to be executed after a successful request. Evt is a dictionary with the following:
    * provider[string]: The name of the provider used to login
    * data[dictionary]: Information about the user.  The properties are equivalent to those defined in the [Gigya User Object documentation][Gigya.User_object] 
* function error(evt) [optional]: A callback to be executed when an error occurs. Evt is a dictionary with the following:
    * errorCode[integer]: The error code as defined in the [Gigya Response Codes and Errors documentation][Gigya.errorCodes]
    * errorMessage[string]: The error message
* function load() [optional]: A callback to be executed when the network selection page is shown
* function close(evt) [optional]: A callback to be executed when the login UI is closed (for any reason - canceled, error, operation completed OK). Evt is a dictionary with the following:
    * canceled[boolean]: Indicates if the login UI closed due to a cancel request

### void showAddConnectionsUI(args)
Displays an "Add Connections" UI, which enables establishing connections to social networks. The UI presents the available social network icons as connect options. Args is a dictionary with the following:

* dictionary params [optional]: Fields as defined in the [Gigya showAddConnectionsUI documentation][Gigya.showAddConnectionsUI]
* function success(evt) [optional]: A callback to be executed after a successful request. Evt is a dictionary with the following:
    * provider[string]: The name of the provider added
    * data[dictionary]: Information about the user.  The properties are equivalent to those defined in the [Gigya User Object documentation][Gigya.User_object] 
* function error(evt) [optional]: A callback to be executed when an error occurs. Evt is a dictionary with the following:
    * errorCode[integer]: The error code as defined in the [Gigya Response Codes and Errors documentation][Gigya.errorCodes]
    * errorMessage[string]: The error message
* function load() [optional]: A callback to be executed when the add connections selection page is shown
* function close(evt) [optional]: A callback to be executed when the add connections UI is closed (for any reason - canceled, error, operation completed OK). Evt is a dictionary with the following:
    * canceled[boolean]: Indicates if the login UI closed due to a cancel request

### void login(args)
Login the user to a specified provider. Opens a WebDialog. The provider name is passed via the params. Args is a dictionary with the following:

* dictionary params [optional]: Fields as defined in the [Gigya login documentation][Gigya.login]
* function success(evt) [optional]: A callback to be executed after a successful request. Evt is a dictionary with the following:
    * method[string]: The name of the method
    * data[dictionary]: Information about the user.  The properties are equivalent to those defined in the [Gigya User Object documentation][Gigya.User_object] 
* function error(evt) [optional]: A callback to be executed when an error occurs. Evt is a dictionary with the following:
    * errorCode[integer]: The error code as defined in the [Gigya Response Codes and Errors documentation][Gigya.errorCodes]
    * errorMessage[string]: The error message

### void logout()
This method Logs out the current user of Gigya Platform. The method also clears WebDialog cookies and session. We highly recommend calling this method when the user logs out of the hosting site.

### void addConnection(args)
Add a Social Network connection to the current user. Technically speaking, a connection is an established session with the social network and it expires according to the social network policy. A valid and active connection will give your application access to the user's social graph and ability to perform various social actions, such as publishing a newsfeed report to the connected social network. Args is a dictionary with the following:

* dictionary params [optional]: Fields as defined in the [Gigya addConnection documentation][Gigya.addConnection]
* function success(evt) [optional]: A callback to be executed after a successful request. Evt is a dictionary with the following:
    * method[string]: The name of the method
    * data[dictionary]: Information about the user.  The properties are equivalent to those defined in the [Gigya User Object documentation][Gigya.User_object] 
* function error(evt) [optional]: A callback to be executed when an error occurs. Evt is a dictionary with the following:
    * errorCode[integer]: The error code as defined in the [Gigya Response Codes and Errors documentation][Gigya.errorCodes]
    * errorMessage[string]: The error message

### void removeConnection(args)
Removes an existing Social Network connection from the current user (the provider is specified via params). Args is a dictionary with the following:

* dictionary params [optional]: Fields as defined in the [Gigya removeConnection documentation][Gigya.removeConnection]
* function success(evt) [optional]: A callback to be executed after a successful request. Evt is a dictionary with the following:
    * method[string]: The name of the method
    * data[dictionary]: Information about the user.  The properties are equivalent to those defined in the [Gigya User Object documentation][Gigya.User_object] 
* function error(evt) [optional]: A callback to be executed when an error occurs. Evt is a dictionary with the following:
    * errorCode[integer]: The error code as defined in the [Gigya Response Codes and Errors documentation][Gigya.errorCodes]
    * errorMessage[string]: The error message

### void sendRequest(args)
Sends a request to Gigya server. This method is used for invoking any of the methods supported by [Gigya's REST API][Gigya.RESTAPI].

* string method [required]: The Gigya API method to call, including namespace. For example: "socialize.getUserInfo". Please refer to the [Gigya REST API reference][Gigya.RESTAPI] for the list of available methods.
* boolean useHTTPS [optional]: Determines whether the request to Gigya will be sent over HTTP or HTTPS. To send of HTTPS, please set this parameter to true. (default: false)
* dictionary params [optional]: Fields as defined in the [Gigya REST API reference][Gigya.RESTAPI], specific to the method that is called
* function success(evt) [optional]: A callback to be executed after a successful request. Evt is a dictionary with the following:
    * method[string]: The name of the method
    * data[dictionary]: Gigya's response
* function error(evt) [optional]: A callback to be executed when an error occurs. Evt is a dictionary with the following:
    * errorCode[integer]: The error code as defined in the [Gigya Response Codes and Errors documentation][Gigya.errorCodes]
    * errorMessage[string]: The error message

## Properties

### string apiKey
The API Key for your application, which can be retrieved from the Site Dashboard page on Gigya's website.

### boolean loggedIn
Indicates if there is a currently logged in Gigya session

## Events

Use Ti.Gigya.addEventListener() to process the following events that are sent from the module:

### DID_LOGIN

Fired on a successful login, either by the showLoginUI or login method. Event dictionary is:

provider[string]: The name of the provider used to login
data[dictionary]: Information about the user.  The properties are equivalent to those defined in the [Gigya User Object documentation][Gigya.User_object]

### DID_LOGOUT

Fired on a successful logout

### DID_ADD_CONNECTION

Fired when a connection is added, either by the showAddConnectionsUI or addConnection method. Event dictionary is:

provider[string]: The name of the provider added
data[dictionary]: Information about the user.  The properties are equivalent to those defined in the [Gigya User Object documentation][Gigya.User_object]

### DID_REMOVE_CONNECTION

Fired when a connection is removed. Event dictionary is:

provider[string]; The name of the provider removed

## Constants

### Gigya.DID_LOGIN
### Gigya.DID_LOGOUT
### Gigya.DID_ADD_CONNECTION
### Gigya.DID_REMOVE_CONNECTION

## Usage

See example

## Author

Jeff English

## Module History

View the [change log](changelog.html) for this module.

## Feedback and Support

Please direct all questions, feedback, and concerns to [info@appcelerator.com](mailto:info@appcelerator.com?subject=iOS%20Gigya%20Module).

## License
Copyright(c) 2010-2013 by Appcelerator, Inc. All Rights Reserved. Please see the LICENSE file included in the distribution for further details.

[Gigya.showLoginUI]: http://developers.gigya.com/032_SDKs/iPhone/Reference/Class_GSAPI#showLoginUI
[Gigya.showAddConnectionsUI]: http://developers.gigya.com/032_SDKs/iPhone/Reference/Class_GSAPI#showAddConnectionsUI
[Gigya.login]: http://developers.gigya.com/032_SDKs/iPhone/Reference/Class_GSAPI#login
[Gigya.logout]: http://developers.gigya.com/032_SDKs/iPhone/Reference/Class_GSAPI#logout
[Gigya.addConnection]: http://developers.gigya.com/032_SDKs/iPhone/Reference/Class_GSAPI#addConnection
[Gigya.removeConnection]: http://developers.gigya.com/032_SDKs/iPhone/Reference/Class_GSAPI#removeConnection

[Gigya.User_object]: http://developers.gigya.com/030_Gigya_Socialize_API_2.0/030_API_reference/Objects/User_object
[Gigya.errorCodes]: http://developers.gigya.com/037_API_reference/030_Response_Codes_and_Errors
[Gigya.RESTAPI]: http://developers.gigya.com/037_API_reference/020_REST_API
