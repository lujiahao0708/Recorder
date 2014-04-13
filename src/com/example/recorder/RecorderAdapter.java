package com.example.recorder;

import java.io.File;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class RecorderAdapter extends BaseAdapter {

	private Context mContext;
	private File[] mData;
	private LayoutInflater mInflater;
	private MediaPlayer mPlayer;
	// 定义对话框对象
	private AlertDialog alertDialog;

	public RecorderAdapter(Context context, File[] data) {
		mContext = context;
		mData = data;
		mPlayer = new MediaPlayer();
		mInflater = LayoutInflater.from(this.mContext);
	}

	@Override
	public int getCount() {
		return mData.length;
	}

	@Override
	public Object getItem(int arg0) {
		return mData[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.list_item, null);
		TextView fileName = (TextView) convertView.findViewById(R.id.fileName);
		TextView createtime = (TextView) convertView
				.findViewById(R.id.createtime);

		final File file = mData[position];
		fileName.setText(file.getName());

		createtime.setText(U.millis2CalendarString(file.lastModified(),
				"yyyy年MM月dd日 HH:mm:ss"));

		convertView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				alertDialog = new AlertDialog.Builder(mContext)
						.setTitle(file.getName())
						.setPositiveButton("播放",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										play(file.getAbsolutePath());
										Toast.makeText(mContext, "正在播放...",
												Toast.LENGTH_LONG).show();
									}
								})
						.setNegativeButton("删除",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 删除该文件
										file.delete();
										Toast.makeText(mContext, "删除成功",
												Toast.LENGTH_LONG).show();
									}
								}).create();
				alertDialog.show();

			}
		});

		return convertView;
	}

	// 录音播放
	private void play(String path) {
		try {
			mPlayer.reset();
			mPlayer.setDataSource(path);
			mPlayer.prepare();
			mPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
