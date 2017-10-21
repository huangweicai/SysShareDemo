package com.example.huangweicai.androidsysshare;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;

public class MainActivity extends Activity {
	public void shareText(View view) {
		SysShareUtil.getInstance().shareText(this);
	}




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView pathTextView=(TextView) findViewById(R.id.path);
		
        Intent intent = getIntent();
        String action = intent.getAction();//action
        String type = intent.getType();//类型
        
        //类型是mp4类型
        if (Intent.ACTION_SEND.equals(action) && type != null && "video/mp4".equals(type)) {

			Log.d("TAG", "---MainActivity---");

			Uri uri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
			//如果是媒体类型需要从数据库获取路径
			String filePath=getRealPathFromURI(uri);
            pathTextView.setText("视频文件路径:"+filePath);
        }
	}
	
	/**
	 * 通过Uri获取文件在本地存储的真实路径
	 */
	private String getRealPathFromURI(Uri contentUri) {
		String[] proj = {MediaStore.MediaColumns.DATA};
		Cursor cursor=getContentResolver().query(contentUri, proj, null, null, null);
		if(cursor.moveToNext()){
			return cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
		}
		cursor.close();
		return null;
	}
}
