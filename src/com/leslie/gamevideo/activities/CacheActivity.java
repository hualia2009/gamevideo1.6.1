package com.leslie.gamevideo.activities;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leslie.gamevideo.R;

public class CacheActivity extends ActivityGroup{
    
    private LinearLayout container = null;
    
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cache_layout);
        
        container = (LinearLayout) findViewById(R.id.containerBody);
        container.removeAllViews();
        container.addView(getLocalActivityManager().startActivity(
                "Module1",
                new Intent(CacheActivity.this, CachingActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                .getDecorView());
        
     // 模块1
        TextView downloading = (TextView) findViewById(R.id.downloading);
        downloading.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                container.removeAllViews();
                container.addView(getLocalActivityManager().startActivity(
                        "Module1",
                        new Intent(CacheActivity.this, CachingActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        .getDecorView());
            }
        });

        // 模块2
        TextView downloaded = (TextView) findViewById(R.id.downloaded);
        downloaded.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                container.removeAllViews();
                container.addView(getLocalActivityManager().startActivity(
                        "Module2",
                        new Intent(CacheActivity.this, CachedActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        .getDecorView());
            }
        });
    }
    
}
