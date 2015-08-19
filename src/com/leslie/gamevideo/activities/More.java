package com.leslie.gamevideo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.baidu.appx.BDInterstitialAd;
import com.baidu.appx.BDInterstitialAd.InterstitialAdListener;
import com.leslie.gamevideo.AppConnect;
import com.leslie.gamevideo.R;
import com.leslie.gamevideo.UpdatePointsNotifier;
import com.leslie.gamevideo.utils.Utils;
import com.leslie.gamevideo.utils.Utils.DialogListenner;
import com.sixnine.live.util.SharePreferenceUtil;

public class More extends BaseActivity implements UpdatePointsNotifier {

	ListView lv;
	private RelativeLayout favoriteRl;
	private RelativeLayout historyRl;
	private RelativeLayout feedBackRl;
	private RelativeLayout aboutRl;
	private RelativeLayout download;
	private RelativeLayout app;
	private RelativeLayout myCoin;
	private ImageButton imgbtnHistory;
	private String email;
	private String about_us;
	private String webName;
	
	private static String TAG = "AppX_Interstitial";
	private BDInterstitialAd appxInterstitialAdView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);
		
		findAllViews();
		initBaiduAd();
		initOnclickerListener();
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    AppConnect.getInstance(More.this).getPoints(More.this);
	}

	private void initOnclickerListener() {
		imgbtnHistory.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(More.this, History.class);
				startActivity(intent);
			}
		});

	}

	private void findAllViews() {
		imgbtnHistory = (ImageButton) findViewById(R.id.imgbtnHistory);
		favoriteRl = (RelativeLayout) findViewById(R.id.more_favorite);
		historyRl = (RelativeLayout) findViewById(R.id.more_history);
		feedBackRl = (RelativeLayout) findViewById(R.id.more_feedback);
		aboutRl = (RelativeLayout) findViewById(R.id.more_about);
		download = (RelativeLayout) findViewById(R.id.download_layout);
		app = (RelativeLayout) findViewById(R.id.app_layout);
		myCoin = (RelativeLayout) findViewById(R.id.coin_layout);
		initListener();

	}

	private void initListener() {
		OnItemListener listener = new OnItemListener();
		favoriteRl.setOnClickListener(listener);
		historyRl.setOnClickListener(listener);
		feedBackRl.setOnClickListener(listener);
		aboutRl.setOnClickListener(listener);
		download.setOnClickListener(listener);
		app.setOnClickListener(listener);
		myCoin.setOnClickListener(listener);
	}

	private class OnItemListener implements OnClickListener {

		public void onClick(View v) {
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.more_favorite:
				intent.setClass(More.this, Favorite.class);
				startActivity(intent);
				break;
			case R.id.more_history:
				intent.setClass(More.this, History.class);
				startActivity(intent);
				break;
			case R.id.more_feedback:
				intent.setClass(More.this, FeedBack.class);
				intent.putExtra("email", email);
				intent.putExtra("webname", webName);
				startActivity(intent);
				break;
			case R.id.more_about:
				intent.setClass(More.this, About.class);
				intent.putExtra("about_us", about_us);
				intent.putExtra("website_name", webName);
				startActivity(intent);
				break;
			case R.id.download_layout:
			    intent.setClass(More.this, CacheActivity.class);
                startActivity(intent);
                break;
			case R.id.coin_layout:
			    Utils.customDialog(More.this, "当前积分为："+
			            SharePreferenceUtil.getInstance(More.this).getTotalPoint()+"，获取更多积分？", new DialogListenner() {
                    
                    @Override
                    public void confirm() {
                        AppConnect.getInstance(More.this).showOffers(More.this);
                    }
                });
			    break;
			case R.id.app_layout:
			    if (appxInterstitialAdView.isLoaded()) {
                    appxInterstitialAdView.showAd();
                } else {
                    appxInterstitialAdView.loadAd();
                }
			    intent.setClass(More.this, AppWall.class);
			    startActivity(intent);
			    break;
			default:
				break;
			}

		}

	}
	
	private void initBaiduAd(){
	    appxInterstitialAdView = new BDInterstitialAd(this,
            "qx8EPt4ijcaog5Mn9Sw99C8O", "0vywPp6v3PhQmwREAzWjiIr3");

        // 设置插屏广告行为监听器
        appxInterstitialAdView.setAdListener(new InterstitialAdListener() {

        @Override
        public void onAdvertisementDataDidLoadFailure() {
            Log.e(TAG, "load failure");
        }

        @Override
        public void onAdvertisementDataDidLoadSuccess() {
            Log.e(TAG, "load success");
        }

        @Override
        public void onAdvertisementViewDidClick() {
            Log.e(TAG, "on click");
        }

        @Override
        public void onAdvertisementViewDidHide() {
            Log.e(TAG, "on hide");
        }

        @Override
        public void onAdvertisementViewDidShow() {
            Log.e(TAG, "on show");
        }

        @Override
        public void onAdvertisementViewWillStartNewIntent() {
            Log.e(TAG, "leave");
        }

        });
        appxInterstitialAdView.loadAd();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(More.this, MainTabsActivity.class);
		startActivity(intent);
	}

	@Override
	protected void setTitles() {
		
	}

    @Override
    public void getUpdatePoints(String arg0, int arg1) {
        if(SharePreferenceUtil.getInstance(More.this).getTotalPoint() != arg1){
            SharePreferenceUtil.getInstance(More.this).setTotalPoint(arg1);
        }
    }

    @Override
    public void getUpdatePointsFailed(String arg0) {
        
    }

}
