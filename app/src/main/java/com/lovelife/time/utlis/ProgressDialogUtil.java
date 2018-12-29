package com.lovelife.time.utlis;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lovelife.time.R;


/**
 * 全局progressdalog
 *
 * @author win
 *
 */
public class ProgressDialogUtil {

	//内存泄漏
	private static Dialog progressDialog;


	/**
	 * 得到自定义的progressDialog
	 *
	 * @param context
	 * @return
	 */
	private static Dialog createLoadingDialog(Context context) {

		Dialog loadingDialog = new Dialog(context, R.style.CustomProgressDialog);// 创建自定义样式dialog
		loadingDialog.setCanceledOnTouchOutside(false);
//		loadingDialog.setCancelable(true);// 可以用“返回键”取消
		View view = LayoutInflater.from(context).inflate(R.layout.custom_progress_dialog, null);
		loadingDialog.setContentView(view);// 设置布局
                loadingDialog.getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                        WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);


		loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					stopProgressDialog();
					return true;
				} else {
					return false; //默认返回 false
				}
			}
		});

		return loadingDialog;

	}


	public static void startProgressDialog(Context ct) {
		if(!(ct instanceof Activity) || ((Activity) ct).isFinishing() || ((Activity) ct).isDestroyed()){
			return;
		}
		if (progressDialog == null) {
			progressDialog = createLoadingDialog(ct);
		}
		if (progressDialog.isShowing()) {
			return;
		}
		Window window = progressDialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.alpha = 0.9f;
		window.setAttributes(lp);
		progressDialog.show();
	}


	public static void stopProgressDialog() {
		if (progressDialog != null) {
			if (progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			progressDialog = null;
		}
	}
}
