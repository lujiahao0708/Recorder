package com.example.recorder;

import java.io.File;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

public class RecorderList extends Activity {

	private ListView lv;
	private RecorderAdapter mAdapter;
	// 音频文件  
	private File soundFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 去掉标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.recorderlist);

		lv = (ListView) findViewById(R.id.listView1);

		// 更新ListView数据

		File[] files = null;
		if (U.sdCardExists()) {
			File file = new File(U.DATA_DIRECTORY);
			// 查找该目录下所有wav格式文件
			WavFileNameFilter filenameFilter = new WavFileNameFilter(".wav");
			files = file.listFiles(filenameFilter);
		}
		mAdapter = new RecorderAdapter(this, files);

		// 设置适配器
		lv.setAdapter(mAdapter);

	}

}
