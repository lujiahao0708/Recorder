package com.example.recorder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

public class U {

	public static final String DATA_DIRECTORY = "/sdcard/";

	// 查看路径是否有文件
	public static void createDirectory() {
		if (sdCardExists()) {
			File file = new File(DATA_DIRECTORY);
			if (!file.exists()) {
				file.mkdirs();
			}
		} else {
			Log.e("U", "无sd卡...");
		}
	}

	// 检查SD卡是否存在
	public static boolean sdCardExists() {
		return Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	// 获取文件创建时间
	@SuppressLint("SimpleDateFormat")
	public static String millis2CalendarString(long millis, String format) {
		if (millis > 0) {
			SimpleDateFormat yFormat = new SimpleDateFormat(format);
			Calendar yCalendar = Calendar.getInstance();
			yCalendar.setTimeInMillis(millis);

			try {
				return yFormat.format(yCalendar.getTime());
			} catch (Exception e) {

			} finally {
				yCalendar = null;
				yFormat = null;
				format = null;
			}
		}

		return "";
	}

}
