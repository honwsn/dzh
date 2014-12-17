package com.hw.dzh.imageutils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.hw.dzh.utils.MttLog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;

/**
 * @author maxxiang
 * 本类提供图片压缩的功能，比如选择相册，发送图片�?如果不是选择原图发�?（太大啦），都需要先压缩后再发�?。（记得不要在主线程调用�?
 * Create On: 2014-7-21 
 */
public class ImageCompress {
	//压缩质量参数
	public static final int IMAGE_COMPRESS_MID_QUALITY = 50;
	public static final int IMAGE_COMPRESS_HIGH_QUALITY = 100;  //质量压缩方法，这�?00表示不压�?
	
	//像素限制
	public static final int IMAGE_LONG_LIMIT = 960;
	public static final int IMAGE_SHORT_LIMIT = 640;
	
	//小图片的处理
	public static final long IMAGE_MIN_LENGHT = 100 * 1024;  //<100k算小图？
	
	//*************************************************************************************
	//对外：生成一个新的图片，经过自动压缩�?(非原�?. �?M的原图，降质�?00K左右
	//*************************************************************************************
	public static boolean makeCompressImage(final String orginPath,
			final String imgNewPath, final String strFileName) {
		try {
			// 这里可能会有OOM异常，添加异常处�?
			BitmapFactory.Options opt = getImageOptions(orginPath);
			if (opt.outWidth >= opt.outHeight * 2
					|| opt.outHeight >= opt.outWidth * 2) {
				
				BitmapFactory.Options optNew = new BitmapFactory.Options();
				optNew.inPurgeable = true;//If this is set to true, then the resulting bitmap will allocate its pixels such that they can be purged if the system needs to reclaim memory.
				optNew.inInputShareable = true; 
				Bitmap bm = BitmapFactory.decodeFile(orginPath, optNew);
				if (bm == null)
					return false;
				int degree = getOrientation(orginPath);
				bm = BitmapRotate(bm, degree);
				return SaveBitmapToImage(bm, IMAGE_COMPRESS_MID_QUALITY,
						Bitmap.CompressFormat.JPEG, imgNewPath + strFileName,
						true);
			}

			int w = opt.outWidth >= opt.outHeight ? IMAGE_LONG_LIMIT
					: IMAGE_SHORT_LIMIT;
			int h = opt.outHeight >= opt.outWidth ? IMAGE_SHORT_LIMIT
					: IMAGE_LONG_LIMIT;
			return extractThumbImage(orginPath, w, h,
					Bitmap.CompressFormat.JPEG, IMAGE_COMPRESS_MID_QUALITY,
					imgNewPath + strFileName, true);
		} 
		catch (OutOfMemoryError err){
			MttLog.d("img", "OutOfMemoryError err");
			return false;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	
	//*************************************************************************************
	//以下是对内的函数
	//*************************************************************************************

	
	//生成裁剪后的图片，并存储
	private static boolean extractThumbImage(String orignPath, int width, int height, Bitmap.CompressFormat format,
			int quality, String strPathName, boolean checkDegree){
		BitmapFactory.Options opt= getImageOptions(orignPath);
		try{
			opt.inJustDecodeBounds = false;
			opt.inSampleSize = calculateInSampleSize(opt, width, height);  //maxxiang todo: 未来这里要灵�?

			Bitmap tmpBmp= BitmapFactory.decodeFile(orignPath, opt);
			
			if (checkDegree){
				int degree = getOrientation(orignPath);
				tmpBmp = BitmapRotate(tmpBmp, degree);
			}
			
			return SaveBitmapToImage(tmpBmp, quality, format, strPathName, true);
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
	{
		if (reqWidth == 0 || reqHeight == 0 || reqWidth == -1 || reqHeight == -1)
		{
			return 1;
		}
		// Raw height and width of image
		int height = options.outHeight;
		int width = options.outWidth;
		int inSampleSize = 1;
		
        double wr = (double) width / reqWidth;
        double hr = (double) height / reqHeight;
        double ratio = Math.min(wr, hr);
        while ((inSampleSize * 2) <= ratio) {
        	inSampleSize *= 2;
        }

		return inSampleSize;
	}
	
	private static boolean SaveBitmapToImage(Bitmap bm, final int quality, Bitmap.CompressFormat format,
			String strPath,  final boolean recycle){
		File f= new File(strPath);
		try {
			if(f.exists()){
				f.delete();
			}
			f.createNewFile();
			FileOutputStream fOut= null;
			fOut = new FileOutputStream(f);
			bm.compress(format, quality, fOut);
			fOut.flush();
			fOut.close();
			
			if (recycle){
				bm.recycle();
			}
			return true;
				
		}catch(IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	private static int getOrientation(final String path){
		int degree = 0;
		ExifInterface exif = null;
		try{
			exif = new ExifInterface(path);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if (exif != null){
			int on = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
			if(on != -1){
				switch (on) {
				case ExifInterface.ORIENTATION_ROTATE_90:{
					degree= 90;
					break;
				}
				case ExifInterface.ORIENTATION_ROTATE_180:{
					degree= 180;
					break;
				}
				case ExifInterface.ORIENTATION_ROTATE_270:{
					degree= 270;
					break;
				}
				default:
					break;
				}
			}
		}
		return degree;
	}
	
	private static Bitmap BitmapRotate(Bitmap tmpBm, int degree){
		if (degree == 0)
			return tmpBm;
		
		Matrix m = new Matrix();
		m.reset();
		m.setRotate(degree, tmpBm.getWidth()/2, tmpBm.getHeight()/2);
		Bitmap bm = Bitmap.createBitmap(tmpBm, 0, 0, tmpBm.getWidth(), tmpBm.getHeight(), m, true);
		if (tmpBm != bm){
			tmpBm.recycle();
		}
		return bm;
	}
	
	private static BitmapFactory.Options getImageOptions(final String path){
		final BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true;
		try{
			Bitmap tmp= BitmapFactory.decodeFile(path, opt);
			if(tmp != null){
				tmp.recycle();
				tmp = null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return opt;
	}
	
}
