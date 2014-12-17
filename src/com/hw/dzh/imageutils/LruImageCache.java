/**
 * maxxiang
 * 2014-5-7
 * TODO
 */
package com.hw.dzh.imageutils;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.hw.dzh.utils.MttLog;

/**
 * @author maxxiang
 * 用于bitmap的Memory Cache, 此类不对UI�?��，仅提供给ImageCacheManager�?
 * 2014/7/18: 此类�?��外开放啦，其它地方需要用�?
 * Create On: 2014-5-7 
 */
public class LruImageCache extends LruCache<String, Bitmap> implements ImageCache {
	private final String TAG = this.getClass().getSimpleName();
	
	public LruImageCache(int maxSize) {
		super(maxSize);
	}

	@Override
	protected int sizeOf(String key, Bitmap value) {
		return value.getRowBytes() * value.getHeight();
	}	

	@Override
	public Bitmap getBitmap(String url) {
		return get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		int n = size();
		int j = putCount();		
		put(url, bitmap);
	}

	@Override
	protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
		
		if(evicted){
			MttLog.d("img", "maxsize:"+maxSize()+"size:"+size() + "  removed from cache: " + key);
		}
	}
}
