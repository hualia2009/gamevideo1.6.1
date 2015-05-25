package com.leslie.gamevideo.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leslie.gamevideo.R;
import com.leslie.gamevideo.activities.Detail;
import com.leslie.gamevideo.activities.PlayerActivity;
import com.leslie.gamevideo.adapter.YoukuVideoAdapter;
import com.leslie.gamevideo.banner.GalleryFlow;
import com.leslie.gamevideo.banner.ImageAdapter;
import com.leslie.gamevideo.entity.Video;
import com.leslie.gamevideo.jsonparser.YoukuParser;

/**
 * 管理和控制全部的面板 Controller的逻辑是: 将全部的活动都分组, 当切换到某一组时会自动选择显示相应的界面, 当从一个界面切换出来时,
 * 如果当前界面不是 home 界面而且不是, 则会销毁该界面;
 * 
 * 本类可以托管活动的切换, 但是不管第五组的活动
 * 
 * @author Administrator
 * 
 */
@SuppressLint("JavascriptInterface")
public class Controller {

	public static final int GROUP_ID_HOME = 1;
	public static final int GROUP_ID_CHANNEL = 2;
	public static final int GROUP_ID_SORT = 3;
	public static final int GROUP_ID_SEARCH = 4;
	public static final int GROUP_ID_MORE = 5;
	private static Controller instance = null;
	private Activity activity;
	private YoukuVideoAdapter adapter;
	private ImageView dotImages[];
	private List<Video> banners;

	/**
	 * Single Instance 负责控制全部的Activity的切换和显示
	 */
	private Controller() {
		banners = new ArrayList<Video>();
	}

	public static Controller instance() {
		if (instance == null) {
			instance = new Controller();
		}

		return instance;

	}

	public void play(View view, Video videoInfo) {
		this.activity = (Activity) view.getContext();
		Intent intent = new Intent(this.activity, PlayerActivity.class);
		Bundle extras = new Bundle();
		extras.putString("vid", videoInfo.getId());
		extras.putString("title", videoInfo.getTitle());
		extras.putString("duration", videoInfo.getDuration());
		intent.putExtras(extras);
		this.activity.startActivity(intent);
	}

	public void manageBanner(GalleryFlow gallery, String url, Context context,
			final TextView textView, LinearLayout galleryPoint) {
		try {
			HttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String results = EntityUtils.toString(entity);
			banners = YoukuParser.parse(results);
			if(banners!=null && banners.size()>5){
				banners = banners.subList(0, 5);
			}
				
			dotImages = new ImageView[banners.size()];
			addPoint(galleryPoint, banners.size(), context);
			ImageAdapter imageAdapter = new ImageAdapter(context, banners,
					textView, gallery);
			gallery.setAdapter(imageAdapter);
			gallery.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					Intent intent = new Intent(arg1.getContext(), Detail.class);
					Bundle extras = new Bundle();
					extras.putSerializable("info",
							banners.get(position % banners.size()));
					intent.putExtras(extras);
					arg1.getContext().startActivity(intent);
				}
			});
			gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> adapterview,
						View view, int j, long l) {
					textView.setText(banners.get(j % 5).getTitle());
					for (int i = 0; i < banners.size(); i++) {
						if (i == j) {
							dotImages[i]
									.setImageResource(R.drawable.feature_point_cur);
						} else {
							dotImages[i]
									.setImageResource(R.drawable.feature_point);
						}
					}
				}

				public void onNothingSelected(AdapterView<?> arg0) {
				}
			});
		} catch (MalformedURLException e) {
			Log.e("Controller", "MalformedURLException");
		} catch (IOException e) {
			Log.e("Controller", "IOException");
		}

	}

	private void addPoint(LinearLayout galleryPoint, int size, Context context) {
		for (int i = 0; i < size; i++) {
			dotImages[i] = new ImageView(context);
			dotImages[i].setImageResource(R.drawable.feature_point);
			galleryPoint.addView(dotImages[i]);
		}
	}

	public YoukuVideoAdapter getAdapter() {
		return this.adapter;
	}

	public void update() {
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
	}
}
