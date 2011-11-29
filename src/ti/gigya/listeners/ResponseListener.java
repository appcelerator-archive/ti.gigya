package ti.gigya.listeners;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.titanium.kroll.KrollCallback;

import ti.gigya.Util;

import com.gigya.socialize.GSResponse;
import com.gigya.socialize.GSResponseListener;

public class ResponseListener implements GSResponseListener 
{
	private final KrollCallback _success;
	private final KrollCallback _error;
	private final KrollProxy _proxy;
	
	public ResponseListener(final KrollProxy proxy, final KrollDict args)
	{
		_success = (KrollCallback)args.get("success");
		_error = (KrollCallback)args.get("error");
		_proxy = proxy;
	}
	
	public void onGSResponse(String method, GSResponse response, Object context)
	{
		KrollDict event = new KrollDict();
		event.put("method", method);
		
		if (response.getErrorCode() == 0) {
			if (_success != null) {
				event.put("data", Util.dataFromGSObject(response.getObject("data",null)));
				_proxy.fireSingleEvent("success", _success, event, true);
			}
		} else {
			if (_error != null) {
				event.put("errorCode", response.getErrorCode());
				event.put("errorMessage", response.getErrorMessage());
				_proxy.fireSingleEvent("error", _error, event, true);
			}
		}
	}

}
