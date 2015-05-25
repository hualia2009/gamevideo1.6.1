package com.leslie.gamevideo.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.leslie.gamevideo.AppConnect;
import com.leslie.gamevideo.R;
import com.umeng.analytics.MobclickAgent;

public class LogoActivity extends BaseActivity {
	private AlphaAnimation alphaAnimation;
	private ImageView img_logo;
	public static ArrayList<Activity> activities = new ArrayList<Activity>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		//万普广告平台
		AppConnect.getInstance(this);
		
		// 友盟统计
		MobclickAgent.openActivityDurationTrack(false);
		MobclickAgent.updateOnlineConfig(this);
		MobclickAgent.setDebugMode( true );
		MobclickAgent.setCatchUncaughtExceptions(true);
		
		MainTabsActivity.is_ad_on = AppConnect.getInstance(this).getConfig("is_ad_on","0").trim();
		Log.i("is_ad_on", "!!!"+MainTabsActivity.is_ad_on);
		
		activities.add(LogoActivity.this);

		img_logo = (ImageView) findViewById(R.id.image_logo);
		alphaAnimation = new AlphaAnimation(1.0f, 1.0f);
		alphaAnimation.setDuration(3000);
		img_logo.startAnimation(alphaAnimation);
		alphaAnimation.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				Intent intent = new Intent(LogoActivity.this,
						MainTabsActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void setTitles() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(this.getClass().getName());
		MobclickAgent.onResume(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(this.getClass().getName());
		MobclickAgent.onPause(this);
	}

}