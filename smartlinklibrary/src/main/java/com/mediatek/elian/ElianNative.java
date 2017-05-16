package com.mediatek.elian;


public class ElianNative {
	/**
	 * 动态库加载
	 * @return
     */
	public static boolean LoadLib() {
		try {
			System.loadLibrary("elianjni");
			return true;
		} catch (UnsatisfiedLinkError localUnsatisfiedLinkError) {

		}
		return false;
	}

    /**
	 *  获得动态库的版本（没有太大作用）
	 * @return
	 */
	public native int GetLibVersion();

	public native int GetProtoVersion();


	/**
	 * 初始化智能连机固定为（null,1,1）
	 * @param paramString null
	 * @param paramInt1   1
	 * @param paramInt2   1
     * @return
     */
	public native int InitSmartConnection(String paramString, int paramInt1, int paramInt2);

	/**
	 * 开始发包
	 * @param paramString1  WiFi的SSID
	 * @param paramString2  WiFi的密码
	 * @param paramString3  ""传空字符串
	 * @param paramByte     WiFi加密方式
     * @return
     */
	public native int StartSmartConnection(String paramString1, String paramString2, String paramString3, byte paramByte);

	/**
	 * 停止发包
	 * @return
     */
	public native int StopSmartConnection();

}
