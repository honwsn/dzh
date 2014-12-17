/*
 * Copyright (C) 2010 Lytsing Huang http://lytsing.org
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// package org.lytsing.android.util;
package com.hw.dzh.utils;


import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

/**
 * Wrapper API for sending log output.
 */
public class MttLog
{

	protected static final String	TAG = "dzh";

	/*
	 * 'enableLog' flag hints whether the MttLog.d(), MttLog.w(), etc. is going to work or not.
	 */
	private static boolean			enableLog = true;//EnvironmentManager.IS_DEBUG;
	
	public static boolean			ENABLE_INPUT_DEBUG = false;
	
	public static boolean			ENABLE_SCROLL_DEBUG = false;
	
	//modify by rmli 这个日志是处理消息传递逻辑的
	//打开这个日志开关，可以清楚看到消息的走向
	public static boolean			ENABLE_INPUTDISPATCH_DEBUG = false;	
	
	public static final String		SCROLL_TAG = "ScrollDebug";
	
	public static final String		INPUTDISPATCH_TAG = "InputDispatchDebug";
	
	public static final String		PERFORMANCE_STAT_TAG = "performancestat";
	
	public static final String		AD_FILTERING_USING_REGEX_TAG = "adfilteringusingregex";

	// log
	public static final byte		LOG_CONSOLE			= 1;			// console
	public static final byte		LOG_FILE			= 2;			// file
	public static final byte		LOG_BOTH			= 3;			// both
	public static byte				logDevice			= LOG_CONSOLE;
	
	public static final byte		LOG_LEVEL_V			= 1;
	public static final byte		LOG_LEVEL_D			= 2;
	public static final byte		LOG_LEVEL_I			= 3;
	public static final byte		LOG_LEVEL_W			= 4;
	public static final byte		LOG_LEVEL_E			= 5;

	public static boolean isEnableLog()
	{
		return enableLog;
	}

	// enable performance log? you can enable it by return true
	public static boolean enablePerformanceLog()
	{
		return false;
	}


	public MttLog()
	{
	}

	public static void printLogAs(String tag, String msg, int level, int device)
	{
		if (msg == null)
			msg = "NULL MSG";

		switch (device)
		{
			case LOG_CONSOLE:
				printLogAs(tag, msg, level);
				break;
			case LOG_FILE:
				break;
			case LOG_BOTH:
				break;
			default:
				break;
		}
	}
	public static void printLogAs(String tag, String msg, int level, int device, Throwable thr)
	{
		if (msg == null)
			msg = "NULL MSG";

		switch (device)
		{
			case LOG_CONSOLE:
				printLogAs(tag, msg, level, thr);
				break;
			case LOG_FILE:
				break;
			case LOG_BOTH:
				break;
			default:
				break;
		}
	}
	
	public static void printLogAs(String tag, String msg, int level)
	{
		if(!enableLog)
			return;
		
		if (msg == null)
			msg = "NULL MSG";

		switch (level)
		{
			case LOG_LEVEL_V:
				Log.v(tag, msg);
				break;
			case LOG_LEVEL_D:
				Log.d(tag, msg);
				break;
			case LOG_LEVEL_I:
				Log.i(tag, msg);
				break;
			case LOG_LEVEL_W:
				Log.w(tag, msg);
				break;
			case LOG_LEVEL_E:
				Log.e(tag, msg);
				break;
			default:
				break;
		}
	}
	
	public static void printLogAs(String tag, String msg, int level, Throwable thr)
	{
		if (msg == null)
			msg = "NULL MSG";

		switch (level)
		{
			case LOG_LEVEL_V:
				Log.v(tag, msg, thr);
				break;
			case LOG_LEVEL_D:
				Log.d(tag, msg, thr);
				break;
			case LOG_LEVEL_I:
				Log.i(tag, msg, thr);
				break;
			case LOG_LEVEL_W:
				Log.w(tag, msg, thr);
				break;
			case LOG_LEVEL_E:
				Log.e(tag, msg, thr);
				break;
			default:
				break;
		}
	}
	
	
	/**
	 * Send a VERBOSE log message.
	 * 
	 * @param msg The message you would like logged.
	 */
	public static void v(String msg)
	{
		printLogAs(TAG, msg, LOG_LEVEL_V, logDevice);
	}

	/**
	 * Send a VERBOSE log message.
	 * 
	 * @param msg The message you would like logged.
	 */
	public static void v(String tag, String msg)
	{
		printLogAs(tag, msg, LOG_LEVEL_V, logDevice);
	}



	/**
	 * Send a VERBOSE log message and log the exception.
	 * 
	 * @param msg The message you would like logged.
	 * @param thr An exception to log
	 */
	public static void v(String msg, Throwable thr)
	{
		printLogAs(TAG, msg, LOG_LEVEL_V, logDevice, thr);
	}

	/**
	 * Send a VERBOSE log message and log the exception with specified tag.
	 * 
	 * @param tag specified tag.
	 * @param msg The message you would like logged.
	 * @param thr An exception to log
	 */
	public static void v(String tag, String msg, Throwable thr)
	{
		printLogAs(tag, msg, LOG_LEVEL_V, logDevice, thr);
	}

	/**
	 * Send a DEBUG log message.
	 * 
	 * @param msg The message you would like logged.
	 */
	public static void d(String msg)
	{
		printLogAs(TAG, msg, LOG_LEVEL_D, logDevice);
	}
	
