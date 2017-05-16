package com.mediatek.elian;

public class ElianNative {
	/*
	 * 动态库加载
	 */
	public static boolean LoadLib() {
		try {
			System.loadLibrary("elianjni");
			return true;
		} catch (UnsatisfiedLinkError localUnsatisfiedLinkError) {
			System.err.println("WARNING: Could not load elianjni library!");
		}
		return false;
	}

	/*
	 * 获得动态库的版本（没有太大作用）
	 */
	public native int GetLibVersion();

	public native int GetProtoVersion();

	/*
	 * 初始化智能连机固定为（null,1,1）
	 */
	public native int InitSmartConnection(String paramString, int paramInt1,
										  int paramInt2);

	/*
	 * 开始智能连机（WiFi的SSID,WiFi的密码，"",WiFi的加密方式） 枚举值如下 byte AuthModeAutoSwitch =2;
	 * byte AuthModeOpen = 0; byte AuthModeShared = 1; byte AuthModeWPA = 3;
	 * byte AuthModeWPA1PSKWPA2PSK = 9; byte AuthModeWPA1WPA2 = 8; byte
	 * AuthModeWPA2 = 6; byte AuthModeWPA2PSK = 7; byte AuthModeWPANone = 5;
	 * byte AuthModeWPAPSK = 4;
	 */
	public native int StartSmartConnection(String paramString1,
										   String paramString2, String paramString3, byte paramByte);

	/*
	 * 停止智能连机
	 */
	public native int StopSmartConnection();

}
