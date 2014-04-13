package com.example.recorder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.OutputFormat;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private ImageButton imageButton_about;
	private ImageButton imageButton_recorderlist;
	private ImageButton imageButton_recorde;
	private ImageButton imageButton_stop;
	private Button button_status;
	
	
	// 音频文件  
	File soundFile;
	MediaRecorder mediaRecorder;
	// 定义对话框对象
	private AlertDialog alertDialog;
	private RecorderAdapter mAdapter;
	// 刷新按钮
	protected MenuItem refreshItem;
	
	
	//时钟显示相关
	private TextView minText;		//分
	private TextView secText;		//秒
	private boolean isPaused = false;
	private String timeUsed;
	private int timeUsedInsec;
	
	private Handler uiHandle = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 1:
				if(!isPaused) {
					addTimeUsed();
					updateClockUI();
				}
				uiHandle.sendEmptyMessageDelayed(1, 1000);
				break;
			default: break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去掉标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		// 实例化底栏的按钮
		ImageButton imageButton_about = (ImageButton) this
				.findViewById(R.id.imageButton_about);
		ImageButton imageButton_recorderlist = (ImageButton) this
				.findViewById(R.id.imageButton_recorderlist);
		ImageButton imageButton_recorde = (ImageButton) this
				.findViewById(R.id.imageButton_recorde);
		ImageButton imageButton_stop = (ImageButton) this
				.findViewById(R.id.imageButton_stop);
		final Button button_status = (Button)this.findViewById(R.id.button_status);
		
		//获取界面的控件
        minText = (TextView) findViewById(R.id.min);
        secText = (TextView) findViewById(R.id.sec);
        

		// 关于界面的按钮
		imageButton_about.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, About.class);
				MainActivity.this.startActivity(intent);
			}
		});
		// 录音列表界面按钮
		imageButton_recorderlist.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, RecorderList.class);
				MainActivity.this.startActivity(intent);
			}
		});
		// 录音按钮
		imageButton_recorde.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 具体的录音功能的实现
				try {
					Log.d("lb", Environment.getExternalStorageDirectory()
							.getCanonicalFile().toString());
					String s = new SimpleDateFormat("yyyyMMdd_hhmmss")
							.format(new Date(System.currentTimeMillis()));
					// 直接存储到了sdcard中
					soundFile = new File(Environment
							.getExternalStorageDirectory().getCanonicalFile()
							+ "/" + s + ".wav");
					mediaRecorder = new MediaRecorder();
					mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 录制的声音的来源
					// recorder.setVideoSource(video_source); //录制视频
					// 录制的声音的输出格式（必须在设置声音的编码格式之前设置）
					mediaRecorder
							.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					// 设置声音的编码格式
					mediaRecorder
							.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
					// 设置声音的保存位置
					mediaRecorder.setOutputFile(soundFile.getAbsolutePath());
					mediaRecorder.prepare(); // **准备录音**
					mediaRecorder.start(); // **开始录音**
					button_status.setText("正在录音...");
					
					//开始计时
					uiHandle.removeMessages(1);
					startTime();
					isPaused = false;
					
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		//停止录音
		imageButton_stop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (soundFile != null && soundFile.exists()) {
					mediaRecorder.stop(); // **停止录音**
					button_status.setText("停止录音");
					
					//停止计时
					isPaused = true;
					timeUsedInsec = 0;
					
					
					mediaRecorder.release(); // **释放资源**
					mediaRecorder = null;
				}
			}
		});
		
		
	}

	
	@Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	isPaused = true;
    }
	
	@Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	isPaused = false;
    }
    
    private void startTime(){
    	uiHandle.sendEmptyMessageDelayed(1, 1000);
    }
    
    /**
     * 更新时间的显示
     */
    private void updateClockUI(){
    	minText.setText(getMin() + "0");
    	secText.setText(getSec());
    }
    
    public void addTimeUsed(){
    	timeUsedInsec = timeUsedInsec + 1;
    	timeUsed = this.getMin() + "" + this.getSec();
    }
    
    public CharSequence getMin(){
    	return String.valueOf(timeUsedInsec / 60);
    }
    
    public CharSequence getSec(){
    	int sec = timeUsedInsec % 60;
    	return sec < 10? "0" + sec :String.valueOf(sec);
    }
    
    
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
