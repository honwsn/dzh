/**
 * 
 */
package com.hw.dzh.network;

/**
 * @author maxxiang
 * 
 */
public class HttpHeader
{
	public static final class REQ
	{
		public static final String	ACCEPT			= "Accept";
		public static final String	HOST			= "Host";
		public static final String	ACCEPT_LANGUAGE	= "Accept-Language";
		public static final String	ACCEPT_ENCODING	= "Accept-Encoding";
		public static final String	CONTENT_LENGTH	= "Content-Length";
		public static final String	CONTENT_TYPE	= "Content-Type";
		public static final String	USER_AGENT		= "User-Agent";
		public static final String	REFERER			= "Referer";
		public static final String	RANGE			= "Range";
		public static final String	CONNECTION		= "Connection";
		public static final String	COOKIE			= "Cookie";
		public static final String	QCOOKIE			= "QCookie";
		public static final String	QUA				= "Q-UA";
		public static final String	QGUID			= "Q-GUID";
		public static final String	QAUTH			= "Q-Auth";
		public static final String	X_ONLINE_HOST	= "X-Online-Host";

		public static final String	QENCRYPT		= "QQ-S-Encrypt";
		public static final String	QSZIP			= "QQ-S-ZIP";

	}

	public static final class RSP
	{
		/* version */
		/* status code */
		public static final String	LOCATION			= "Location";
		public static final String	SERVER				= "Server";
		public static final String	CONTENT_TYPE		= "Content-Type";
		public static final String	CONTENT_LENGTH		= "Content-Length";
		public static final String	CONTENT_ENCODING	= "Content-Encoding";
		public static final String	CHARSET				= "Charset";
		public static final String	TRANSFER_ENCODING	= "Transfer-Encoding";
		public static final String	LAST_MODIFY			= "Last-Modified";
		public static final String	BYTE_RNAGES			= "Byte-Ranges";
		public static final String	CACHE_CONTROL		= "Cache-Control";
		public static final String	CONNECTION			= "Connection";
		public static final String	CONTENT_RANGE		= "Content-Range";
		public static final String	CONTENT_DISPOSITION	= "Content-Disposition";
		public static final String	ETAG				= "ETag";
		public static final String	RETRY_AFTER			= "Retry-After";

		public static final String	QENCRYPT			= "QQ-S-Encrypt";
		public static final String	QSZIP				= "QQ-S-ZIP";
	}
}
