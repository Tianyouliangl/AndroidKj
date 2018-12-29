package com.kj.app.utlis;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kj.app.R;

public class LoadingUtil {

    private static View view;
    private static Dialog mLoadingDialog;

   public static void showLoading(Activity activity){

        if (view == null){
            view = LayoutInflater.from(activity).inflate(R.layout.layout_loading, null);
        }
        if (mLoadingDialog == null){
            mLoadingDialog = new Dialog(activity, R.style.loading_dialog_style);
        }
       initSet();
    }

    private static void initSet() {
        mLoadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mLoadingDialog.hide();
                    return true;
                }
                return false;
            }
        });
        mLoadingDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT );
        mLoadingDialog.show();
    }

    public   static void hideLoading(){
        if (mLoadingDialog != null && mLoadingDialog.isShowing()){
            mLoadingDialog.dismiss();
        }
    }


}