	public static void d(String msg, byte[] b, int offset, int len){
		StringBuffer strBuf = new StringBuffer(); 
		if(b != null){
			for (int i = offset; i < len; i++) { 
				String hex = Integer.toHexString(b[i] & 0xFF); 
				if (hex.length() == 1) { 
					hex = '0' + hex; 
				} 
				strBuf.append(' ').append(hex);
			} 
		}
		
		printLogAs(TAG, msg + strBuf.toString(), LOG_LEVEL_D, logDevice);
	}
	 

	/**
	 * Send a DEBUG log message.
	 * 
	 * @param tag specified tag
	 * @param msg The message you would like logged.
	 */
	public static void d(String tag, String msg)
	{
		printLogAs(tag, msg, LOG_LEVEL_D, logDevice);
	}

	/**
	 * Send a DEBUG log message and log the exception.
	 * 
	 * @param msg The message you would like logged.
	 * @param tr An exception to log
	 */
	public static void d(String msg, Throwable thr)
	{
		printLogAs(TAG, msg, LOG_LEVEL_D, logDevice, thr);
	}

	/**
	 * Send a DEBUG log message and log the exception with specified tag.
	 * 
	 * @param tag specified tag.
	 * @param msg The message you would like logged.
	 * @param thr An exception to log
	 */
	public static void d(String tag, String msg, Throwable thr)
	{
		printLogAs(tag, msg, LOG_LEVEL_D, logDevice, thr);
	}

	/**
	 * Send an INFO log message.
	 * 
	 * @param msg The message you would like logged.
	 */
	public static void i(String msg)
	{
		printLogAs(TAG, msg, LOG_LEVEL_I, logDevice);
	}

	/**
	 * Send an INFO log message.
	 * 
	 * @param tag Used to identify the source of a log message.
	 * @param msg The message you would like logged.
	 */
	public static void i(String tag, String msg)
	{
		printLogAs(tag, msg, LOG_LEVEL_I, logDevice);
	}

	/**
	 * Send a INFO log message and log the exception.
	 * 
	 * @param msg The message you would like logged.
	 * @param thr An exception to log
	 */
	public static void i(String msg, Throwable thr)
	{
		printLogAs(TAG, msg, LOG_LEVEL_I, logDevice, thr);
	}


	/**
	 * Send a INFO log message and log the exception.
	 * 
	 * @param tag Used to identify the source of a log message.
	 * @param msg The message you would like logged.
	 * @param thr An exception to log
	 */
	public static void i(String tag, String msg, Throwable thr)
	{
		printLogAs(tag, msg, LOG_LEVEL_I, logDevice, thr);
	}

	/**
	 * Send an ERROR log message.
	 * 
	 * @param msg The message you would like logged.
	 */
	public static void e(String msg)
	{
		printLogAs(TAG, msg, LOG_LEVEL_E, logDevice);
	}

	/**
	 * Send an ERROR log message with specified tag.
	 * 
	 * @param tag Used to identify the source of a log message.
	 * @param msg The message you would like logged.
	 */
	public static void e(String tag, String msg)
	{
		printLogAs(tag, msg, LOG_LEVEL_E, logDevice);
	}

	/**
	 * Send a WARN log message
	 * 
	 * @param msg The message you would like logged.
	 */
	public static void w(String msg)
	{
		printLogAs(TAG, msg, LOG_LEVEL_W, logDevice);
	}

	/**
	 * Send a WARN log message
	 * 
	 * @param tag Used to identify the source of a log message.
	 * @param msg The message you would like logged.
	 */
	public static void w(String tag, String msg)
	{
		printLogAs(tag, msg, LOG_LEVEL_W, logDevice);
	}

	/**
	 * Send a WARN log message and log the exception.
	 * 
	 * @param msg The message you would like logged.
	 * @param thr An exception to log
	 */
	public static void w(String msg, Throwable thr)
	{
		printLogAs(TAG, msg, LOG_LEVEL_W, logDevice, thr);
	}

	/**
	 * Send a WARN log message and log the exception.
	 * 
	 * @param tag Used to identify the source of a log message.
	 * @param msg The message you would like logged.
	 * @param thr An exception to log
	 */
	public static void w(String tag, String msg, Throwable thr)
	{
		printLogAs(tag, msg, LOG_LEVEL_W, logDevice, thr);
	}

	/**
	 * Send an empty WARN log message and log the exception.
	 * 
	 * @param thr An exception to log
	 */
	public static void w(Throwable thr)
	{
		android.util.Log.w(TAG, buildMessage(""), thr);
	}

	/**
	 * Send an ERROR log message and log the exception.
	 * 
	 * @param msg The message you would like logged.
	 * @param thr An exception to log
	 */
	public static void e(String msg, Throwable thr)
	{
		printLogAs(TAG, msg, LOG_LEVEL_E, logDevice, thr);
	}

	/**
	 * Send an ERROR log message and log the exception.
	 * 
	 * @param tag Used to identify the source of a log message.
	 * @param msg The message you would like logged.
	 * @param thr An exception to log
	 */
	public static void e(String tag, String msg, Throwable thr)
	{
		printLogAs(tag, msg, LOG_LEVEL_E, logDevice, thr);
	}

	/**
	 * Building Message
	 * 
	 * @param msg The message you would like logged.
	 * @return Message String
	 */
	protected static String buildMessage(String msg)
	{
		return msg;
//		StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
//
//		return new StringBuilder().append(caller.getClassName()).append(".").append(caller.getMethodName()).append("(): ").append(msg).toString();
	}
	
}
