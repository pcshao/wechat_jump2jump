package cn.pcshao.xgames.wechat_jump2jump.dao_adb;

import java.io.File;
import java.io.IOException;
/**
 * 2018/3/9
 * @author pcshao-mini pcshao.cn
 *
 */
public class ADB_tools {
	
	static String ADB_PATH = "C:/adb/adb";
	
	/**
	 * ��ͼ
	 */
	public void Screnntshot() {
		Process process;
		try {
			process = Runtime.getRuntime().exec(ADB_PATH + " shell /system/bin/screencap -p /sdcard/screenshot.png");
			process.waitFor();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/**
	 * ʹ��ʱ���뽫��ǰ������file���󴫽���
	 * @param file
	 */
	public void Pullimage(File file) {
		Process process;
		try {
			process = Runtime.getRuntime().exec(ADB_PATH + " pull /sdcard/screenshot.png " + file.getAbsolutePath());
			process.waitFor();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	/**
	 * 
	 * @param pressX
	 * @param pressY
	 * @param distance
	 */
	public void Swipe(int pressX,int pressY,int distance) {
		String adbCommand = ADB_PATH + String.format(" shell input swipe %d %d %d %d %d", pressX, pressY, pressX, pressY, distance);
		System.out.println(adbCommand);
		
		try {
			Runtime.getRuntime().exec(adbCommand);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
