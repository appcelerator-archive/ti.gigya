package ti.gigya.listeners;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.titanium.kroll.KrollCallback;

import ti.gigya.Constants;
import ti.gigya.Util;

import com.gigya.socialize.GSObject;
import com.gigya.socialize.android.event.GSConnectUIListener;

public class ConnectUIListener implements GSConnectUIListener 
{
	private final KrollCallback _success;
	private final KrollCallback _error;
	private final KrollCallback _load;
	private final KrollCallback _close;
	private final KrollProxy _proxy;
	
	public ConnectUIListener(final KrollProxy proxy, final KrollDict args)
	{
		_success = (KrollCallback)args.get(Constants.kSuccess);
		_error = (KrollCallback)args.get(Constants.kError);
		_load = (KrollCallback)args.get(Constants.kLoad);
		_close = (KrollCallback)args.get(Constants.kClose);
		_proxy = proxy;
	}

	public void onConnectionAdded(String provider, GSObject user, Object context)
	{
		if (_success != null) {
			KrollDict event = new KrollDict();
			event.put(Constants.kProvider, provider);
			event.put(Constants.kData, Util.dataFromGSObject(user));
			_proxy.fireSingleEvent(Constants.kSuccess, _success, event, true);
		}
	}
	
	public void onLoad(Object context)
	{
		if (_load != null) {
			_proxy.fireSingleEvent(Constants.kLoad, _load, null, true);
		}
	}
	
	public void onError(int errorCode, String errorMessage, String trace, Object context)
	{
		if (_error != null) {
			KrollDict event = new KrollDict();
			event.put(Constants.kErrorCode, errorCode);
			event.put(Constants.kErrorMessage, errorMessage);
			_proxy.fireSingleEvent(Constants.kError, _error, event, true);
		}
	}
	
	public void onClose(boolean canceled, Object context)
	{
		if (_close != null) {
			KrollDict event = new KrollDict();
			event.put(Constants.kCanceled, canceled);
			_proxy.fireSingleEvent(Constants.kClose, _close, event, true);
		}
	}
}
