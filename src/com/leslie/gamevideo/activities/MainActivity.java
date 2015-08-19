package com.leslie.gamevideo.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.leslie.gamevideo.AppConnect;
import com.leslie.gamevideo.UpdatePointsNotifier;
import com.sixnine.live.R;
import com.sixnine.live.fragment.ExampleFragment;
import com.sixnine.live.util.SharePreferenceUtil;

public class MainActivity extends FragmentActivity implements UpdatePointsNotifier {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        
        LiveHallAdFragment liveFragment = new LiveHallAdFragment();
        fragmentTransaction.add(R.id.live_fragment, liveFragment);
        //fragmentTransaction.add(exampleFragment, "");
        fragmentTransaction.commit();
        
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ExampleFragment fragment = new ExampleFragment();
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				
				ft.replace(R.id.live_fragment, fragment);
				ft.addToBackStack(null);
				ft.commit();
			}
		});
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        AppConnect.getInstance(this).getPoints(this);
    }

    @Override
    public void getUpdatePoints(String arg0, int arg1) {
        if(SharePreferenceUtil.getInstance(this).getTotalPoint() != arg1){
            SharePreferenceUtil.getInstance(this).setTotalPoint(arg1);
        }
    }

    @Override
    public void getUpdatePointsFailed(String arg0) {
        
    }
}

