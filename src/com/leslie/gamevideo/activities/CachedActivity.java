package com.leslie.gamevideo.activities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.leslie.gamevideo.R;
import com.youku.service.download.DownloadInfo;
import com.youku.service.download.DownloadManager;

/**
 * 简单展示已经缓存的视频，用户可定制自己的界面
 *
 */
public class CachedActivity extends Activity {
	//展示视频信息的ListView
	private ListView lv;
	
	//数据Adapter
	private CachedVideoAdapter adapter;
	
	private static final int MSG_STATE_CHANGE = 0;
    
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if(msg.what == MSG_STATE_CHANGE){
                initData();
                //更改界面现实状态
                adapter.notifyDataSetChanged();
            }
        }
        
    };
	
	//通过DownloadManager获取下载视频列表
	private DownloadManager downloadManager;
	
	//记录当前已经下载的视频列表
	private ArrayList<DownloadInfo> downloadedList_show = new ArrayList<DownloadInfo>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.cache_activity);
		
		lv = (ListView)this.findViewById(R.id.list);

		adapter = new CachedVideoAdapter(this,handler);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(listener );
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//获取已经下载的视频数据		
		initData();
		//同志数据更新
		adapter.notifyDataSetChanged();		
	}
	
	/**
	 * 获取已下载视频信息
	 */
	private void initData(){
		downloadedList_show.clear();
		
		//通过DownloadManager类的getDownloadedData()接口函数获取已经下载的视频信息
		downloadManager = DownloadManager.getInstance();
		Iterator iter = downloadManager.getDownloadedData().entrySet().iterator();
		
		while(iter.hasNext()){
			Entry entry = (Entry) iter.next();
			//视频信息实体类用DownloadInfo表示
			DownloadInfo info = (DownloadInfo) entry.getValue();
			downloadedList_show.add(info);
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		downloadedList_show.clear();
		finish();
		super.onDestroy();
	}
	
	/**
	 * 简单示例：用户展示视频信息的Adapter
	 * 第三方需要自己完善
	 *
	 */
	class CachedVideoAdapter extends BaseAdapter{
		LayoutInflater inflater;
		private Handler h;
		
		public CachedVideoAdapter(Context context,Handler handler){
			inflater = LayoutInflater.from(context);
			h = handler;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return downloadedList_show.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return downloadedList_show.get(position).title;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = inflater.inflate(R.layout.cached_item, null);
			//简单展示视频信息的名称
			TextView tv_title = (TextView) view.findViewById(R.id.video_title);
			Button delete = (Button) view.findViewById(R.id.delete);
			//视频信息实体类用DownloadInfo表示
			final DownloadInfo info = downloadedList_show.get(position);
			
			tv_title.setText(info.title);
			
			delete.setOnClickListener(new OnClickListener() {
                
                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    downloadManager.deleteDownloaded(info);
                    h.sendEmptyMessageDelayed(0, 100);
                }
            });
			
			return view;
		}
		
	}
	
	/**
	 * 通过单击已经下载过的视频进行播放
	 */
	private AdapterView.OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			//获取点击item表示视频信息
			DownloadInfo info = downloadedList_show.get(position);
			
			//跳转到播放界面进行播放，用户可以修改为自己的播放界面
			Intent intent = new Intent(CachedActivity.this, PlayerActivity.class);
			
			//点击缓存视频播放时需要传递给播放界面的两个参数
			intent.putExtra("isFromLocal", true);		
			intent.putExtra("video_id", info.videoid);
			
			startActivity(intent);
			
		}

	};
	

}
