package com.sgcc.smarteleclife.Presenter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.DrawableRes;

import com.sgcc.smarteleclife.MainActivity;

import mvpArt.mvp.BasePresenter;
import mvpArt.mvp.Message;

/**
 * Created by miao_wenlong on 2017/8/7.
 */

public class MainPresenter extends BasePresenter{
   public void share(Message message){
       MainActivity mainActivity = (MainActivity) message.getTarget();
       /*Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("image*//*");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(getResourcesUri(R.mipmap.ic_launcher)));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, getTitle()));*/
       Intent qqIntent = new Intent(Intent.ACTION_SEND);
       qqIntent.setPackage("com.tencent.mobileqq");
       qqIntent.setType("text/plain");
       qqIntent.putExtra(Intent.EXTRA_TEXT, "分享到微信的内容");
       mainActivity.startActivity(qqIntent);
   }
    private String getResourcesUri(@DrawableRes int id, Context context) {
        Resources resources = context.getResources();
        String uriPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(id) + "/" +
                resources.getResourceTypeName(id) + "/" +
                resources.getResourceEntryName(id);
        return uriPath;
    }
}
