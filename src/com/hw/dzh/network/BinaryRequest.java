package com.hw.dzh.network;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.hw.dzh.utils.BytesBuffer;


/**
 * @author maxxiang
 * 扩展自volley, 提供multipart/form-data的post功能，简单说是为了支持wup的post能力�?
 * Create On: 2014-5-7 
 */
public class BinaryRequest extends Request<byte[]> {
	private static final int TIMEOUT = 5000;
	private final Response.Listener<byte[]> mListener;
	private final Response.ErrorListener   mErrorListener;
	private RetryPolicy mRetryPolicy;
	private byte[] mData;
	
	private String rspContent_Encoding;
	
	public BinaryRequest(String url, byte[] data, Response.Listener<byte[]> listener, Response.ErrorListener errorListener) {
		super(Method.POST, url, errorListener);
		// TODO Auto-generated constructor stub

		mData = data;
		mListener = listener;
		mErrorListener = errorListener;
		
		mRetryPolicy = new DefaultRetryPolicy(TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		
		this.setRetryPolicy(mRetryPolicy);
	}
	
	@Override  
    public Map<String, String> getHeaders() throws AuthFailureError {  
		Map<String, String> headers = new HashMap<String, String>();  
//        headers.put(HttpHeader.REQ.QGUID, AccountManager.getInstance().getGuid());
//        headers.put(HttpHeader.REQ.QUA, EnvironmentManager.getQua());
        headers.put(HttpHeader.REQ.USER_AGENT, "dzh");
        headers.put(HttpHeader.REQ.ACCEPT, "*/*");
        headers.put(HttpHeader.REQ.ACCEPT_ENCODING, "gzip"); //identity
        headers.put(HttpHeader.REQ.CONTENT_LENGTH, String.valueOf(mData.length));
//        headers.put(HttpHeader.REQ.CONTENT_TYPE, "multipart/form-data; charset=utf-8; boundary=----abcdefgfedcba----");//utf-8
        return headers;  
    }  	
	
	@Override
    public String getBodyContentType() {
        return "multipart/form-data; charset=utf-8; boundary=7d92bf36e1196";
		//return "application/x-www-form-urlencoded";
    }

	
	@Override
    public byte[] getBody() throws AuthFailureError
    {
		return mData;
    }

	@Override
	protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
		// TODO Auto-generated method stub
		rspContent_Encoding = response.headers.get(HttpHeader.RSP.CONTENT_ENCODING);
		return Response.success(response.data, getCacheEntry());
	}

	@Override
	protected void deliverResponse(byte[] response) {
		// TODO Auto-generated method stub
		if (rspContent_Encoding != null && rspContent_Encoding.equalsIgnoreCase("gzip"))
			mListener.onResponse(BytesBuffer.unGzip(response));
		else
			mListener.onResponse(response);
	}
	
	@Override
	public RetryPolicy getRetryPolicy()
	{
		return mRetryPolicy;
	}

}
