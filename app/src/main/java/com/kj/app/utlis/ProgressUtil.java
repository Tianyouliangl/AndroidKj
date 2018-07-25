package com.kj.app.utlis;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kj.app.R;


public class ProgressUtil extends DialogFragment {

    @SuppressLint("StaticFieldLeak")
    private static ProgressUtil instance;
    private TextView tvTitle;
    private String content;

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.0f;

        window.setAttributes(windowParams);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View v = inflater.inflate(R.layout.pop_progress_loading, container, false);
        tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        if (!TextUtils.isEmpty(content)) {
            tvTitle.setText(content);
        }
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });
        return v;
    }

    @Override
    public Dialog getDialog() {
        return super.getDialog();
    }

    public static void show(FragmentManager fm) {
        if (instance != null && instance.getFragmentManager() != null) {
            instance.dismissAllowingStateLoss();
        }
        instance = new ProgressUtil();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(instance, "ProgressUtil");
        fragmentTransaction.commitAllowingStateLoss();// 这里直接调用show方法会报java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        //因为show方法中是通过commit进行的提交(通过查看源码)
        //这里为了修复这个问题，使用commitAllowingStateLoss()方法
//        instance.show(fm, "ProgressUtil");
    }

    public static void updateText(String text) {
        if (instance != null) {
            instance.content = text;
            if (instance.tvTitle != null) {
                instance.tvTitle.setText(text);
            }
        }
    }

    public static void dis() {
        if (instance != null && instance.getFragmentManager() != null)
            instance.dismissAllowingStateLoss();
        instance = null;
    }

    public static void setCancelAble(boolean flag) {
        if (instance != null) {
            instance.setCancelable(flag);
        }
    }

}
