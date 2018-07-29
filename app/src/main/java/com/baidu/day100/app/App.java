package com.baidu.day100.app;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
    }


    private void initImageLoader() {
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .defaultDisplayImageOptions(imgageOptions())
                .build();
        ImageLoader.getInstance().init(configuration);
    }


    private DisplayImageOptions imgageOptions() {
        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .displayer(new CircleBitmapDisplayer())
                .build();
        return options;
    }
}
