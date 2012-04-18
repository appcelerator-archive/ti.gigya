/*
 This example will show you the basics of interacting with Gigya through Titanium Mobile.

 Gigya's workflow goes like this:

 1) Log in to at least one social site.
 2) Send requests to Gigya in order to get or set status, photos, friends, etc.
 3) Log out.

 We'll demonstrate this with a very simple UI, 3 part UI:

 1) Up top, the user can log in and out.
 2) Right below that, we'll give the user a text box to update their status.
 3) The rest will be a list of the user's friends.

 Let's get to it!
 */

var Gigya = require('ti.gigya');
Gigya.apiKey = '<<< YOUR_API_KEY >>>';

var u = Ti.Android != undefined ? 'dp' : 0;

var win = Ti.UI.createWindow({
    backgroundColor: 'white'
});

/*
 1) Logging in, adding connections, and logging out.
 */
var loginButton = Ti.UI.createLabel({
    text: 'Login',
    top: 5 + u, right: 5 + u, left: 5 + u,
    height: 38 + u,
    textAlign: 'center', font: { fontWeight: 'bold', fontSize: 14 },
    color: '#fff', shadowColor: '#000', shadowOffset: { x: 0, y: -1 },
    backgroundImage: 'button-positive.png', backgroundLeftCap: 11 + u
});
loginButton.addEventListener('click', function (e) {
    Gigya.showLoginUI({
		params: {
			//enabledProviders: 'twitter, yahoo',
			captionText: 'Login',
			forceAuthentication: true
		},
		// The following callbacks can be declared to get notified of specific 
		// events for the showLoginUI call. They are optional parameters.
		success: function (evt) {
			Ti.API.info("Success");
			Ti.API.info(JSON.stringify(evt));
		},
		error: function (evt) {
			Ti.API.info("Error");
			Ti.API.info(JSON.stringify(evt));
		},
		load: function(evt) {
			Ti.API.info("Load");
			Ti.API.info(JSON.stringify(evt));
		},
		close: function(evt) {
			Ti.API.info("Close");
			Ti.API.info(JSON.stringify(evt));
		}    
	});
    /*
     Note: if you want the user to log in to a particular site, you can use the simpler "login" method like this:
     Gigya.login({
			params: { provider: 'twitter' }
		});
     */
});

var addConnectionsButton = Ti.UI.createLabel({
    text: 'Add Connections',
    top: 5 + u, right: 100 + u, left: 5 + u,
    height: 38 + u,
    textAlign: 'center', font: { fontWeight: 'bold', fontSize: 14 },
    color: '#fff', shadowColor: '#000', shadowOffset: { x: 0, y: -1 },
    backgroundImage: 'button-positive.png', backgroundLeftCap: 11 + u
});
addConnectionsButton.addEventListener('click', function (e) {
    Gigya.showAddConnectionsUI({
		params: {
			disabledProviders: 'myspace',
			captionText: 'Add Connections'
		},
		// The following callbacks can be declared to get notified of specific 
		// events for the showAddConnectionsUI call. They are optional parameters.
		success: function (evt) {
			Ti.API.info("Success");
			Ti.API.info(JSON.stringify(evt));
			// Updating the friends list is handled in the global event listener below
		},
		error: function (evt) {
			Ti.API.info("Error");
			Ti.API.info(JSON.stringify(evt));
		},
		load: function(evt) {
			Ti.API.info("Load");
			Ti.API.info(JSON.stringify(evt));
		},
		close: function(evt) {
			Ti.API.info("Close");
			Ti.API.info(JSON.stringify(evt));
		}    
    });
});

var logoutButton = Ti.UI.createLabel({
    text: 'Logout',
    top: 5 + u, right: 5 + u,
    width: 90 + u, height: 38 + u,
    textAlign: 'center', font: { fontWeight: 'bold', fontSize: 14 },
    color: '#fff', shadowColor: '#000', shadowOffset: { x: 0, y: -1 },
    backgroundImage: 'button-negative.png', backgroundLeftCap: 11 + u
});
logoutButton.addEventListener('click', function (e) {
    Gigya.logout();
});

/*
 2) Updating the user's status.
 */
