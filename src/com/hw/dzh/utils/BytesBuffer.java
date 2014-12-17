/**
 * maxxiang
 * 2014-5-1
 * TODO
 */
package com.hw.dzh.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
/**
 * @author maxxiang
 *
 * Create On: 2014-5-1 
 */
public class BytesBuffer {
	private static final String	TAG	= "BytesBuffer";

	private byte[]				buffer;

	public BytesBuffer()
	{
		buffer = new byte[0];
	}

	public int size()
	{
		if (buffer != null)
		{
			return buffer.length;
		}
		return 0;
	}

	public void append(byte[] data, int offest, int len)
	{
		if (data == null || data.length < offest + len)
		{
			return;
		}

		byte[] oldBuffer = buffer;
		buffer = new byte[oldBuffer.length + len];
		System.arraycopy(oldBuffer, 0, buffer, 0, oldBuffer.length);
		System.arraycopy(data, offest, buffer, oldBuffer.length, len);
		oldBuffer = null;
	}

	public void append(byte[] data)
	{
		append(data, 0, data.length);
	}

	public byte[] get(int len)
	{
		if (buffer != null && buffer.length >= len)
		{
			byte[] oldBuffer = buffer;
			byte[] data = new byte[len];
			System.arraycopy(oldBuffer, 0, data, 0, len);

			buffer = new byte[oldBuffer.length - len];
			System.arraycopy(oldBuffer, len, buffer, 0, oldBuffer.length - len);

			oldBuffer = null;
			return data;
		}
		else
		{
			byte[] data = buffer;
			buffer = new byte[0];
			return data;
		}
	}


	public int getHeadInt()
	{
		if (buffer == null || buffer.length < 4)
			return -1;
		try
		{
			ByteArrayInputStream bin = new ByteArrayInputStream(buffer);
			DataInputStream dis = new DataInputStream(bin);
			return dis.readInt();
		}
		catch (IOException e)
		{
			return -1;
		}
		// return ((buffer[0] << 24) + (buffer[1] << 16) + (buffer[2] << 8) +
		// (buffer[3] << 0));
	}

	/**
	 * Gzip
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] gZip(byte[] data)
	{
		byte[] b = null;
//		Logger.d(TAG, "gzip begin");
		try
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			GZIPOutputStream gzip = new GZIPOutputStream(bos);
			gzip.write(data);
			gzip.finish();
			gzip.close();
			b = bos.toByteArray();
			bos.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return data;
		}
//		Logger.d(TAG, "gzip end");
		return b;
	}

	/***
	 * GZip
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] unGzip(byte[] data)
	{
		byte[] b = null;
//		Logger.d(TAG, "unGzip begin");
		try
		{
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			GZIPInputStream gzip = new GZIPInputStream(bis);
			byte[] buf = new byte[1024];
			int num = -1;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((num = gzip.read(buf, 0, buf.length)) != -1)
			{
				baos.write(buf, 0, num);
			}
			b = baos.toByteArray();
			baos.flush();
			baos.close();
			gzip.close();
			bis.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return data;
		}
//		Logger.d(TAG, "unGzip end");
		return b;
	}


}
