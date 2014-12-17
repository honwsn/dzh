/**
 * maxxiang
 * 2014-5-7
 * TODO
 */
package com.hw.dzh.imageutils;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.hw.dzh.proto.ProtoCommandExecutor;

public class ImageCacheManager {

	private static ImageCacheManager mInstance;

	/**
	 * Memory Cache Size, 10M is the default value
	 */
	private int lMemoryCacheSize = 10 * 1024 * 1024;

	/**
	 * Volley image loader
	 */
	private ImageLoader mImageLoader;

	/**
	 * Image cache implementation
	 */
	private LruImageCache mImageCache = new LruImageCache(lMemoryCacheSize);

	/**
	 * @return instance of the cache manager
	 */
	public static ImageCacheManager getInstance() {
		if (mInstance == null)
			mInstance = new ImageCacheManager();

		return mInstance;
	}

	public void init(Context context) {
		mImageLoader = new ImageLoader(ProtoCommandExecutor.getInstance().getRequestQueue(), mImageCache);
	}

	public Bitmap getBitmap(String url) {
		try {
			return mImageCache.getBitmap(createKey(url));
		} catch (NullPointerException e) {
			throw new IllegalStateException("Image Cache Not initialized");
		}
	}

	public void putBitmap(String url, Bitmap bitmap) {
		try {
			// int n = mImageCache.size();
			// int j = mImageCache.putCount();
			mImageCache.putBitmap(createKey(url), bitmap);
		} catch (NullPointerException e) {
			throw new IllegalStateException("Image Cache Not initialized");
		}
	}

	/**
	 * Executes and image load
	 * 
	 * @param url
	 *            location of image
	 * @param listener
	 *            Listener for completion
	 */
	public void getImage(String url, ImageListener listener) {
		mImageLoader.get(url, listener);
	}

	/**
	 * @return instance of the image loader
	 */
	public ImageLoader getImageLoader() {
		return mImageLoader;
	}

	/**
	 * Creates a unique cache key based on a url value
	 * 
	 * @param url
	 *            url to be used in key creation
	 * @return cache key value
	 */
	private String createKey(String url) {
		return String.valueOf(url.hashCode());
	}

	public ImageCache getImageCache() {
		return mImageCache;
	}
}
