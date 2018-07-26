package com.kj.app.view.home.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kj.app.R;
import com.kj.app.base.BaseFragment;
import com.kj.app.utlis.SeekAttentionView;

public class HomePageFragment extends BaseFragment implements TextWatcher, View.OnClickListener {


    private EditText ed_input;
    private TextView tv_input_number;
    private int input_sum=0;
    private Button btn_submit;
    private String input_string;
    private TextView tv_hint;
    private String[] split;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_homepage;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView(View view) {
        ed_input = view.findViewById(R.id.ed_input);
        tv_input_number = view.findViewById(R.id.tv_input_number);
        btn_submit = view.findViewById(R.id.btn_submit);
        tv_hint = view.findViewById(R.id.tv_hint);
        tv_input_number.setText(" 0/155 ");
    }

    @Override
    public void onResume() {
        super.onResume();
        ed_input.addTextChangedListener(this);
        btn_submit.setOnClickListener(this);
        split = tv_input_number.getText().toString().trim().split("/");
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.i("SSSS", "beforeTextChanged: "+s.toString().trim().length());

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.i("SSSS", "onTextChanged: "+s.toString().trim().length());
        input_sum=s.toString().trim().length();
        tv_input_number.setText(input_sum+"/"+split[split.length-1]);
        if (s.toString().trim().length() <= Integer.valueOf(split[split.length-1])){
            editTextable(ed_input,true);
            tv_hint.setVisibility(View.GONE);
            input_string=s.toString().trim();
            tv_input_number.setTextColor(Color.BLUE);

            ed_input.setBackgroundResource(R.drawable.custom_edinput_bg_no);
            ed_input.setSelection(s.toString().trim().length());
        }else {
            ed_input.setText(input_string);
            ed_input.setSelection(input_string.toString().trim().length());
            tv_hint.setVisibility(View.VISIBLE);
            viewShaking(ed_input);
            viewShaking(tv_input_number);
            viewShaking(tv_hint);
            tv_input_number.setTextColor(Color.RED);
            ed_input.setBackgroundResource(R.drawable.custom_edinput_bg_yes);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.i("SSSS", "afterTextChanged: "+s.toString().trim().length());
    }

    @Override
    public void onClick(View v) {

    }
    private void editTextable(EditText editText, boolean editable) {
        if (!editable) { // disable editing password
            editText.setEnabled(false);
        } else { // enable editing of password
            editText.setEnabled(true);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void viewShaking(View iv) {
        ObjectAnimator animator = SeekAttentionView.tada(iv);
        animator.setRepeatCount(0);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ed_input.setBackgroundResource(R.drawable.custom_edinput_bg_no);
                tv_hint.setVisibility(View.INVISIBLE);
                tv_input_number.setTextColor(Color.BLUE);
            }
        });

    }
    }