var statusTextField = Ti.UI.createTextField({
    hintText: 'Update Your Status',
    height: 40 + u,
    top: 48 + u, right: 5 + u, left: 5 + u,
    borderStyle: Ti.UI.INPUT_BORDERSTYLE_ROUNDED,
    appearance: Ti.UI.KEYBOARD_APPEARANCE_ALERT,
    returnKeyType: Ti.UI.RETURNKEY_SEND
});
statusTextField.addEventListener('return', function (evt) {
    statusTextField.blur();
    if (statusTextField.value.length == 0) {
        return;
    }
    Gigya.sendRequest({
        method: 'socialize.setStatus',
        params: {
            status: statusTextField.value
        },
        useHTTPS: false,
		// The following callbacks can be declared to get notified of specific 
		// events for the sendRequest call. They are optional parameters.
		success: function (evt) {
			Ti.API.info("Success");
			Ti.API.info(JSON.stringify(evt));
			if (evt.data.providerPostIDs) {
				alert('Status Set!');
			}
		},
		error: function (evt) {
			Ti.API.info("Error");
			Ti.API.info(JSON.stringify(evt));
		}
    });
    statusTextField.value = '';
});

/*
 3) A list of the user's friends.
 */
function requestFriends() {
    Gigya.sendRequest({
        method: 'socialize.getFriendsInfo',
        params: {
            detailLevel: 'basic'
        },
        useHTTPS: false,
		// The following callbacks can be declared to get notified of specific 
		// events for the sendRequest call. They are optional parameters.
		success: function (evt) {
			Ti.API.info("Success");
			Ti.API.info(JSON.stringify(evt));
			if (evt.data.friends) {
				populateFriends(evt.data.friends);
			}
		},
		error: function (evt) {
			Ti.API.info("Error");
			Ti.API.info(JSON.stringify(evt));
		}
    });
}
function populateFriends(rawFriends) {
    Ti.API.info(rawFriends);
    var friends = [];
    for (var i = 0; i < rawFriends.length; i++) {
        var friend = rawFriends[i];
        var friendView = Ti.UI.createTableViewRow({
            hasChild: true, data: friend
        });
        friendView.add(Ti.UI.createImageView({
            image: friend.thumbnailURL,
            width: 48 + u, height: 48 + u,
            left: 5 + u, top: 5 + u
        }));
        friendView.add(Ti.UI.createLabel({
            text: friend.nickname,
            top: 5 + u, left: 58 + u,
            height: 22 + u,
            textAlign: 'left', font: { fontWeight: 'bold', fontSize: 18 }
        }));
        var identities = 'Identities: ';
        for (var j = 0; j < friend.identities.length; j++) {
            identities += friend.identities[j].provider.charAt(0).toUpperCase() + friend.identities[j].provider.slice(1) + ', ';
        }
        friendView.add(Ti.UI.createLabel({
            text: identities.substr(0, identities.length - 2),
            top: 32 + u, left: 58 + u,
            height: 16 + u,
            textAlign: 'left', font: { fontSize: 14 }
        }));
        friends.push(friendView);
    }
    friendsTable.setData(friends);
}
var friendsTable = Ti.UI.createTableView({
    top: 93 + u, rowHeight: 58 + u
});
friendsTable.addEventListener('click', function (evt) {
    if (evt.row && evt.row.hasChild) {
        alert(evt.row.data);
    }
});

/*
 Based on if the user is logged in or not, we want to show different buttons and UI to them.
 */
function showLoggedOutButtons() {
    win.add(loginButton);
    win.remove(addConnectionsButton);
    win.remove(logoutButton);
    win.remove(statusTextField);
    win.remove(friendsTable);
}

function showLoggedInButtons() {
    win.remove(loginButton);
    win.add(addConnectionsButton);
    win.add(logoutButton);
    win.add(statusTextField);
    win.add(friendsTable);
    requestFriends();
}

/* 
 Global events can be set on the module.
*/
Gigya.addEventListener(Gigya.DID_LOGIN, showLoggedInButtons);
Gigya.addEventListener(Gigya.DID_LOGOUT, showLoggedOutButtons);
Gigya.addEventListener(Gigya.DID_ADD_CONNECTION, requestFriends);
Gigya.addEventListener(Gigya.DID_REMOVE_CONNECTION, requestFriends);

if (Gigya.loggedIn) {
    showLoggedInButtons();
}
else {
    showLoggedOutButtons();
}

/*
 That's it! Check out our documentation and Gigya's website to find out more.
 http://www.gigya.com/mobile/
 */
win.open();