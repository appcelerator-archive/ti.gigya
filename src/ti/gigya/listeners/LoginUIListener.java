package ti.gigya.listeners;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.titanium.kroll.KrollCallback;

import ti.gigya.Util;

import com.gigya.socialize.GSObject;
import com.gigya.socialize.android.event.GSLoginUIListener;

public class LoginUIListener implements GSLoginUIListener 
{
	private final KrollCallback _success;
	private final KrollCallback _error;
	private final KrollCallback _load;
	private final KrollCallback _close;
	private final KrollProxy _proxy;

	public LoginUIListener(final KrollProxy proxy, final KrollDict args)
	{
		_success = (KrollCallback)args.get("success");
		_error = (KrollCallback)args.get("error");
		_load = (KrollCallback)args.get("load");
		_close = (KrollCallback)args.get("close");
		_proxy = proxy;
	}
	
	public void onLogin(String provider, GSObject user, Object context)
	{
		if (_success != null) {
			KrollDict event = new KrollDict();
			event.put("provider", provider);
			event.put("data", Util.dataFromGSObject(user));
			_proxy.fireSingleEvent("success", _success, event, true);
		}
	}
	
	public void onLoad(Object context)
	{
		if (_load != null) {
			_proxy.fireSingleEvent("load", _load, null, true);
		}
	}
	
	public void onError(int errorCode, String errorMessage, String trace, Object context)
	{
		if (_error != null) {
			KrollDict event = new KrollDict();
			event.put("errorCode", errorCode);
			event.put("errorMessage", errorMessage);
			_proxy.fireSingleEvent("error", _error, event, true);
		}
	}
	
	public void onClose(boolean canceled, Object context)
	{
		if (_close != null) {
			KrollDict event = new KrollDict();
			event.put("canceled", canceled);
			_proxy.fireSingleEvent("close", _close, event, true);
		}
	}
}
