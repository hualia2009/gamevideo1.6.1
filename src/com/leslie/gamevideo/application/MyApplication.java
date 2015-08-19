package com.leslie.gamevideo.application;

import android.app.Activity;

import com.leslie.gamevideo.activities.CachedActivity;
import com.leslie.gamevideo.activities.CachingActivity;
import com.youku.player.YoukuPlayerBaseApplication;

/**
 * 接入时自定义的Application需要继承YoukuPlayerBaseApplication 
 *
 */
public class MyApplication extends YoukuPlayerBaseApplication {
    
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		

	}

	/**
	 * 配置视频的缓存路径，格式举例： /appname/videocache/
	 * 如果返回空，则视频默认缓存路径为： /应用程序包名/videocache/ 
	 *
	 */
	public String configDownloadPath() {
		// TODO Auto-generated method stub
		
		//return "/myapp/videocache/";			//举例
		return null;
	}

    public Class<? extends Activity> getCachedActivityClass() {
        // TODO Auto-generated method stub
        return CachedActivity.class;
    }

    public Class<? extends Activity> getCachingActivityClass() {
        // TODO Auto-generated method stub
        return CachingActivity.class;
    }

}
