package com.sgcc.smarteleclife.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sgcc.smarteleclife.R;

import media.IjkVideoManager;
import media.controller.MediaController;
import media.videoview.IjkVideoView;

/**
 * Created by miao_wenlong on 2017/8/28.
 */

public class VideoActivity extends AppCompatActivity {
    private IjkVideoView mIjkVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        mIjkVideoView = (IjkVideoView) findViewById(R.id.video_view);
        MediaController mediaController = new MediaController(this);
        mediaController.setShowThumb(true);
        mIjkVideoView.setMediaController(mediaController);

        mIjkVideoView.setVideoPath("http://baobab.wdjcdn.com/14564977406580.mp4");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        IjkVideoManager.getInstance().release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        IjkVideoManager.getInstance().pause();
    }
}
