package com.hw.dzh;

public class EnvironmentManager  {
	public static final boolean IS_DEBUG = false;//发布时改为false
	//测试地址,先在测试环境联调
	public static String TEST_SERVER_ADDR = "http://61.172.204.175:18000";
	public static String RELEASE_SERVER_ADDR = "http://wup.imtt.qq.com:8080";
	public static String WUP_SERVER_ADDR = RELEASE_SERVER_ADDR;
}
